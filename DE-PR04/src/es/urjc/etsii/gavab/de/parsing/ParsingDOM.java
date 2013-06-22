package es.urjc.etsii.gavab.de.parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import es.urjc.etsii.gavab.de.aux.Noticia;

public class ParsingDOM 
{

	/**
	 * Realiza un parseo DOM del fichero xml original.
	 * Como entidades, agrega todas aquellas que aparecen en el título y en
	 * el primer párrafo.
	 * 
	 * @param noticia	Objeto Noticia con la información extraída.
	 */
	public static void setInfoNoticia(Noticia noticia)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(noticia.getRuta()));
			
	   
		   //Procesar el título, y añadir entidades que formen parte de él 
		   NodeList list = document.getElementsByTagName("TITLE");
		   int i=0;
		   String titleAux = "";
		   //En realidad solo debería haber uno, pero mejor lo procesamos así
		   while(i<list.getLength()) 
		   {
			   NodeList children = list.item(i).getChildNodes();
			   
			   int j = 0;
			   
			   while(j< children.getLength())
			   {
				   //Si es un nodo W, almacenamos su atributo FRM en un buffer
				   if (children.item(j).getNodeName().equalsIgnoreCase("W"))
				   {
					   titleAux+= children.item(j).getAttributes().getNamedItem("FRM").getNodeValue() + " ";
				   }else if (children.item(j).getNodeName().equalsIgnoreCase("MW")){
					   //Y si es un nodo MW, además lo guardamos como entidad
					   titleAux+= children.item(j).getAttributes().getNamedItem("FRM").getNodeValue() + " ";
					   noticia.addEntidad(children.item(j).getAttributes().getNamedItem("FRM").getNodeValue());
				   }
				   
				   j++;
			   }
		      
			   i++;
		   }
		   
		   //Y le asignamos el título
		   noticia.setTitulo(titleAux);
		   
		   //Por último, agregamos todas las entidades del primer párrafo
		   NodeList parrafos = document.getElementsByTagName("P");
		   
		   //Tomamos el primero si existe, y buscamos entidades entre sus hijos
		   if (parrafos.item(0)!=null)
		   {
			   NodeList children = parrafos.item(0).getChildNodes();
			   
			   int j = 0;
			   
			   while(j< children.getLength())
			   {
				   //Si es un nodo MW lo guardamos como entidad
				   if (children.item(j).getNodeName().equalsIgnoreCase("MW"))
					   noticia.addEntidad(children.item(j).getAttributes().getNamedItem("FRM").getNodeValue());
				   
				   j++;
			   }
			   
		   }
		   
		} catch (Exception e) {
		     e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * Serializa un Document DOM a un fichero xml
	 * 
	 * @param document	Document DOM a serializar
	 * @param path	Path del destino
	 */
	public static void writeXmlDocument(Document document, String path){
        final String ENCODING = "ISO-8859-1";
        PrintStream fXml = null;
        try {
            fXml = new PrintStream(new FileOutputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        OutputFormat format = new OutputFormat(document, ENCODING, true);
        StringWriter s = new StringWriter();
        XMLSerializer serial = new XMLSerializer(s, format);
        try {
            serial.asDOMSerializer();
            serial.serialize(document.getDocumentElement());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fXml.print(s.toString());
        fXml.close();
    }
	
}

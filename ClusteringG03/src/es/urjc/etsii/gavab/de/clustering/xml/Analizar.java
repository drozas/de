package es.urjc.etsii.gavab.de.clustering.xml;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import java.util.*;

import org.w3c.dom.*;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import es.urjc.etsii.gavab.de.clustering.algoritmo.NewsCluster;
import es.urjc.etsii.gavab.de.clustering.algoritmo.Noticia;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.*;


public class Analizar extends DefaultHandler 
{	
	private boolean title_ok = false;
	private String title = "";
	private String asunto;
	private boolean asunto_ok = false;
	private String sumario = "";
	private boolean sumario_ok = false;
	private boolean parrafo_ok = false;
	private String parrafo = "";
	

	public void startElement(String uri, String local, String name,	Attributes attrs)  
							throws SAXException 
	{	
		if (name.equals("TITLE"))
		{
			asunto_ok = true;
			title_ok = true;
		}
		
		if (name.equals("SUMMARY"))
			sumario_ok = true;
		
		if (name.equals("BODY"))
			parrafo_ok = true;
		
		if (!asunto_ok)
			asunto = attrs.getValue (0);
		
		if (title_ok && ((name.equals("W") || (name.equals("MW")))))
			title = title + attrs.getValue (0) + " ";	
		
		if (sumario_ok && name.equals("MW"))
			sumario = sumario + attrs.getValue(0) + " ";
		
		if (parrafo_ok && name.equals("MW"))
			parrafo = parrafo + attrs.getValue(0) + " ";
	}  
	

    public void endElement(String uri, String localName, String qName) 
    {
    	if (qName.equals("TITLE"))
    		title_ok = false;	
    	
    	if (qName.equals("SUMMARY"))
    		sumario_ok = false;
    	
    	if (qName.equals("P"))
    		parrafo_ok = false;
    }
    
    /**
     * 
     * @return Asunto del fichero XML
     */
    public String getAsunto()
    {
    	int comilla = asunto.indexOf("\"");
    	if (comilla!=-1)
    		asunto = asunto.substring(comilla+1);
    	int aux = asunto.indexOf(":");
    	if (aux!=-1)
    	{
    		String asunto1 = asunto.substring(0,aux);
    		int aux2 = asunto.indexOf(",");
    		if (aux2!=-1)
    		{
    			String asunto2 = asunto.substring(aux+1,aux2);
    			String asunto3 = asunto.substring(aux2+1);
    			asunto = asunto1 + " " + asunto2 +" " + asunto3;
    		}
    		else
    			asunto = asunto1;
    	}
    	return asunto;
    }
    
    /**
     * 
     * @return Titulo del fichero XML
     */
    public String getTitle()
    {
    	return title;
    }
    
    /**
     * 
     * @return Sumario del fichero XML
     */
    public String getSumario()
    {
    	return sumario;
    }
    
    /**
     * 
     * @return Primer Parrafo del fichero XML
     */
    public String getPrimerParrafo()
    {
    	return parrafo;
    }
        
    /**
     * 
     * @param clusters Lista de clusters para la creacion del XML
     * @param measure Medida F
     */
    public void crearXML(ArrayList<NewsCluster> clusters, float measure)
    {
    	try
    	{
    		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		Document doc = docBuilder.newDocument();
    		Element etiquetaClusters = doc.createElement("CLUSTERS");
    		etiquetaClusters.setAttribute("medida", Float.toString(measure));
    		doc.appendChild(etiquetaClusters);
    		
    		for (int i=0; i<clusters.size(); i++)
    		{
    			Element etiquetaCluster = doc.createElement("CLUSTER");
    			etiquetaClusters.appendChild(etiquetaCluster);
    			etiquetaCluster.setAttribute("id", Integer.toString(i));
    			
        		for (int j=0; j<clusters.get(i).getNoticiasCluster().size(); j++)
        		{
        			Element etiquetaNoticia = doc.createElement("NOTICIA");
        			etiquetaCluster.appendChild(etiquetaNoticia);
        			etiquetaNoticia.setAttribute("id", Integer.toString(j));
        			
        			Noticia noticia = clusters.get(i).getNoticiasCluster().get(j);
        			Element etiquetaNombre = doc.createElement("NOMBRE");
        			Element etiquetaTitulo = doc.createElement("TITULO");
        			Element etiquetaUrl = doc.createElement("URL");
        			etiquetaNombre.setTextContent(noticia.getName());
        			etiquetaTitulo.setTextContent(noticia.getTitulo());
        			etiquetaUrl.setTextContent("../news/" + noticia.getName());
        			etiquetaNoticia.appendChild(etiquetaNombre);
        			etiquetaNoticia.appendChild(etiquetaTitulo);
        			etiquetaNoticia.appendChild(etiquetaUrl);
        		}
    		}
    		writeXmlDocument(doc, "out/resultado.xml");
    	}
    	catch (Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	
    	
    }

    /**
     * 
     * @param document Documento XML a escribir
     * @param path Path a escribir el fichero
     */
    public static void writeXmlDocument(Document document, String path)
    {
        final String ENCODING = "ISO-8859-1";
        PrintStream fXml = null;
        try 
        {
        	fXml = new PrintStream(new FileOutputStream(path));
        } 
        catch (FileNotFoundException e) 
        {
        }
        OutputFormat format = new OutputFormat(document, ENCODING, true);
        StringWriter s = new StringWriter();
        XMLSerializer serial = new XMLSerializer(s, format);
       
        try 
        {
            serial.asDOMSerializer();
            serial.serialize(document.getDocumentElement());
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        fXml.print(s.toString());
        fXml.close();
    }
    
}
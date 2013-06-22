import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import com.sun.org.apache.xerces.internal.parsers.SAXParser;


public class Estadisticas {
	
	
	public static int getNParrafosDOM(String path)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
		   DocumentBuilder db = dbf.newDocumentBuilder();
		   Document document = db.parse(new File(path));
		   NodeList list = document.getElementsByTagName("P");
		   return list.getLength();
		   
		} catch (Exception e) {
		     e.printStackTrace();
		     return -1;
		}		
	}
	/**
	 * 
	 * @param path	Path del fichero xml a tratar
	 * @param nodoName	Nombre del nodo a buscar. Ej.: CAT
	 * @param atrBuscado	Nombre del atributo que vamos a mirar en ese nodo. Ej.:CODE
	 * @param valAtrBuscado	Valor del atributo por el que seleccionaremos ese nodo. Ej.: LOCATION, PERSON
	 * @param atrPadreMostrar	Valor del atributo del nodo padre que vamos a mostrar .Ej.: FRM
	 * @return
	 */
	public static ArrayList<String> getListDOM(String path, String nodoName, String atrBuscado, String valAtrBuscado, String atrPadreMostrar)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
		   DocumentBuilder db = dbf.newDocumentBuilder();
		   Document document = db.parse(new File(path));
		   NodeList list = document.getElementsByTagName(nodoName);
		   ArrayList<String> listDevuelta = new ArrayList<String>();
		   
		   int i=0;
		   while(i<list.getLength()) 
		   {
			  //Para cada nodo CAT
		      Node n = list.item(i);
		      //Cogemos todos sus atributos y comprobamos que tiene uno que sea CODE y cuyo
		      //valor sea LOCATION
		      NamedNodeMap atts = n.getAttributes();
		      Node n2 = atts.getNamedItem(atrBuscado);
		      if (n2.getNodeValue()!=null && n2.getNodeValue().equalsIgnoreCase(valAtrBuscado))
		      {
		    	  //Cogemos el atributo FRM del padre del nodo CAT
		    	  //System.out.println(n.getParentNode().getAttributes().getNamedItem(atrPadreMostrar).getNodeValue());
		    	  listDevuelta.add(n.getParentNode().getAttributes().getNamedItem(atrPadreMostrar).getNodeValue());
		      }
		      i++;
		   }

		   return listDevuelta;
		   
		} catch (Exception e) {
		     e.printStackTrace();
		     return null;
		}		
	}
	
	public static MyHandler parsingSAX(String path)
	{
		XMLReader parser = new SAXParser();
		MyHandler handler = new MyHandler();
		InputSource inputFile = new InputSource(path);
		inputFile.setEncoding("ISO-8859-1");
		try {
		    parser.setContentHandler(handler);
		    parser.parse(inputFile);
		    return handler;
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		} catch (SAXException e) {
		    e.printStackTrace();
		    return null;
		}
	
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub	

		MyHandler saxHandler = parsingSAX("noticia.xml");
		System.out.println("Dom dice: hay " + getNParrafosDOM("noticia.xml") + " párrafos.");
		System.out.println("SAX dice: hay "+ saxHandler.getCounter() + " párrafos.");
		ArrayList<String> lugares = getListDOM("noticia.xml","CAT","CODE","LOCATION","FRM");
		ArrayList<String> personas = getListDOM("noticia.xml","CAT","CODE","PERSON","FRM");
		ArrayList<String> lugaresSax = saxHandler.getListaLugares();
		ArrayList<String> personasSax = saxHandler.getListaPersonas();

		//Mostrar lista de lugares con DOM
		System.out.println("Lista de lugares DOM: ");
		Iterator<String> lugaresIterador = lugares.iterator();
		while (lugaresIterador.hasNext())
			System.out.println("\t" + lugaresIterador.next());

		//Mostrar lista de lugares con SAX
		System.out.println("Lista de lugares SAX: ");
		lugaresIterador = lugaresSax.iterator();
		while (lugaresIterador.hasNext())
			System.out.println("\t" + lugaresIterador.next());

		//Mostrar lista de personas con DOM
		System.out.println("Lista de personas con DOM: ");
		Iterator<String> personasIterador = personas.iterator();
		while (personasIterador.hasNext())
			System.out.println("\t" + personasIterador.next());

		//Mostrar lista de personas con SAX
		System.out.println("Lista de personas con SAX: ");
		personasIterador = personasSax.iterator();
		while (personasIterador.hasNext())
			System.out.println("\t" + personasIterador.next());
	}

}

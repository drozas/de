import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;


public class Noticia 
{
	public static final String source = "noticia.xml";
	public static final String destiny = "noticia_modificada.xml";

	/**
	 * Crea una instancia del manejador SAX y realiza el parseo que
	 * generará un documento DOM
	 * 
	 * @param path	Ruta del documento a parsear
	 * @param doc	Documento dom a generar
	 * @return	generadorSAX
	 */
	public static SAXHandler parsingSAX(String path, Document doc)
	{
		XMLReader parser = new SAXParser();
		SAXHandler handler = new SAXHandler(doc);
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


	/**
	 * @param args
	 */
	public static void main(String[] args) {


		//Creamos una nueva factoría
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
		try 
		{
			//Y un nuevo documento a partir de ella
			docBuilder = dbfac.newDocumentBuilder();
	        Document doc = docBuilder.newDocument();

			SAXHandler saxHandler = parsingSAX(source, doc);
			writeXmlDocument(saxHandler.getDocument(), destiny);
			System.out.println("Fichero " + destiny + " creado correctamente a partir de " + source);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

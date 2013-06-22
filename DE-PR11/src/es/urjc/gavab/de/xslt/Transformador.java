package es.urjc.gavab.de.xslt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Transformador {

	public Transformador(){}
	
	public void transform(String xmlFile, String outputFile, String xslFile) throws FileNotFoundException, TransformerException {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new StreamSource(xslFile));
			transformer.transform(new StreamSource(xmlFile), new StreamResult(new FileOutputStream(outputFile)));
	}

}

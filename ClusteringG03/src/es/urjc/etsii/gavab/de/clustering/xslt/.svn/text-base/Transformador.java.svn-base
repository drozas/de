package es.urjc.etsii.gavab.de.clustering.xslt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Transformador {

	public Transformador(){}
	
	/**
	 * 
	 * Aplica una hoja de estilo XSLT a un fichero XML, generando un fichero HTML
	 * 
	 * @param xmlFile	Fichero XML a tratar
	 * @param outputFile	Fichero HTML generado
	 * @param xslFile	Hoja de estilo a aplicar
	 * @throws FileNotFoundException
	 * @throws TransformerException
	 */
	public void transform(String xmlFile, String outputFile, String xslFile) throws FileNotFoundException, TransformerException {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new StreamSource(xslFile));
			transformer.transform(new StreamSource(xmlFile), new StreamResult(new FileOutputStream(outputFile)));
	}

}

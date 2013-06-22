package es.urjc.gavab.de.xpath;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Analyzer {
	
	public void analyze(File xmlFile) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document document = null;
		try {
			db = dbf.newDocumentBuilder();
			document = db.parse(xmlFile);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.analyze(document);
	}
	
	
	public void analyze(Document document) {
		XPathFactory xfactory = XPathFactory.newInstance();
		XPath xpath = xfactory.newXPath();
		XPathExpression editoriales_expression, titulos_expression, counter_expression;
		Object editoriales_nodes = null;
		Object titulos_nodes = null;
		Object counter_nodes = null;
		try {
			editoriales_expression = xpath.compile("//editorial");
			editoriales_nodes = editoriales_expression.evaluate(document, XPathConstants.NODESET);
			
			titulos_expression = xpath.compile("/biblioteca/libro[@year=\"2008\"]");
			titulos_nodes = titulos_expression.evaluate(document, XPathConstants.NODESET);
			
			counter_expression = xpath.compile("//libro");
			counter_nodes = counter_expression.evaluate(document, XPathConstants.NODESET);
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		int i;
		NodeList nodes = (NodeList) editoriales_nodes;
		System.out.println("Editoriales presentes en la biblioteca:\n");
		for (i = 0; i < nodes.getLength(); i++)
			System.out.println(nodes.item(i).getTextContent());
			
			
		nodes = (NodeList) titulos_nodes;
		System.out.println("\n\nTítulo y genero de los libros del 2008:\n");
		for (i = 0; i < nodes.getLength(); i++)
		{
			NodeList children = nodes.item(i).getChildNodes();
			for(int j=0; j<children.getLength(); j++)
			{
				if (children.item(j).getNodeName().equalsIgnoreCase("titulo"))
					System.out.print(children.item(j).getTextContent());
				else if (children.item(j).getNodeName().equalsIgnoreCase("genero"))
					System.out.println(" -- " + children.item(j).getTextContent());
				
			}
				
			
		}
		
		nodes = (NodeList) counter_nodes;
		System.out.println("\nNúmero de libros en la biblioteca:\n");
		System.out.println(nodes.getLength() + " libros");
			
			
		
	}

}

package es.urjc.gavab.de.xslt;

import java.io.FileNotFoundException;

import javax.xml.transform.TransformerException;

public class XsltExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Transformador t = new Transformador();
		// args[0]: fichero xml origen
		// args[1]: fichero donde se quiere dejar la salida
		// args[2]: fichero que contiene la hoja de estilo
		try 
		{

			t.transform("Cartelera.xml", "parte01.html", "plantilla01.xsl");
			t.transform("Cartelera.xml", "parte02.html", "plantilla02.xsl");
			t.transform("Tienda.xml", "parte03.html", "plantilla03.xsl");
			
			System.out.println("OK");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

}

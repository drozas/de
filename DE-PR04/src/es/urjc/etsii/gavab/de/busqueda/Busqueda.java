package es.urjc.etsii.gavab.de.busqueda;

import org.w3c.dom.Document;

import es.urjc.etsii.gavab.de.parsing.ParsingDOM;


public class Busqueda {

	public static final String src_path = "./noticias/";
	public static final String dest_path = "./eventos.xml";
	public static final int nNoticiasThreshold = 2;
	public static final int nMatchesThreshold = 2;
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		AnalizadorNoticias analyzer = new AnalizadorNoticias(src_path);
		
		//Cargamos todas las noticias a memoria
		System.out.println("Cargando noticias de " + src_path);
		System.out.println(analyzer.toString());
		
		//Realizamos un análisis y creamos los eventos
		System.out.println("Realizando comparativa de noticias con parámetros: nNoticiasThresold = " +
				Integer.toString(nNoticiasThreshold) + ", nMatchesThreshold = " + Integer.toString(nMatchesThreshold));
		analyzer.compararNoticias(nNoticiasThreshold, nMatchesThreshold);
		System.out.println(analyzer.eventosToString());
		
		//Por último solicitamos y serializamos un documento DOM con dicha información
		Document eventosDom = analyzer.getEventosDOM();
		ParsingDOM.writeXmlDocument(eventosDom, dest_path);
		System.out.println("Documento DOM serizalizado en : " + dest_path);

	}

}

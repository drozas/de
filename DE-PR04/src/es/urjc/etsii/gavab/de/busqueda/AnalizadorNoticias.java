package es.urjc.etsii.gavab.de.busqueda;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import es.urjc.etsii.gavab.de.aux.Evento;
import es.urjc.etsii.gavab.de.aux.Noticia;

public class AnalizadorNoticias {
	
	private ArrayList<Noticia> noticias = new ArrayList<Noticia>();
	private ArrayList<Evento> eventos = new ArrayList<Evento>();
	
	/**
	 * 
	 * Constructor. Carga todas las noticias que se encuentren en el path
	 * 
	 * @param path	Path en el que se encuentran las noticias
	 */
	public AnalizadorNoticias(String path)
	{
		File directorio = new File(path);
		String [] ficheros = directorio.list();
		
		for (int i = 0; i < ficheros.length; i++) 
			this.noticias.add(new Noticia(path + ficheros[i])); 
		
	}
	
	/**
	 * 
	 * Compara todas las noticias, y para aquellas en las que se repitan más de 3 entidades
	 * se generará un evento
	 * 
	 * Idea A: Generamos un evento por cada noticia, y agregamos las similares
	 * Idea B: Generamos un evento y analizamos todas las noticias. Si se agregaron más de
	 * 			N, se guarda el evento
	 * Idea C: Híbrido. Genereamos un evento por cada noticia, y la agregamos.
	 * 		Después agregamos todas las similares, y si el evento tiene al menos NNoticiasThresold
	 * 		lo guardarmos.
	 * 
	 *  
	 * Implementación actual: La idea C parece ofrecer mejores resultados, por tanto es la
	 * 	que se implementa en esta versión.
	 * 
	 * @param nNoticiasThreshold Número mínimo de noticias necesarias para que se almacene dicho evento
	 * @param nMatchesThreshold Número mínimo de matches entre dos noticias, para que se almacene 
	 * 							en un posible evento.
	 * 
	 */
	public void compararNoticias(int nNoticiasThreshold, int nMatchesThreshold)
	{
		for (int i=0; i<this.noticias.size(); i++)
		{
			//Creamos un evento, con esa noticia
			Evento evento = new Evento(this.eventos.size() + 1);
			evento.addNoticia(this.noticias.get(i));
			
			for (int j=i+1; j<this.noticias.size();j++)
			{
				//System.out.println(this.noticias.get(i).getTitulo() + " tiene " + this.noticias.get(i).getMatches(this.noticias.get(j)) + " matches con " + this.noticias.get(j).getTitulo());
				//De momento, si hay más de nMatchesThresold coincidencias, guardamos el evento
				if(this.noticias.get(i).getMatches(this.noticias.get(j))>=nMatchesThreshold)
				{
					evento.addNoticia(this.noticias.get(j));
				}
			}
			
			//Y almacenamos dicho evento sólo si contiene más de N noticias
			if (evento.nNoticias()>=nNoticiasThreshold)
				this.eventos.add(evento);
		}	
		
	}
	
	/**
	 * 
	 * Devuelve un documento DOM con los eventos seleccionados.
	 * 
	 * @return	Documento DOM si el proceso fue correcto, null en otro caso
	 */
	public Document getEventosDOM()
	{
		try 
		{
			//Creamos una nueva factoría
	        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder;
			//Y un nuevo documento a partir de ella
			docBuilder = dbfac.newDocumentBuilder();
	        Document doc = docBuilder.newDocument();
	        
	        //Creamos una raíz eventos
			Element root = doc.createElement("Eventos");
            doc.appendChild(root);
            
            //Y por cada evento, un nodo hijo evento
    		Iterator<Evento> eventosIterador = this.eventos.iterator();
    		
    		while (eventosIterador.hasNext())
    		{
    			Evento evento = eventosIterador.next();
				Element eventoNode = doc.createElement("Evento");
				eventoNode.setAttribute("id", Integer.toString(evento.getId()));
	            root.appendChild(eventoNode);
	            
	            //Y en el, todas sus noticias.
	    		Iterator<Noticia> noticiasIterador = evento.getNoticias().iterator();
	    		while (noticiasIterador.hasNext())
	    		{
	    			Noticia n = noticiasIterador.next();
					Element documentoNode = doc.createElement("Documento");
					documentoNode.setAttribute("name", n.getRuta());
		            eventoNode.appendChild(documentoNode);
		            
					//Agregamos el título como un nodo hijo de documentNode.
					Element tituloNode = doc.createElement("Titulo");
					tituloNode.setTextContent(n.getTitulo());
		            documentoNode.appendChild(tituloNode);

	    		}
	            
    		}

			return doc;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		

	
	}
	
	/**
	 * 
	 * Devuelve un string con todas las noticias cargadas en este analizador.
	 * 
	 */
	public String toString()
	{
		String res = "";
		Iterator<Noticia>iterador = this.noticias.iterator();
		while (iterador.hasNext())
			res += iterador.next().toString();
		return res;
	
	}
	
	public String eventosToString()
	{
		String res = "";
		Iterator<Evento> eventosIterador = this.eventos.iterator();
		
		while (eventosIterador.hasNext())
			res+= eventosIterador.next().toString();
		
		return res;
	}

}

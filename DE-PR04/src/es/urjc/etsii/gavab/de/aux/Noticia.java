package es.urjc.etsii.gavab.de.aux;

import java.util.ArrayList;
import java.util.Iterator;
import es.urjc.etsii.gavab.de.parsing.ParsingDOM;

public class Noticia 
{
	private String ruta;
	private String titulo;


	private ArrayList<String> entidades;
	
	public Noticia(String ruta)
	{
		this.ruta = ruta;
		this.titulo = "prueba";
		this.entidades = new ArrayList<String>();
		//Hacer parseo para inicializar titulo y lista de entidades
		ParsingDOM.setInfoNoticia(this);
	}
	
	public String getRuta() {
		return ruta;
	}


	public String getTitulo() {
		return titulo;
	}

	public ArrayList<String> getEntidades() {
		return entidades;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void addEntidad(String entidad) {
		this.entidades.add(entidad);
	}
	
	/**
	 * 
	 * Devuelve el nº de entidades iguales en ambas noticias
	 * 
	 * @param otraNoticia	Noticia a comparar
	 * @return	Nº de matches
	 */
	public int getMatches(Noticia otraNoticia){
		int matches = 0;
		Iterator<String> entidadesIterador1 = this.entidades.iterator();
		
		while (entidadesIterador1.hasNext())
		{
			String entidadAux = entidadesIterador1.next();
			Iterator<String> entidadesIterador2 = otraNoticia.entidades.iterator();
			while (entidadesIterador2.hasNext())
			{
				//System.out.println("Comparando " + entidadAux + " con " + entidadesIterador2.next());
				if(entidadAux.equalsIgnoreCase(entidadesIterador2.next()))
					matches++;
			}
		}
		
		
		return matches;
		
	}
	
	public String toString()
	{
		String res = "\nNoticia";
		res += "\n\tRuta = " + this.getRuta();
		res += "\n\tTítulo = " + this.getTitulo();
		
		Iterator<String> entidadesIterador = this.entidades.iterator();
		while (entidadesIterador.hasNext())
			res += "\n\t" + entidadesIterador.next();
		
		return res;

	}

}

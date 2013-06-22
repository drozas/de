package es.urjc.etsii.gavab.de.aux;

import java.util.ArrayList;
import java.util.Iterator;

public class Evento 
{


	private int id;
	private ArrayList<Noticia> noticias = new ArrayList<Noticia>();
	


	public Evento(int id)
	{
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public ArrayList<Noticia> getNoticias() {
		return noticias;
	}
	
	public void addNoticia(Noticia n)
	{
		this.noticias.add(n);
		
	}
	
	public int nNoticias()
	{
		return this.noticias.size();
	}
	
	public String toString()
	{
		String res = "";
		
		res += "Identificador = " + Integer.toString(this.id) + "\n";
		Iterator<Noticia> noticiasIterador = this.noticias.iterator();
		
		while (noticiasIterador.hasNext())
		{
			Noticia n = noticiasIterador.next();
			res+= n.getRuta() + "\t" + n.getTitulo() + "\n";
		}
		
		return res;
	}

}

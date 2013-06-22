package es.urjc.etsii.gavab.de.clustering.algoritmo;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;

import es.urjc.etsii.gavab.de.clustering.xml.Analizar;


public class Noticia implements Comparable<Noticia> {
	
	private String titulo;
	private String name;
	private String asunto;
	private String sumario;
	private String primerParrafo;
	private Boolean analizada;
	
	/**
	 * Constructor que recibe el nombre del fichero de la noticia
	 * 
	 * @param name Nombre del fichero
	 */
	public Noticia(String name)
	{
		this.name = name;
		Analizar analizar = analizar("news/" + this.name);
		this.setTitulo(analizar.getTitle());
		this.setAsunto(analizar.getAsunto());
		this.setSumario(analizar.getSumario());
		this.setPrimerParrafo(analizar.getPrimerParrafo());
		this.analizada = false;
	}
	
	/**
	 * 
	 * @return Primer Parrafo de la noticia
	 */
	public String getPrimerParrafo() {
		return primerParrafo;
	}

	/**
	 * 
	 * @param primerParrafo
	 */
	public void setPrimerParrafo(String primerParrafo) {
		this.primerParrafo = primerParrafo;
	}

	/**
	 * 
	 * @return Nombre de la noticia
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return Si la noticia esta analizada
	 */
	public Boolean getAnalizada() {
		return analizada;
	}


	/**
	 * 
	 * @param analizada
	 */
	public void setAnalizada(Boolean analizada) {
		this.analizada = analizada;
	}



	/**
	 * 
	 * @param path Path del fichero noticia a analizar
	 * @return Objeto Analizar
	 */
	public Analizar analizar(String path)
	{
		Analizar handler = null;
		try 
        {
    		XMLReader parser = new SAXParser();
    		handler = new Analizar();
    		InputSource inputFile = new InputSource(path);
    		inputFile.setEncoding("ISO-8859-1");
    		parser.setContentHandler(handler);
    		parser.parse(inputFile);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        catch (SAXException e) 
        {
            e.printStackTrace();
        }
        return handler;
	}

	/**
	 * 
	 * @return Titulo de la noticia
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * 
	 * @param titulo
	 */
	private void setTitulo(String titulo) 
	{
		this.titulo = titulo;
	}
	
	/**
	 * 
	 * @return Asunto de la noticia
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * 
	 * @param asunto
	 */
	private void setAsunto(String asunto) 
	{
		this.asunto = asunto;
	}
	
	/**
	 * 
	 * @return Sumario de la noticia
	 */
	public String getSumario() {
		return sumario;
	}
	
	/**
	 * 
	 * @param sumario
	 */
	public void setSumario(String sumario) 
	{
		this.sumario = sumario;
	}
	

	
	/**
	 * 
	 * Devuelve un String con toda la información relevante de la noticia
	 * 
	 * @return String con path, titulo y asunto
	 * 
	 * 
	 */
	public String toString()
	{
		String aux = "";
		aux += "Name: " + this.name + "\n";
		aux += "Titulo: " + this.titulo + "\n";
		aux += "Asunto: " + this.asunto + "\n";
		aux += "Sumario: " + this.sumario + "\n";
		aux += "Primer Parrafo: " + this.primerParrafo + "\n";
		return aux;
	}
	
	/**
	 * Obligatoria paras implementar la interfaz Comparable
	 * Se compara por nombre de fichero
	 */
	public int compareTo(Noticia otraNoticia) throws ClassCastException {
		if (!(otraNoticia instanceof Noticia))
			throw new ClassCastException("Se esperaba un objeto Noticia");
			return this.name.compareTo(((Noticia)otraNoticia).getName());    
		  }


}

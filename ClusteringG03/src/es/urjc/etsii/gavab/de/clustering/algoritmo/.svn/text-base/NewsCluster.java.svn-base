package es.urjc.etsii.gavab.de.clustering.algoritmo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsCluster implements Comparable<NewsCluster> {

	// Identificador del cluster
	private int id;

	// Lista con los nombres de los documentos que forman parte del cluster	 
	private List<String> documents = new ArrayList<String>();
	
	private ArrayList<Noticia> noticiasCluster = new ArrayList<Noticia>();
	

	public NewsCluster(int id) {
		this.id = id;
	}
	/**
	 * Se inicializa un cluster con un identificador de cluster y el nombre del 
	 * documento que se a�ade a dicho cluster
	 * 
	 * @param id
	 */
	public NewsCluster(int id, String document) {
		this(id);
		this.documents.add(document);
	}
	
	public NewsCluster(int id, List<String> documents) {
		this(id);
		this.documents = documents;		
	}
	
	/**
	 * 
	 * Constructor que recibe además el objeto Noticia (no se borran los
	 * anteriores por posible compatibilidad con FMeasure)
	 * 
	 * @param id	Número de cluster
	 * @param document	Nombre del fichero
	 * @param n	Objeto completo con la noticia
	 */
	public NewsCluster(int id, String document, Noticia n) {
		this(id);
		this.documents.add(document);
		this.noticiasCluster.add(n);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Cluster: " + this.id + "\n");
		//sb.append("Documentos del cluster\n");
		Iterator<Noticia> noticiaItr = this.noticiasCluster.iterator();
		while(noticiaItr.hasNext())
			sb.append(noticiaItr.next().toString()+ "\n");
		//for (String s : this.documents) {
		//	sb.append(s + "\n");
		//}
		return sb.toString();
	}

	/**
	 * Retorna un valor l�gico indicando si el cluster est� vac�o, es decir, no
	 * contienen ning�n documento
	 * 
	 * @return <code>true</code> si el cluster est� vac�o y <code>false</code>
	 *         en caso contrario
	 */
	public boolean isEmpty() {
		return this.documents.isEmpty();		
	}

	/**
	 * Comprueba si el cluster contiene un determinado documento
	 * 
	 * @param name
	 * @return
	 */
	public boolean contains(String document) {
		return this.documents.contains(document);
	}
	


	/**
	 * Retorna los documentos que contiene el cluster
	 * 
	 * @return
	 */
	public List<String> getDocuments() {
		return this.documents;
	}
	
	/**
	 * 
	 * Método requerido por la interfaz Comparable.
	 * Se compara por los nombres de fichero
	 * 
	 */
	public int compareTo(NewsCluster c) {
		Integer id1=new Integer(this.id);
		Integer id2 = new Integer(c.id);
		
		return id1.compareTo(id2);
	}
	
	/**
	 * Retorna el n�mero de documentos del cluster
	 * @return
	 */
	public int getSize() {
		return this.documents.size();
	}
	
	/**
	 * 
	 * Añade un documento al cluster (deprecado, se mantiene por posible compatibilidad
	 * con FMeasure)
	 * 
	 * @param document	Nombre de fichero
	 */
	public void addDocument(String document) {
		this.documents.add(document);
	}
	
	/**
	 * 
	 * Añade un documento (y su objeto Noticia) al cluster.
	 * 
	 * @param document	Nombre del fichero
	 * @param n	Objeto Noticia
	 */
	public void addDocument(String document, Noticia n) {
		this.documents.add(document);
		this.noticiasCluster.add(n);
	}
	
	/**
	 * 
	 * Devuelve el array de noticias asociado a este cluster.
	 * 
	 * @return	Array de noticias asociado al cluster
	 */
	public ArrayList<Noticia> getNoticiasCluster() {
		return noticiasCluster;
	}
	
	/**
	 * 
	 * Inicializa el campo "analizada" a false, de todas las noticias
	 * de este cluster.
	 * 
	 */
	public void inicializarAnalizada(){
		Iterator<Noticia> noticiaItr = this.noticiasCluster.iterator();
		while(noticiaItr.hasNext())
			noticiaItr.next().setAnalizada(false);
		
	}
	
	/**
	 * Devuelve la primera noticia sin analizar de este cluster. Null si ya están
	 * todas analizadas
	 * 
	 * @return
	 */
	public Noticia getSiguienteNoticia(){
		Iterator<Noticia> noticiaItr = this.noticiasCluster.iterator();
		while(noticiaItr.hasNext())
		{
			Noticia n = noticiaItr.next();
			if (!n.getAnalizada())
				return n;
		}
		
		return null;
	}
	
	/**
	 * 
	 * Recibe el nombre de fichero, y devuelve el objeto de noticia asociado
	 * a dicho identificador
	 * 
	 * @param name	Nombre de fichero
	 * @return	Objeto con la noticia
	 */
	public Noticia getNoticiaByName(String name){
		Iterator<Noticia> noticiaItr = this.noticiasCluster.iterator();
		while(noticiaItr.hasNext())
		{
			Noticia n = noticiaItr.next();
			if (n.getName().equals(name))
				return n;
		}
		
		return null;
		
	}
	
	
}

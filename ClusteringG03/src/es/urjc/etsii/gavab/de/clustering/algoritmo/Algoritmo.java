package es.urjc.etsii.gavab.de.clustering.algoritmo;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import es.urjc.etsii.gavab.de.clustering.lucene.SearchEngine;
import es.urjc.etsii.gavab.de.clustering.measure.FMeasure;
import es.urjc.etsii.gavab.de.clustering.xml.Analizar;
import es.urjc.etsii.gavab.de.clustering.xslt.Transformador;

public class Algoritmo {
	
	private ArrayList<Noticia> noticias = new ArrayList<Noticia>();
	private ArrayList<NewsCluster> clusters = new ArrayList<NewsCluster>();
	private SearchEngine motor; 
	
	public Algoritmo(){}
	
	/**
	 * Inicializa a false los valores de analizadas de todas las noticias
	 * de todos los clusters
	 */
	private void inicializarAnalizadas(ArrayList<NewsCluster> clusters){
		//Mostrar contenido de los clusters creados
		Iterator<NewsCluster> clusterItr = clusters.iterator();
		while (clusterItr.hasNext()) 
			clusterItr.next().inicializarAnalizada();
		
	}
	
	/**
	 * 
	 * Crea el motor de búsqueda y el índice, a partir del array de noticias ordenado.
	 * 
	 * @param path	Path en el que se guarda persistencia del índice
	 * @param noticias	Array de noticias a indexar
	 * @return	Motor de búsqueda con índice listo para hacer búsquedas.
	 * @throws Exception
	 */
	public SearchEngine crearIndice(String path, ArrayList<Noticia> noticias) throws Exception
	{
		System.out.println("Creando motor de busqueda...");
		SearchEngine motor = new SearchEngine(path);
		
		
		System.out.print("Indexando las noticias...");
		//El índice guardará además la posición en el array de noticias (para indexarlas fácilmente después)
		for (int i=0; i<noticias.size(); i++)
		{
			motor.addNoticiaToIndex(noticias.get(i));
	    }
		
		motor.closeIndex();
		System.out.println("ok");
		return motor;
	}
	
	
	/**
	 * 
	 * Crea un cluster inicial con todas las noticias (para inicializar nuestro algoritmo)
	 * 
	 * 
	 * @param motor	Motor de búsqueda
	 * @param clusters	Lista de clusters actual
	 * @param noticias	Lista de noticias actuales
	 * @return	Lista de clusters (con un único cluster en el que estarán todas las noticias)
	 */
	private ArrayList<NewsCluster> inicializacion(SearchEngine motor, ArrayList<NewsCluster> clusters, ArrayList<Noticia> noticias){
		NewsCluster c = new NewsCluster(clusters.size());
		clusters.add(c);
		Iterator<Noticia> itr = noticias.iterator();
		while (itr.hasNext()) 
		{
			Noticia n = itr.next();
			c.addDocument(n.getName(), n);
		}
		
		return clusters;
		
	}
	
	/**
	 * 
	 * Realiza un proceso de filtrado de los clusters actuales (expandiendo).
	 * 
	 * La idea es:
	 * 	- Recibo una lista de clusters, y para cada uno de ellos
	 * 	- Cojo la primera noticia no analizada del cluster, y hago una búsqueda (en función
	 * 	del criterio), y añado todas las noticias devueltas que pertenecen a ese cluster (y no han
	 * sido previamente analizadas).
	 *  - Por tanto, nuestro algoritmo, creará más clusters (refinando) a partir de la lista actual
	 * 
	 * @param motor	Motor de búsqueda
	 * @param clustersAntiguos	Lista de clusters recibida
	 * @param criterio	Criterio por el que hacer la búsqueda
	 * @return	Nueva lista de clusters
	 * @throws Exception
	 */
	private ArrayList<NewsCluster> filtrado(SearchEngine motor, ArrayList<NewsCluster> clustersAntiguos, String criterio) throws Exception{
		//Array auxiliar de cluster
		ArrayList<NewsCluster> clustersAux = new ArrayList<NewsCluster>();
		
		int clusterActual;
		
		inicializarAnalizadas(clustersAntiguos);
		
		for (int i=0; i<clustersAntiguos.size(); i++)
		{
			//Mientras haya noticias sin analizar
			while (clustersAntiguos.get(i).getSiguienteNoticia()!=null)
			{
				
				clusterActual = clustersAux.size();
				Noticia noticiaSinAnalizar = clustersAntiguos.get(i).getSiguienteNoticia();
				//Creamos un nuevo cluster, cuyo identificador será el tamaño actual, con la primera noticia sin clasificar
				NewsCluster c = new NewsCluster(clusterActual, noticiaSinAnalizar.getName(), noticiaSinAnalizar);
				
				//Marcamos esa noticia como analizada
				noticiaSinAnalizar.setAnalizada(true);
				
				//Guardamos el cluster en nuestro nuevo array de clusters
				clustersAux.add(c);
				
				ArrayList<String> noticiasCandidatas = null;
				//Hacemos un búsqueda del sumario en todos los campos
				if (criterio.equalsIgnoreCase("asunto"))
				{
					noticiasCandidatas = motor.searchOneField(noticiaSinAnalizar.getAsunto(), "asunto", false);
				}else if(criterio.equalsIgnoreCase("sumario")){
					noticiasCandidatas = motor.searchOneField(noticiaSinAnalizar.getSumario(), "sumario", false);
				}else if(criterio.equalsIgnoreCase("titulo")){
					noticiasCandidatas = motor.searchOneField(noticiaSinAnalizar.getTitulo(), "titulo", false);
				}else if(criterio.equalsIgnoreCase("primerParrafo")){
					noticiasCandidatas = motor.searchOneField(noticiaSinAnalizar.getPrimerParrafo(), "primerParrafo", false);
				}else if (criterio.equalsIgnoreCase("sumario+primerParrafo")){
					String[] campos = {"sumario", "primerParrafo"};
					noticiasCandidatas = motor.searchMultiField(noticiaSinAnalizar.getSumario(), campos, false);
				}
				/*
				 * Recibiremos todos los identificadores (nombre de fichero) de las noticias candidatas.
				 * Agregamos todas al cluster si:
				 * 	- Están contenidas en el cluster antiguo AND
				 *  - No están contenidas en el cluster nuevo AND
				 *  - No está ya analizada
				 *  - 
				 * 
				 */
				Iterator<String> itr = noticiasCandidatas.iterator();
				while (itr.hasNext()) 
				{
					String noticiaId = itr.next();
					Noticia noticiaAAnalizar = clustersAntiguos.get(i).getNoticiaByName(noticiaId);
					
					//De nuevo: agregamos al cluster, y modificamos su clusterId (si no tiene cluster previo)
					if ( clustersAntiguos.get(i).contains(noticiaId) &&
						!(clustersAux.get(clusterActual).contains(noticiaId)) && 
						!(noticiaAAnalizar.getAnalizada()) )
					{
						//La marcamos como analizada
						noticiaAAnalizar.setAnalizada(true);
						//Y la metemos en el nuevo cluster
						c.addDocument(noticiaAAnalizar.getName(), noticiaAAnalizar);
					}
				}
			}
			
		}
		
		return clustersAux;
	}
		
	
	


	/**
	 * 
	 * Lanza el algorimo de clustering
	 * 
	 */
	public void runAlgoritmo()
	{
		//Creamos un filtro para eliminar ficheros que empicen con "." (Ex.: .svn)
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return !name.startsWith(".");
			}
		};

		//Leemos el directorio, y para cada fichero llamamos al constructor de noticia
		File[] files = new File("news").listFiles(filter);
		for (File f : files) 
		{
			this.noticias.add(new Noticia(f.getName()));
		}
		
		//Ordenamos el array de noticias por nombre (independiente de SO)
		Collections.sort(this.noticias);

		// Creamos el motor de búsqueda, e indexamos todas las noticias
		try 
		{
			this.motor = crearIndice("indexingDirectory", this.noticias); 
			this.clusters = inicializacion(this.motor, this.clusters, this.noticias);
			
			this.clusters = filtrado(this.motor, this.clusters, "sumario+primerParrafo");
			this.clusters = filtrado(this.motor, this.clusters, "primerParrafo");
		
			
			java.util.List<NewsCluster> list = this.clusters;
			float measure = FMeasure.fMeasure(new File("manualSolution"), list, this.noticias.size());
			
			System.out.println("Medida = " + measure);
			
			
			//Creamos el fichero XML
			Analizar analizador = new Analizar();
			analizador.crearXML(this.clusters, measure);
			
			System.out.println("El fichero XML con la informacion se ha creado correctamente");
			
			//Transformamos el primero 
			Transformador t = new Transformador();
			t.transform("out/resultado.xml", "out/resultado.html", "xslt/resultado.xsl");
			
			System.out.println("Los resultados se pueden comprobar en out/resultado.html");
	

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

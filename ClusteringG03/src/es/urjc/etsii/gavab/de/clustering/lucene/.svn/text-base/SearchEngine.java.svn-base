package es.urjc.etsii.gavab.de.clustering.lucene;


import java.io.File;
import java.util.ArrayList;


//import org.apache.lucene.analysis.SimpleAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;

import es.urjc.etsii.gavab.de.clustering.algoritmo.Noticia;

/**
 * 
 * Motor de búsqueda en español, con persistencia en el sistema de ficheros.
 * 
 */
public class SearchEngine {

	// Directorio donde se encuentra o se crear� el �ndice
	private String indexFile = null;
	private IndexWriter index = null;

	/**
	 * 
	 * Construye un motor de búsqueda en español con persistencia 
	 * en sistema de ficheros (en la ruta indicada como parámetro del constructor)
	 * 
	 * @param indexFile	Directorio donde se almacenarán los archivos temporales de indexación
	 * @throws Exception
	 */
	public SearchEngine(String indexFile) throws Exception {
		this.indexFile = indexFile;
		
		File dict = new File("spanishStopList.txt");
		
		//Si ponemos create a true, los documentos se indexarían tantas veces como el indexador es lanzado
		this.index = new IndexWriter(this.indexFile, new SpanishAnalyzer(dict), true, IndexWriter.MaxFieldLength.UNLIMITED);
		
	}



	/**
	 * Realiza una búsqueda sólo en uno de los campos
	 * 
	 * @param q	String con la consulta
	 * @param field	String con el campo a consultar
	 * @param verbose 	Muestra el resultado de la ejecución por la salida estándar
	 * 					si es true (útil para testing)
	 * 
	 * @throws Exception
	 * 
	 */
	public ArrayList<String> searchOneField(String q, String field, boolean verbose) throws Exception {

		// Hay que crearse un objeto que busque sobre el indice
		Searcher searcher = new IndexSearcher(this.indexFile);


		QueryParser singleParser = new QueryParser(field, new SpanishAnalyzer(new File("spanishStopList.txt")));
		Query query = singleParser.parse(q);
	
		// El buscador busca en el índice: devolvemos solo 20
		TopDocs results = searcher.search(query, null, 20);
		
		if(verbose)
		{
			System.out.println("Se ha realizado la query " + q + " en el campo " + field);
			System.out.println("Se han encontrado " + results.totalHits + " documentos");
			for(ScoreDoc result : results.scoreDocs)
			{
				System.out.println("doc=" + result.doc + " score=" + result.score);
				Document doc = searcher.doc(result.doc);
				System.out.println("name=" + doc.getField("name").stringValue());
				System.out.println("titulo=" + doc.getField("titulo").stringValue());
			}
		}
		
		ArrayList<String> aux = new ArrayList<String>();
		//Devolveremos un ArrayList, cuyos identificadores corresponderán el nombre del fichero (sin path)
		for(ScoreDoc result : results.scoreDocs)
		{
			Document doc = searcher.doc(result.doc);
			aux.add(doc.getField("name").stringValue());
		}
		
		return aux;
		


	}
	
	/**
	 * Realiza una búsqueda sólo en varios campos
	 * 
	 * @param q	String con la consulta
	 * @param fields	Array de Strings con todos los campos en los que 
	 * 					se quiere realizar la consulta
	 * @param verbose 	Muestra el resultado de la ejecución por la salida estándar
	 * 					si es true (útil para testing)
	 * 
	 * @throws Exception
	 */
	public ArrayList<String> searchMultiField(String q, String[] fields, boolean verbose) throws Exception {

		// Hay que crearse un objeto que busque sobre el indice
		Searcher searcher = new IndexSearcher(this.indexFile);

		MultiFieldQueryParser multiquery = new MultiFieldQueryParser(fields, new SpanishAnalyzer(new File("spanishStopList.txt")));
		
		Query query = multiquery.parse(q);
		
		// El buscador busca en el indice: devolvemos solo 20
		TopDocs results = searcher.search(query, null, 20);
		
		//Si el modo verboso está activo, mostramos información acerca de los resultados
		if (verbose)
		{
			System.out.print("Se ha realizado la query " + q + " en los campos: ");
			for(int i=0; i<fields.length; i++)
				System.out.print(fields[i] + " ");
			System.out.println();
			
			System.out.println("Se han encontrado " + results.totalHits + " documentos");
			for(ScoreDoc result : results.scoreDocs)
			{
				System.out.println("doc=" + result.doc + " score=" + result.score);
				Document doc = searcher.doc(result.doc);
				System.out.println("path=" + doc.getField("name").stringValue());
				System.out.println("titulo=" + doc.getField("titulo").stringValue());
			}
		}
		
		ArrayList<String> aux = new ArrayList<String>();
		//Devolveremos un ArrayList, cuyos identificadores corresponderán el nombre del fichero (sin path)
		for(ScoreDoc result : results.scoreDocs)
		{
			Document doc = searcher.doc(result.doc);
			aux.add(doc.getField("name").stringValue());
		}
		
		return aux;
		


	}

	/**
	 * 
	 * Agrega una noticia al índice del motor de búsqueda
	 * 
	 * @param noticia
	 * @throws Exception
	 */
	public void addNoticiaToIndex(Noticia noticia) throws Exception {


		// Creando un Document con tres campos: path, titulo y asunto
		Document doc = new Document();
		
		doc.add(new Field("name", noticia.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("titulo", noticia.getTitulo(), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("asunto", noticia.getAsunto(), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("sumario", noticia.getSumario(), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("primerParrafo", noticia.getPrimerParrafo(), Field.Store.NO, Field.Index.ANALYZED));
		this.index.addDocument(doc);
	}
	
	/**
	 * 
	 * Optimizar y cerrar el índice (sólo se puede hacer una vez)
	 * 
	 * @throws Exception
	 */
	public void closeIndex() throws Exception
	{
		// Se optimiza para mejorar el proceso de busqueda
		this.index.optimize();
		// ahora se guardaran todos los documentos indexados
		this.index.close();
	}


}
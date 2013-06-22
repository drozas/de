package motorbusqueda;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

//import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
//import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;

//http://lingpipe-blog.com/2009/02/18/lucene-24-in-60-seconds/

public class BusquedasLucene {

	// Directorio donde se encuentra o se crear� el �ndice
	private String indexFile = null;
	//private IndexWriter index = null;

	public BusquedasLucene(String indexFile) {
		this.indexFile = indexFile;
	}



	/**
	 * Dada una consulta, se hace la b�squeda de los ficheros que se
	 * correspondan con dicha consulta
	 * 
	 * @param query
	 * @throws Exception
	 */
	public void buscarFicheros(String q) throws Exception {

		System.out.println("Se va a realizar una b�squeda");
		// Hay que crearse un objeto que busque sobre el �ndice
		Searcher searcher = new IndexSearcher(this.indexFile);

		// La consulta tambi�n se analiza (se utilizar� el mismo analizador que
		// para los ficheros)
		//QueryParser queryParser = new QueryParser("body",new SimpleAnalyzer());
		QueryParser queryParser = new QueryParser("body",new StandardAnalyzer());

		// Se construye un objeto Query que representar� la consulta
		Query query = queryParser.parse(q);

		
	
		// El buscador busca en el �ndice
		//Hits hits = searcher.search(query);
		TopDocs results = searcher.search(query, null, 100);
		System.out.println("Se han encontrado " + results.totalHits + " documentos");
		for(ScoreDoc result : results.scoreDocs)
		{
			//ScoreDoc scoreDoc = results.scoreDocs[i];
			System.out.println("doc=" + result.doc + " score=" + result.score);
			Document doc = searcher.doc(result.doc);
			System.out.println("path=" + doc.getField("path").stringValue());
		}
		


	}

	public void addDocumentsToIndex(File documentsDirectory)
			throws Exception {
		File[] files = documentsDirectory.listFiles();

		//Si ponemos create a true, los documentos se indexarían tantas veces como el indexador es lanzado
		IndexWriter writer = new IndexWriter(this.indexFile, new StandardAnalyzer(), true, IndexWriter.MaxFieldLength.UNLIMITED);
		
		for (File f : files) {
			System.out.println("Indexing file " + f.getName());
			InputStream is = new FileInputStream(f.getAbsolutePath());
			// Creando un Document con dos campos, uno contiene
			// la ruta del archivo, y el otro el contenido del archivo.
			Document doc = new Document();
			//doc.add(new Field("path", f.getAbsolutePath(), Field.Store.YES,Field.Index.UN_TOKENIZED));
			doc.add(new Field("path", f.getAbsolutePath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("body", ((Reader) new InputStreamReader(is))));
			writer.addDocument(doc);
			is.close();
		}
		// Se optimiza praa mejorar el proceso de b�squeda
		writer.optimize();
		// ahora se guardar�n todos los documentos indexados
		writer.close();
	}

	public static void main(String args[]) {

		// Par�metros cuando se indexa el fichero
		// args[0]: cadena que indica que se van a indexar documentos ("index")
		// args[1]: ruta con el directorio donde se encuentran los documentos a indexar

		// Par�metros cuando se realiza la b�squeda
		// args[0]: cadena que indica que se quiere hacer una b�squeda
		// ("search")
		// args[1]: query (cadena con la consulta)

		BusquedasLucene motor = new BusquedasLucene("indexingDirectory");

		if (args.length < 2) {
			System.out.println("Se necesitan al menos dos par�metros: (1) index o search; (2) cadena con ruta o con query");
			return;
		} else {
			try {
				if (args[0].equalsIgnoreCase("index")) {
					motor.addDocumentsToIndex(new File(args[1]));					
				} else if (args[0].equalsIgnoreCase("search")) {
					String query = "";
					for (int i=1; i<args.length; i++)
						query += args[i] + " ";
					System.out.println("Query: " + query);
					motor.buscarFicheros(query);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
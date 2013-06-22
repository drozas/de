package motorbusqueda.xml;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class SpanishAnalyzer extends Analyzer {

	/**
	 * Contains the stopwords used with the StopFilter.
	 */
	private Set<Object> stopTable = new HashSet<Object>();	

	/**
	 * Builds an analyzer with the given stop words from file.
	 * 
	 * @throws IOException
	 */
	public SpanishAnalyzer(File stopWords) throws IOException {
		stopTable = new HashSet(WordlistLoader.getWordSet(stopWords));		
	}

/**
	 * Constructs a {@link StandardTokenizer} filtered by a {@link 
	 * StandardFilter}, a {@link LowerCaseFilter}, a {@link StopFilter} and a
	 * {@link SpanishStemFilter}.
	 */
	public final TokenStream tokenStream(String fieldName, Reader reader) {
		TokenStream result = new StandardTokenizer(reader);
		result = new StandardFilter(result);
		result = new LowerCaseFilter(result);
		result = new StopFilter(result, stopTable);
		result = new SpanishStemFilter(result);
		return result;
	}

}

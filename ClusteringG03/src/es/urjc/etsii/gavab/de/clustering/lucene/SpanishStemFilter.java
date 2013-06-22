package es.urjc.etsii.gavab.de.clustering.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import org.tartarus.snowball.ext.SpanishStemmer;

public final class SpanishStemFilter extends TokenFilter {

	private SpanishStemmer stemmer;
	private Token token = null;

	public SpanishStemFilter(TokenStream in) {
		super(in);
		stemmer = new SpanishStemmer();
	}

	/** Returns the next input Token, after being stemmed */
	public final Token next() throws IOException {
		Token r = new Token();
		if ((token = input.next(r)) == null) {
			return null;
		} else {
			stemmer.setCurrent(new String(token.termBuffer()));
			stemmer.stem();
			String s = stemmer.getCurrent();
			if (!s.equals(new String(token.termBuffer()))) {
				return new Token(token.startOffset(), token.endOffset(), token
						.type());
			}
			return token;
		}
	}

	/**
	 * Set a alternative/custom Stemmer for this filter.
	 */
	public void setStemmer(SpanishStemmer stemmer) {
		if (stemmer != null) {
			this.stemmer = stemmer;
		}
	}
}

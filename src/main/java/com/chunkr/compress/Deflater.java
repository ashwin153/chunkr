package com.chunkr.compress;

import java.io.OutputStream;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.expressions.Expression;
import com.chunkr.compress.regressors.Regressor;

public class Deflater {

	private Regressor _regressor;
	
	public Deflater(Regressor regressor) {
		_regressor = regressor;
	}
	
	public void deflate(int chunkSize, boolean[] bits, OutputStream output) {
		// Step 1: Chunk the input bits using modified chunking
		Chunker chunker = new ModifiedChunker(chunkSize);
		int[] chunks = chunker.chunk(bits);

		// Step 2: Regress the chunked values into an expression
		Expression expression = _regressor.fit(chunks);

		// Step 3: Create an archive out of the compressed expression
		Archive archive = new Archive(chunkSize, chunks.length, expression);

		// Step 4: Encode the archive into the specified output stream
		new Encoder().write(archive, output);
	}
	
}

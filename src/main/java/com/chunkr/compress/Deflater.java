package com.chunkr.compress;

import java.io.OutputStream;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.expressions.Expression;
import com.chunkr.compress.regressors.Regressor;

public class Deflater {

	private Chunker _chunker;
	private Regressor _regressor;
	private Encoder _encoder;
	
	public Deflater(int chunkSize, Regressor regressor) {
		_chunker = new ModifiedChunker(chunkSize);
		_regressor = regressor;
		_encoder = new Encoder();
	}
	
	public void deflate(boolean[] bits, OutputStream output) {
		// Step 1: Chunk the input bits using modified chunking
		int[] chunks = _chunker.chunk(bits);

		// Step 2: Regress the chunked values into an expression
		Expression expression = _regressor.fit(chunks);

		// Step 3: Create an archive out of the compressed expression
		Archive archive = new Archive(_chunker, expression, chunks.length);

		// Step 4: Encode the archive into the specified output stream
		_encoder.write(archive, output);
	}
	
}

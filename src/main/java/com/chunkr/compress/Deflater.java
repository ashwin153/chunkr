package com.chunkr.compress;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.expressions.Expression;
import com.chunkr.compress.regressors.Regressor;

public class Deflater implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(Deflater.class);

	private Chunker _chunker;
	private Regressor _regressor;
	private Encoder _encoder;
	
	private InputStream _input;
	private OutputStream _output;
	
	public Deflater(InputStream input, OutputStream output, Chunker chunker,
			Regressor regressor, Encoder encoder) {
		
		_input = input;
		_output = output;
		_chunker = chunker;
		_regressor = regressor;
		_encoder = encoder;
	}
	
	@Override
	public void run() {
		try {
			// Step 1: Chunk the input bits using modified chunking
			LOGGER.info("Chunking input bits");
			byte[] bytes = new byte[_input.available()];
			_input.read(bytes);
			
			boolean[] bits = new boolean[bytes.length * 8];
			for(int i = 0; i < bits.length; i++)
				if ((bytes[i / 8] & (1 << (7 - (i % 8)))) > 0)
					bits[i] = true;
			int[] chunks = _chunker.chunk(bits);
	
			// Step 2: Regress the chunked values into an expression
			LOGGER.info("Determining expression");
			Expression expression = _regressor.fit(chunks);
	
			// Step 3: Create an archive out of the compressed expression
			LOGGER.info("Creating archive");
			Archive archive = new Archive(_chunker, expression, chunks.length);
	
			// Step 4: Encode the archive into the specified output stream
			LOGGER.info("Encoding archive");
			_encoder.write(archive, _output);	
		} catch(Exception e) {
			// Catch any exceptions and print a detailed error message + stack trace
			LOGGER.error("Unable to deflate input data");
			LOGGER.debug(e);
		}
	}
	
}

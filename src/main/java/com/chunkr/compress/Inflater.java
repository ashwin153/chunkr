package com.chunkr.compress;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

import com.chunkr.compress.chunkers.StandardChunker;

public class Inflater implements Runnable {
	
	private static final Logger LOGGER = Logger.getLogger(Inflater.class);
	
	private InputStream _input;
	private OutputStream _output;
	private Encoder _encoder;
	
	public Inflater(InputStream input, OutputStream output, Encoder encoder) {
		_input = input;
		_output = output;
		_encoder = encoder;
	}
	
	@Override
	public void run() {
		try {
			// Step 1: Decode the input stream into an archive
			Archive archive = _encoder.read(_input);
			
			// Step 2: Evaluate the input stream at each point in the file
			int[] chunks = new int[archive.getLength()];
			Expression expression = archive.getExpression();
			for(int i = 0; i < chunks.length; i++) {
				BigDecimal value = expression.eval(BigDecimal.valueOf(i));
				BigDecimal round = value.setScale(0, RoundingMode.HALF_UP);
				chunks[i] = round.intValue();
			}
			
			// Step 3: Decode and covert the binary versions of the chunks into bytes
			Chunker standard = new StandardChunker(8);
			Chunker modified = archive.getChunker();
			
			boolean[] unchunks = modified.unchunk(chunks);
			int[] data = standard.chunk(unchunks);
			
			for(int i = 0; i < data.length; i++)
				_output.write(data[i]);
			
			LOGGER.info("Successfully wrote inflated bits to output stream");
		} catch(Exception e) {
			// Catch any exceptions and print a detailed error message + stack trace
			LOGGER.error("Unable to inflate input data");
			LOGGER.debug(e);
		}
	}
	
}

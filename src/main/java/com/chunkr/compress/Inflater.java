package com.chunkr.compress;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.expressions.Expression;

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
			LOGGER.info("Loading archive from input stream");
			Archive archive = _encoder.read(_input);
			
			// Step 2: Evaluate the input stream at each point in the file
			LOGGER.info("Evaluating encoded expression");
			int[] chunks = new int[archive.getLength()];
			Expression expression = archive.getExpression();
			for(int i = 0; i < chunks.length; i++) {
				BigDecimal value = expression.eval(BigDecimal.valueOf(i));
				BigDecimal round = value.setScale(0, RoundingMode.HALF_UP);
				chunks[i] = round.intValue();
			}
			
			// Step 3: Decode and covert the binary versions of the chunks into bytes
			LOGGER.info("Unchunking integer values");
			Chunker chunker = archive.getChunker();
			boolean[] unchunks = chunker.unchunk(chunks);
			
			byte[] bytes = new byte[unchunks.length / 8];
			for(int i = 0; i < bytes.length; i++)
				for(int j = 0; j < 8; j++)
					if(unchunks[i * 8 + j])
						bytes[i] |= (128 >> j);
			
			LOGGER.info("Writing output bytes");
			_output.write(bytes);
		} catch(Exception e) {
			// Catch any exceptions and print a detailed error message + stack trace
			LOGGER.error("Unable to inflate input data");
			LOGGER.debug(e);
		}
	}
	
}

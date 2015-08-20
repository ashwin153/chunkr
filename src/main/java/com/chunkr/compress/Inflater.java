package com.chunkr.compress;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.chunkers.StandardChunker;
import com.chunkr.compress.encoders.Encoder;
import com.chunkr.compress.evaluators.Evaluator;

public class Inflater implements Runnable {
	
	private static final Logger LOGGER = Logger.getLogger(Inflater.class);
	
	private InputStream _input;
	private OutputStream _output;
	private Encoder _encoder;
	private Evaluator _evaluator;
	
	public Inflater(InputStream input, OutputStream output, Encoder encoder, Evaluator evaluator) {
		_input = input;
		_output = output;
		
		_encoder = encoder;
		_evaluator = evaluator;
	}
	
	@Override
	public void run() {
		try {
			// Decode the input stream into an archive and then evaluate
			// contained expression over the length of the archive.
			Archive archive = _encoder.read(_input);
			int[] chunks = _evaluator.eval(archive.getLength(), archive.getExpression());
			
			// Convert the chunks to bits using modified chunking, then covert
			// them into bytes (as ints) using standard chunking. Then write
			// these bytes to the specified output stream.
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

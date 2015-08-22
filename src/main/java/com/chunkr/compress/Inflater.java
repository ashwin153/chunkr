package com.chunkr.compress;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.chunkers.StandardChunker;
import com.chunkr.compress.encoders.Encoder;
import com.chunkr.compress.evaluators.Evaluator;

/**
 * Inflaters are runnable inflation tasks. Inflaters (1) read an archive from an
 * input stream using an encoder, (2) evaluate the expression over the length of
 * the archive using an evaluator, (3) convert these chunks to binary using
 * modified chunking, (4) convert the binary to bytes using standard chunking,
 * (5) write the bytes to an output stream.
 * 
 * @author ashwin
 * @see Deflater
 */
public class Inflater implements Runnable {
	
	private static final Logger LOGGER = Logger.getLogger(Inflater.class);
	
	private InputStream _input;
	private OutputStream _output;
	private Encoder _encoder;
	private Evaluator _evaluator;
	
	/**
	 * Constructs a new Inflater that writes uncompressed versions of the bytes
	 * in the specified input stream to bytes in the specified output stream
	 * using the properties defined by the properties file.
	 * 
	 * @param input
	 * @param output
	 * @param props
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Inflater(InputStream input, OutputStream output, String path)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		_input = input;
		_output = output;
		
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(path);
		props.load(fis);
		fis.close();
		
		_encoder = (Encoder) Class.forName(props.getProperty("inflater.encoder")).newInstance();
		_evaluator = (Evaluator) Class.forName(props.getProperty("inflater.evaluator")).newInstance();
	}
	
	public Inflater(InputStream input, OutputStream output) 
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		this(input, output, "./src/main/resources/inflater.properties");
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
			Chunker standard = new StandardChunker((byte) 8);
			Chunker modified = new ModifiedChunker(archive.getWeights());
			boolean[] unchunks = modified.unchunk(chunks);
			int[] data = standard.chunk(unchunks);
			
			for(int i = 0; i < data.length; i++)
				_output.write(data[i]);
			
			LOGGER.info("Successfully wrote inflated bits to output stream");
		} catch(Exception e) {
			// Catch any exceptions and print a detailed error message + stack trace
			LOGGER.error("Unable to inflate input data");
			LOGGER.debug(e);
			LOGGER.debug(Arrays.toString(e.getStackTrace()));
		}
	}
	
}

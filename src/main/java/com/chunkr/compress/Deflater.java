package com.chunkr.compress;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.chunkers.StandardChunker;
import com.chunkr.compress.encoders.Encoder;
import com.chunkr.compress.evaluators.Evaluator;
import com.chunkr.compress.regressors.Regressor;
import com.chunkr.expressions.Expression;
import com.chunkr.genetics.Population;
import com.chunkr.genetics.chromosomes.Chromosome;
import com.chunkr.genetics.configurations.BitWeightConfiguration;
import com.chunkr.genetics.selectors.Selector;
import com.chunkr.genetics.selectors.TournamentSelector;

/**
 * Deflaters are runnable deflation tasks that are used to compress data.
 * Deflaters perform the following procedure (1) convert the input bytes to
 * binary using standard chunking, (2) convert the binary to chunks using
 * modified chunking, (3) fit an expression to the chunks using a regressor, (4)
 * determine optimal bit weights to maximize compression accuracy by evaluating
 * the expression and running it through a genetic algorithm, (5) construct and
 * encoder an archive and write the bytes to an output stream using an encoder.
 * 
 * @author ashwin
 * @see Inflater
 */
public class Deflater implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(Deflater.class);

	private Regressor _regressor;
	private Encoder _encoder;
	private Evaluator _evaluator;
	
	private InputStream _input;
	private OutputStream _output;
	private int _chunkSize;
	
	public Deflater(int chunkSize, InputStream input, OutputStream output,
			Regressor regressor, Encoder encoder, Evaluator evaluator) {
		
		_input = input;
		_output = output;
		
		_chunkSize = chunkSize;
		_regressor = regressor;
		_encoder = encoder;
		_evaluator = evaluator;
	}
	
	@Override
	public void run() {
		try {
			// Retrieve the input bytes (as ints) from the input stream,
			// convert the input bytes to bits using standard chunking, then
			// chunk them using the modified chunking algorithm.
			int[] data = new int[_input.available()];
			for(int i = 0; i < data.length; i++)
				data[i] = _input.read();
			
			Chunker standard = new StandardChunker((byte) 8);
			Chunker modified = new ModifiedChunker((byte) _chunkSize);
			boolean[] bits = standard.unchunk(data);
			int[] chunks = modified.chunk(bits);

			// Regress the chunks into an evaluable expression; then use a
			// genetic algorithm to determine the optimal bit weights for
			// inflation. Use this optimal chunker and regressed expression to
			// create a compressed archive. Then write the archive to stream.
			Expression expression = _regressor.fit(chunks);
			int[] output = _evaluator.eval(chunks.length, expression);
			BitWeightConfiguration config = new BitWeightConfiguration(_chunkSize, data, output);
			Selector selector = new TournamentSelector(10);
			Population<List<Double>, Double> population = config.getRandomPopulation(1000, selector);
			
			for(int i = 0; i < 100; i++)
				population = population.evolve(0.02, 0.85, 0.10);
			
			Chromosome<List<Double>, Double> best = population.getBestChromosomes(1).get(0);
			Archive archive = new Archive(_chunkSize, chunks.length, best.getGenome(), expression);
			_encoder.write(archive, _output);	
			
			LOGGER.info("Successfully wrote deflated bits to output stream");
		} catch(Exception e) {
			// Catch any exceptions and print a detailed error message + stack trace
			LOGGER.error("Unable to deflate input data");
			LOGGER.debug(e);
		}
	}
	
}

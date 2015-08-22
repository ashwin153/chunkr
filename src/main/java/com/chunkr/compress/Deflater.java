package com.chunkr.compress;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.chunkers.StandardChunker;
import com.chunkr.compress.encoders.Encoder;
import com.chunkr.compress.evaluators.Evaluator;
import com.chunkr.compress.regressors.Regressor;
import com.chunkr.expressions.Expression;
import com.chunkr.genetics.Configuration;
import com.chunkr.genetics.Population;
import com.chunkr.genetics.chromosomes.Chromosome;
import com.chunkr.genetics.chromosomes.DoubleChromosome;
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
	
	/**
	 * Constructs a new deflater that writes compressed versions of the bytes in
	 * the specified input stream to the specified output stream using a
	 * configuration specified by the properties file.
	 * 
	 * @param input input stream
	 * @param output output stream
	 * @param props properties
	 * @throws InstantiationException invalid properties file
	 * @throws IllegalAccessException invalid properties file
	 * @throws ClassNotFoundException invalid properties file
	 */
	public Deflater(InputStream input, OutputStream output, Properties props)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		_input = input;
		_output = output;
		
		_encoder = (Encoder) Class.forName(props.getProperty("deflater.encoder")).newInstance();
		_chunkSize = Integer.valueOf(props.getProperty("deflater.size"));
		_regressor = (Regressor) Class.forName(props.getProperty("deflater.regressor")).newInstance();
		_evaluator = (Evaluator) Class.forName(props.getProperty("deflater.evaluator")).newInstance();
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
			
			WeightConfiguration config = new WeightConfiguration(data, output);
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
	
	/**
	 * A genetic configuration for determining the optimal bit weights for
	 * modified unchunking. This genetic configuration finds the set of weights
	 * that produces bytes that are closest (minimize squared difference) to the
	 * input bytes.
	 * 
	 * @author ashwin
	 * @see Configuration
	 */
	private class WeightConfiguration extends Configuration<List<Double>, Double> {
		
		private int[] _inputs;
		private int[] _chunks;
		
		public WeightConfiguration(int[] inputs, int[] chunks) {
			_inputs = inputs;
			_chunks = chunks;
		}

		@Override
		public BigDecimal getFitness(Chromosome<List<Double>, Double> chromosome) {
			Chunker standard = new StandardChunker(8);
			Chunker modified = new ModifiedChunker(chromosome.getGenome());
			boolean[] bits = modified.unchunk(_chunks);
			int[] outputs  = standard.chunk(bits);
			
			// Fitness is the least squares difference between input and output
			BigDecimal fitness = BigDecimal.ZERO;
			for(int i = 0; i < _inputs.length; i++) {
				double diff = Math.pow(_inputs[i] - outputs[i], 2);
				fitness = fitness.add(BigDecimal.valueOf(diff));
			}
			
			// Population maximizes fitness; to prevent divide by zero errors we add
			// one to all denominators and invert the fraction.
			return BigDecimal.ONE.divide(fitness.add(BigDecimal.ONE),
					new MathContext(25, RoundingMode.HALF_UP));
		}

		@Override
		public Double getRandomGene() {
			return Math.random();
		}

		@Override
		public Double getRandomGene(Double context) {
			// Adds/subtracts a randomized value to the context value. This value is
			// calculated using a power-low function with a randomized bias term.
			int sign = (Math.random() < 0.50) ? 1 : -1;
			int bias = (int) (Math.random() * 6 + 2);
			double delta = Math.pow(Math.random(), bias);
			return context + sign * delta;
		}

		@Override
		public DoubleChromosome getRandomChromosome() {
			List<Double> weights = new ArrayList<Double>(_chunkSize);
			for(int i = 0; i < _chunkSize; i++)
				weights.add(getRandomGene());
			return new DoubleChromosome(weights);
		}
		
	}
}

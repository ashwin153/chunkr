package com.chunkr.compress.deflaters;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.chunkr.compress.Deflater;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.chunkers.StandardChunker;
import com.chunkr.expressions.Expression;
import com.chunkr.expressions.Regressor;
import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;
import com.chunkr.genetics.Population;
import com.chunkr.genetics.chromosomes.DoubleChromosome;
import com.chunkr.genetics.selectors.TournamentSelector;

/**
 * Compressor contains the logic that deflates and inflates sequences of bytes.
 * The compression algorithm relies on the chunking and curve fitting to produce
 * function representations of binary data.
 * 
 * @author ashwin
 */
public class ExpressionDeflater implements Deflater {
		
	private int _chunkSize;
	private Regressor _regressor;
	
	public ExpressionDeflater(int chunkSize, Regressor regressor) {
		_chunkSize = chunkSize;
		_regressor = regressor;
	}
	
	public void deflate(InputStream in, OutputStream out) throws IOException {
		// Retrieve bytes from the input stream
		int[] bytes = new int[in.available()];
		for(int i = 0; i < bytes.length; i++)
			bytes[i] = in.read();
		
		// Group the bytes together to produce integer chunks; regress the
		// chunks into an expression and use it to determine the optimal
		// parameters for modified unchunking using a genetic algorithm
		int[] chunks = new ModifiedChunker(_chunkSize).chunk(new StandardChunker(8).unchunk(bytes));
		Expression expression = _regressor.fit(chunks);
		int[] results  = expression.eval(0, chunks.length, 1);

		WeightConfiguration config = new WeightConfiguration(bytes, results);
		Population<List<Double>, Double> population = config.getRandomPopulation(1000, new TournamentSelector(10));
		for(int i = 0; i < 100; i++)
			population = population.evolve(0.02, 0.85, 0.05);
		List<Double> weights = population.getBestChromosomes(1).get(0).getGenome();
		
		// Write the archive to the specified object output stream
		ObjectOutput output = new ObjectOutputStream(out);
		output.writeByte(_chunkSize);
		output.writeInt(chunks.length);
		
		for(Double weight : weights)
			output.writeDouble(weight);
		
		expression.writeExternal(output);
		output.close();
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
			int[] bytes = new StandardChunker(8).chunk(new ModifiedChunker(
					chromosome.getGenome()).unchunk(_chunks));
			
			// Fitness is the least squares difference between input and output
			BigDecimal fitness = BigDecimal.ZERO;
			for(int i = 0; i < _inputs.length; i++) {
				double diff = Math.pow(_inputs[i] - bytes[i], 2);
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

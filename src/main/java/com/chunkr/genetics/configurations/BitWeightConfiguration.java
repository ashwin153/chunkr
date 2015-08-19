package com.chunkr.genetics.configurations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.chunkr.compress.Chunker;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.chunkers.StandardChunker;
import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;
import com.chunkr.genetics.chromosomes.DoubleChromosome;

public class BitWeightConfiguration extends Configuration<List<Double>, Double> {
	
	private int _chunkSize;
	private int[] _inputs;
	private int[] _chunks;
	
	public BitWeightConfiguration(int chunkSize, int[] inputs, int[] chunks) {
		_chunkSize = chunkSize;
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

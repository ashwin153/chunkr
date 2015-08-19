package com.chunkr.genetics.configurations;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import com.chunkr.genetics.Configuration;
import com.chunkr.genetics.chromosomes.DoubleChromosome;

public class BitWeightConfiguration implements Configuration<DoubleChromosome, Double> {
	
	public BitWeightConfiguration(int chunkSize, byte[] input, int[] chunks) {
		
	}

	@Override
	public Callable<BigDecimal> getFitness(DoubleChromosome chromosome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getRandomGene() {
		return Math.random();
	}

	@Override
	public Double getRandomGene(Double context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleChromosome getRandomChromosome() {
		// TODO Auto-generated method stub
		return null;
	}

}

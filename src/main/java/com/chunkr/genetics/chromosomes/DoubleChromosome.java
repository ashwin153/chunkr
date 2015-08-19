package com.chunkr.genetics.chromosomes;

import java.util.ArrayList;
import java.util.Collection;

import com.chunkr.genetics.configurations.Configuration;

@SuppressWarnings("serial")
public class DoubleChromosome extends ArrayList<Double> implements Chromosome<DoubleChromosome, Double> {

	public DoubleChromosome(Collection<Double> genes) {
		super(genes);
	}
	
	@Override
	public DoubleChromosome crossover(DoubleChromosome mate, double rate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mutate(Configuration<DoubleChromosome, Double> factory, double rate) {
		// TODO Auto-generated method stub
	}

}

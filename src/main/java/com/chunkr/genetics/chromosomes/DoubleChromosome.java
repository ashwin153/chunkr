package com.chunkr.genetics.chromosomes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;

@SuppressWarnings("serial")
public class DoubleChromosome extends ArrayList<Double> implements Chromosome<DoubleChromosome, Double> {

	public DoubleChromosome(Collection<Double> genes) {
		super(genes);
	}
	
	@Override
	public DoubleChromosome crossover(DoubleChromosome mate, double rate) {
		List<Double> child = new ArrayList<Double>();
		for(int i = 0; i < size(); i++)
			if(Math.random() < rate)
				child.add((get(i) + mate.get(i)) / 2.0);
			else
				child.add(get(i));
		return new DoubleChromosome(child);
	}

	@Override
	public void mutate(Configuration<DoubleChromosome, Double> factory, double rate) {
		for(int i = 0; i < size(); i++)
			if(Math.random() < rate)
				set(i, factory.getRandomGene(get(i)));
	}

}

package com.chunkr.genetics.chromosomes;

import java.util.ArrayList;
import java.util.List;

import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;

public class VectorChromosome extends Chromosome<List<Double>, Double> {

	/** Probability that mutation will be completely random */
	private static final double RAND_MUTATION = 0.20;
	
	public VectorChromosome(List<Double> values) {
		super(values);
	}
	
	@Override
	public VectorChromosome crossover(Chromosome<List<Double>, Double> mate, double rate) {
		List<Double> other = mate.genome();
		assert genome().size() == other.size();
		
		List<Double> child = new ArrayList<Double>();
		for(int i = 0; i < genome().size(); i++)
			if(Math.random() < rate)
				child.add((genome().get(i) + other.get(i)) / 2.0);
			else
				child.add(genome().get(i));
		
		return new VectorChromosome(child);
	}

	@Override
	public void mutate(Configuration<List<Double>, Double> config, double rate) {
		for(int i = 0; i < genome().size(); i++) {
			if(Math.random() < rate) {
				if(Math.random() < RAND_MUTATION)
					genome().set(i, config.getRandomGene());
				else
					genome().set(i, config.getRandomGene(genome().get(i)));					
			}
		}
	}
}

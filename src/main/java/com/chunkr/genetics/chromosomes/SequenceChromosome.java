package com.chunkr.genetics.chromosomes;

import java.util.ArrayList;
import java.util.List;

import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;

public class SequenceChromosome<T> extends Chromosome<List<T>, T> {

	/** Probability that mutation will be completely random */
	private static final double MUT_RAND = 0.20;
	
	public SequenceChromosome(List<T> genome) {
		super(genome);
	}
	
	@Override
	public Chromosome<List<T>, T> crossover(
			Chromosome<List<T>, T> mate, double rate) {

		if(Math.random() >= rate) return this;
		List<T> other = mate.genome();
		int r1 = (int) (Math.random() * other.size());
		int r2 = (int) (Math.random() * genome().size());
		List<T> child = new ArrayList<T>(genome().subList(0, r2));
		child.addAll(other.subList(r1, other.size()));
		return new SequenceChromosome<T>(child);
	}

	@Override
	public void mutate(Configuration<List<T>, T> config, double rate) {
		for(int i = 0; i < genome().size(); i++) {
			if(Math.random() < rate) {
				if(Math.random() < MUT_RAND)
					genome().set(i, config.getRandomGene());
				else
					genome().set(i, config.getRandomGene(genome().get(i)));					
			}
		}
	}

}

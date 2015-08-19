package com.chunkr.genetics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class Configuration<T, G> {
	
	public Population<T, G> getRandomPopulation(int size, Selector selector) {
		List<Chromosome<T, G>> chromosomes = new ArrayList<Chromosome<T, G>>();
		for(int i = 0; i < size; i++)
			chromosomes.add(getRandomChromosome());
		return new Population<T, G>(chromosomes, this, selector);
	}

	/**
	 * Returns a reference to a callable object that in turn returns the fitness
	 * of this chromosome; this enables fitness calculations to be parallelized.
	 * 
	 * @return
	 */
	abstract public BigDecimal getFitness(Chromosome<T, G> chromosome);

	/**
	 * Returns a randomly selected gene.
	 * 
	 * @return
	 */
	abstract public G getRandomGene();
	
	/**
	 * Returns a randomly selected gene that uses the specified gene as a
	 * context for creating the new one. The output of this method should be
	 * less random than the output of the no-argument method.
	 * 
	 * @param context
	 * @return
	 */
	abstract public G getRandomGene(G context);
	
	/**
	 * Returns a randomly selected chromosome.
	 * @return
	 */
	abstract public Chromosome<T, G> getRandomChromosome();
	
}

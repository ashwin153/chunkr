package com.chunkr.genetics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Configurations are used to parameterize a problem. They define how
 * chromosomes and the genes that compose them are formed as well as how
 * chromosome fitness is determined.
 * 
 * @author ashwin
 * 
 * @param <T>
 * @param <G>
 */
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
	 * @return fitness
	 */
	abstract public BigDecimal getFitness(Chromosome<T, G> chromosome);

	/**
	 * Returns a randomly selected gene.
	 * 
	 * @return random gene
	 */
	abstract public G getRandomGene();
	
	/**
	 * Returns a randomly selected gene that uses the specified gene as a
	 * context for creating the new one. The output of this method should be
	 * less random than the output of the no-argument method.
	 * 
	 * @param context
	 * @return random gene influenced by context
	 */
	abstract public G getRandomGene(G context);
	
	/**
	 * Returns a randomly selected chromosome.
	 * 
	 * @return random chromosome
	 */
	abstract public Chromosome<T, G> getRandomChromosome();
	
}

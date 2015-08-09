package com.chunkr.compress.regressors.genetics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class GeneticAlgorithm<T extends GeneticChromosome<T>, K> {

	/**
	 * Returns a reference to a callable object that in turn returns the fitness
	 * of this chromosome; this enables fitness calculations to be parallelized.
	 * 
	 * @return
	 */
	abstract public Callable<BigDecimal> getFitness(T chromosome);

	/**
	 * Returns a randomly selected gene.
	 * @return
	 */
	abstract public K getRandomGene();
	
	/**
	 * Returns a randomly selected chromosome.
	 * @return
	 */
	abstract public T getRandomChromosome();
	
	/**
	 * Returns a randomly generated population.
	 * @return
	 */
	public GeneticPopulation<T> getRandomPopulation(int size) {
		List<T> chromosomes = new ArrayList<T>();
		for(int i = 0; i < size; i++)
			chromosomes.add(getRandomChromosome());
		return new GeneticPopulation<T>(this, chromosomes);
	}
	
}

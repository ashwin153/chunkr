package com.chunkr.compress.genetics;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

public abstract class GeneticAlgorithm<T extends GeneticChromosome<T>> {

	/**
	 * Returns a reference to a callable object that in turn returns the fitness
	 * of this chromosome; this enables fitness calculations to be parallelized.
	 * 
	 * @return
	 */
	abstract public Callable<BigDecimal> getFitness(T chromosome);

}

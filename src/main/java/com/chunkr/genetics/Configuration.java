package com.chunkr.genetics;

import java.math.BigDecimal;

public interface Configuration<T, G> {

	/**
	 * Returns a reference to a callable object that in turn returns the fitness
	 * of this chromosome; this enables fitness calculations to be parallelized.
	 * 
	 * @return
	 */
	public BigDecimal getFitness(Chromosome<T, G> chromosome);

	/**
	 * Returns a randomly selected gene.
	 * 
	 * @return
	 */
	public G getRandomGene();
	
	/**
	 * Returns a randomly selected gene that uses the specified gene as a
	 * context for creating the new one. The output of this method should be
	 * less random than the output of the no-argument method.
	 * 
	 * @param context
	 * @return
	 */
	public G getRandomGene(G context);
	
	/**
	 * Returns a randomly selected chromosome.
	 * @return
	 */
	public Chromosome<T, G> getRandomChromosome();
	
}

package com.chunkr.genetics.chromosomes;

import com.chunkr.genetics.Configuration;

public interface Chromosome<T, G> {

	/**
	 * Returns the genome of this chromosome. The genome is the physical object
	 * that this chromosome represents.
	 * 
	 * @return
	 */
	public T getGenome();
	
	/**
	 * Crosses over this chromosome with the specified mate using the specified
	 * crossover rate. Crossover should be non-destructive; therefore, this
	 * chromosome and its mate should be unchanged by this method.
	 * 
	 * @param mate
	 * @param rate
	 * @return
	 */
	public Chromosome<T, G> crossover(Chromosome<T, G> mate, double rate);
	
	/**
	 * Mutates this chromosome with the specified rate. Mutation is destructive;
	 * therefore, this chromosome should be changed by calls to mutate.
	 * 
	 * @param config
	 * @param rate
	 */
	public void mutate(Configuration<T, G> config, double rate);

}

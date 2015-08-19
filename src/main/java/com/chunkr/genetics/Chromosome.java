package com.chunkr.genetics;

public interface Chromosome<C extends Chromosome<C, G>, G> {

	/**
	 * Crosses over this chromosome with the specified mate using the specified
	 * crossover rate. Crossover should be non-destructive; therefore, this
	 * chromosome and its mate should be unchanged by this method.
	 * 
	 * @param mate
	 * @param rate
	 * @return
	 */
	public C crossover(C mate, double rate);
	
	/**
	 * Mutates this chromosome with the specified rate. Mutation is destructive;
	 * therefore, this chromosome should be changed by calls to mutate.
	 * 
	 * @param config
	 * @param rate
	 */
	public void mutate(Configuration<C, G> config, double rate);

}

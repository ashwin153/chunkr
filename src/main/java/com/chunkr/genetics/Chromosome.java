package com.chunkr.genetics;

/**
 * Chromosomes define the crossover and mutation operators for a particular
 * genome and gene type.
 * 
 * @author ashwin
 * 
 * @param <T> genome type
 * @param <G> gene type
 */
public abstract class Chromosome<T, G> {
	
	private T _genome;
	
	public Chromosome(T genome) {
		_genome = genome;
	}

	/**
	 * Returns the genome of this chromosome. The genome is the physical object
	 * that this chromosome represents.
	 * 
	 * @return genome
	 */
	public T genome() {
		return _genome;
	}
	
	/**
	 * Crosses over this chromosome with the specified mate using the specified
	 * crossover rate. Crossover should be non-destructive; therefore, this
	 * chromosome and its mate should be unchanged by this method.
	 * 
	 * @param mate
	 * @param rate
	 * @return child
	 */
	abstract public Chromosome<T, G> crossover(Chromosome<T, G> mate, double rate);
	
	/**
	 * Mutates this chromosome with the specified rate. Mutation is destructive;
	 * therefore, this chromosome can be changed by calls to mutate.
	 * 
	 * @param config
	 * @param rate
	 */
	abstract public void mutate(Configuration<T, G> config, double rate);

}

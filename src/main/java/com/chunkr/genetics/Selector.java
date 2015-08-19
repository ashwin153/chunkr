package com.chunkr.genetics;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Future;

public interface Selector {

	/**
	 * Selects a chromosome from the specified list of chromosomes using the
	 * specified list of fitnesses. The two lists are guaranteed to be in
	 * one-to-one correspondence; therefore, fitnesses.get(0) is the fitness for
	 * chromosomes.get(0). Implementing methods may not alter these lists.
	 * 
	 * @param chromosomes chromosomes
	 * @param fitnesses fitnesses
	 * @return
	 */
	public <C extends Chromosome<C, G>, G> C select(
			List<C> chromosomes, List<Future<BigDecimal>> fitnesses);
	
}

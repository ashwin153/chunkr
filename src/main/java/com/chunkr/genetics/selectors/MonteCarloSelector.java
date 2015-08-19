package com.chunkr.genetics.selectors;

import java.math.BigDecimal;
import java.util.List;

import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Selector;

/**
 * Monte Carlo Selection selects a random individual from the population. It is
 * used as a benchmark for other selection methods; for a selection method to be
 * viable, it must be at least better than Monte Carlo selection.
 * 
 * @author ashwin
 * 
 */
public class MonteCarloSelector implements Selector {

	@Override
	public int select(List<BigDecimal> fitnesses) {
		return (int) (Math.random() * fitnesses.size());
	}

}

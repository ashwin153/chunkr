package com.chunkr.genetics.selectors;

import java.math.BigDecimal;
import java.util.List;

public interface Selector {

	/**
	 * Selects an index from the specified list of fitnesses.
	 * 
	 * @param fitnesses fitnesses
	 * @return
	 */
	public int select(List<BigDecimal> fitnesses);
	
}

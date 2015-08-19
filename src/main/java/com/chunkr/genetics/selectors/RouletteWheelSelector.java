package com.chunkr.genetics.selectors;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Future;

import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Selector;

public class RouletteWheelSelector implements Selector {

	@Override
	public <C extends Chromosome<C, G>, G> C select(List<C> chromosomes, List<Future<BigDecimal>> fitnesses) {
		// TODO Auto-generated method stub
		return null;
	}

}

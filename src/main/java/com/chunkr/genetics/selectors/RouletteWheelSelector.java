package com.chunkr.genetics.selectors;

import java.math.BigDecimal;
import java.util.List;

import com.chunkr.genetics.Selector;

public class RouletteWheelSelector implements Selector {

	@Override
	public int select(List<BigDecimal> fitnesses) {
		// Determine the total fitness
		BigDecimal sum = BigDecimal.ZERO;
		for(BigDecimal fitness : fitnesses)
			sum = sum.add(fitness);
		
		// Fitness proportionate selection
		BigDecimal rand = sum.multiply(BigDecimal.valueOf(Math.random()));
		int index = 0;
		while(rand.compareTo(BigDecimal.ZERO) > 0 && index < fitnesses.size()) {
			rand = rand.subtract(fitnesses.get(index));
			index++;
		}
		
		return index - 1;
	}

}

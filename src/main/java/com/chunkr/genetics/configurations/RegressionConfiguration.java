package com.chunkr.genetics.configurations;

import java.math.BigDecimal;

import com.chunkr.curves.Expression;
import com.chunkr.curves.Operation;
import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;

public class RegressionConfiguration extends Configuration<Expression, Operation> {

	private BigDecimal[] _points;
	
	@Override
	public BigDecimal getFitness(Chromosome<Expression, Operation> chromosome) {
		try {
			// Evaluate the expression at each value in the domain
			BigDecimal beg = BigDecimal.ZERO;
			BigDecimal end = BigDecimal.valueOf(_points.length);
			BigDecimal inc = BigDecimal.ONE;
			BigDecimal[] res = chromosome.genome().eval(beg, end, inc);
			
			// Calculate the sum of squared differences
			BigDecimal ssd = BigDecimal.ZERO;
			for(int i = 0; i < _points.length; i++)
				ssd = ssd.add(res[i].subtract(_points[i]).pow(2));
			
			// Fitness = 1 / (1 + ssd), because population maximizes fitness
			return BigDecimal.ONE.divide(ssd.add(BigDecimal.ONE), Operation.CONTEXT);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Operation getRandomGene() {
		return null;
	}

	@Override
	public Operation getRandomGene(Operation context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chromosome<Expression, Operation> getRandomChromosome() {
		// TODO Auto-generated method stub
		return null;
	}
}

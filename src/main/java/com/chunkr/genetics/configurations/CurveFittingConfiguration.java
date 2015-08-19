package com.chunkr.genetics.configurations;

import java.math.BigDecimal;

import com.chunkr.expressions.Expression;
import com.chunkr.expressions.Operation;
import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;

public class CurveFittingConfiguration extends Configuration<Expression, Operation> {

	@Override
	public BigDecimal getFitness(Chromosome<Expression, Operation> chromosome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Operation getRandomGene() {
		// TODO Auto-generated method stub
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

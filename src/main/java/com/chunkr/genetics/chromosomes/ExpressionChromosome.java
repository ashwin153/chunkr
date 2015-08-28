package com.chunkr.genetics.chromosomes;

import com.chunkr.expressions.Expression;
import com.chunkr.expressions.Operation;
import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;

public class ExpressionChromosome implements Chromosome<Expression, Operation> {

	private Expression _expression;
	
	public ExpressionChromosome(Expression expression) {
		_expression = expression;
	}
	
	@Override
	public Expression getGenome() {
		return _expression;
	}

	@Override
	public Chromosome<Expression, Operation> crossover(
			Chromosome<Expression, Operation> mate, double rate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mutate(Configuration<Expression, Operation> config, double rate) {
		// TODO Auto-generated method stub
		
	}

}

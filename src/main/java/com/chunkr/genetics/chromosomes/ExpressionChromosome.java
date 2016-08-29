package com.chunkr.genetics.chromosomes;

import java.util.List;

import com.chunkr.curves.Operation;
import com.chunkr.curves.expressions.StackExpression;
import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;

public class ExpressionChromosome extends Chromosome<StackExpression, Operation> {

	public ExpressionChromosome(StackExpression expression) {
		super(expression);
	}

	@Override
	public Chromosome<StackExpression, Operation> crossover(
			Chromosome<StackExpression, Operation> mate, double rate) {
		
		if(Math.random() >= rate) return this;
		int r1 = (int) (Math.random() * mate.genome().length());
		int r2 = (int) (Math.random() * genome().length());
		StackExpression rexpr = mate.genome().subexpr(r1);
		return new ExpressionChromosome(genome().replace(r2, rexpr));
	}

	@Override
	public void mutate(Configuration<StackExpression, Operation> config, double rate) {
		List<Operation> operations = genome().getOperations();
		for(int i = 0; i < operations.size(); i++)
			if(Math.random() < rate)
				operations.set(i, config.getRandomGene(operations.get(i)));
	}

}

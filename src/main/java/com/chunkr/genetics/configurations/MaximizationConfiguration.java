package com.chunkr.genetics.configurations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chunkr.curves.Expression;
import com.chunkr.curves.expressions.StackExpression;
import com.chunkr.curves.operations.nullary.Variable;
import com.chunkr.curves.operations.unary.Neg;
import com.chunkr.curves.operations.unary.Pow;
import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;
import com.chunkr.genetics.Population;
import com.chunkr.genetics.chromosomes.VectorChromosome;
import com.chunkr.genetics.selectors.TournamentSelector;

public class MaximizationConfiguration extends Configuration<List<Double>, Double> {

	public static void main(String[] args) {
//		Variable var = new Variable('x');
//		Expression expr = new StackExpression(var, var, new Pow(2), new Neg());
//		
//		MaximizationConfiguration config = new MaximizationConfiguration(expr, -5, 5);
//		Population<?, ?> pop = config.getRandomPopulation(1000, new TournamentSelector(10));
//		for(int i = 0; i < 100; i++)
//			pop = pop.evolve(0.02, 0.85, 0.05);
//		System.out.println(pop.getBestChromosomes(1).get(0).genome());
	}
	
	private Expression _expression;
	private double _beg, _end;
	
	public MaximizationConfiguration(Expression expression, double beg, double end) {
		_expression = expression;
		_beg = beg;
		_end = end;
	}
	
	@Override
	public BigDecimal getFitness(Chromosome<List<Double>, Double> chromosome) {
		try {
			return _expression.eval(BigDecimal.valueOf(chromosome.genome().get(0)));
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Double getRandomGene() {
		return _beg + Math.random() * (_end - _beg);
	}

	@Override
	public Double getRandomGene(Double context) {
		return context + (Math.random() < 0.50 ? 1 : -1) * Math.random()
				* Math.min(context - _beg, _end - context);
	}

	@Override
	public Chromosome<List<Double>, Double> getRandomChromosome() {
		return new VectorChromosome(new ArrayList<Double>(Arrays.asList(getRandomGene())));
	}

}

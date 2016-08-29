package com.chunkr.curves.regressors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.chunkr.curves.Expression;
import com.chunkr.curves.Operation;
import com.chunkr.curves.Regressor;
import com.chunkr.curves.expressions.StackExpression;
import com.chunkr.curves.operations.BinaryOperation;
import com.chunkr.curves.operations.NullaryOperation;
import com.chunkr.curves.operations.UnaryOperation;
import com.chunkr.curves.operations.binary.Add;
import com.chunkr.curves.operations.binary.Mul;
import com.chunkr.curves.operations.binary.Sub;
import com.chunkr.curves.operations.nullary.Constant;
import com.chunkr.curves.operations.nullary.Variable;
import com.chunkr.curves.operations.unary.Abs;
import com.chunkr.curves.operations.unary.Cos;
import com.chunkr.curves.operations.unary.Neg;
import com.chunkr.curves.operations.unary.Pow;
import com.chunkr.curves.operations.unary.Sin;
import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;
import com.chunkr.genetics.Population;
import com.chunkr.genetics.Selector;
import com.chunkr.genetics.chromosomes.ExpressionChromosome;

public class GeneticRegressor extends Regressor {
	
	private int _popSize, _numGen;
	private double _elitismRate, _crossoverRate, _mutationRate;
	private Selector _selector;
	
	public GeneticRegressor(int popSize, int numGen, double elitismRate,
			double crossoverRate, double mutationRate, Selector selector) {
		
		_popSize = popSize;
		_numGen = numGen;
		_elitismRate = elitismRate;
		_crossoverRate = crossoverRate;
		_mutationRate = mutationRate;
		_selector = selector;
	}
	
	@Override
	public Expression fit(double[] points) {
		RegressionConfiguration config = new RegressionConfiguration(points);
		Population<StackExpression, Operation> population = config.getRandomPopulation(_popSize, _selector);
		for(int i = 0; i < _numGen; i++) {
			population = population.evolve(_elitismRate, _crossoverRate, _mutationRate);
			System.out.println(population.getBestChromosomes(1).get(0).genome());
		}
		return population.getBestChromosomes(1).get(0).genome();
	}

	private class RegressionConfiguration extends Configuration<StackExpression, Operation> {

		private double[] _points;
		private Variable _x;
		
		public RegressionConfiguration(double[] points) {
			_points = points;
			_x = new Variable('x');
		}
		
		@Override
		public BigDecimal getFitness(Chromosome<StackExpression, Operation> chromosome) {
			try {
				// Evaluate the function, and calculate the SSD
				BigDecimal beg = BigDecimal.ZERO;
				BigDecimal end = BigDecimal.valueOf(_points.length);
				BigDecimal inc = BigDecimal.ONE;
				BigDecimal[] eval = chromosome.genome().eval(beg, end, inc);
				
				BigDecimal ssd = BigDecimal.ZERO;
				for(int i = 0; i < _points.length; i++)
					ssd = ssd.add(eval[i].subtract(BigDecimal.valueOf(_points[i])).pow(2));
					
				// Population maximizes fitness; to prevent divide by zero errors we add
				// one to all denominators and invert the fraction.
				return BigDecimal.ONE.divide(ssd.add(BigDecimal.ONE), Operation.CONTEXT);
			} catch(Exception e) {
				// If we cannot calculate the fitness of chromosomes, then this
				// genetic algorithm is completely pointless.
				throw new RuntimeException(e);
			}
		}

		@Override
		public Operation getRandomGene() {
			if(Math.random() < 0.25) {
				switch((int) (Math.random() * 1)) {
					case 0: return new Constant(Math.random() * 100);
					default: return _x;
				}
			} else {
				switch((int) (Math.random() * 7)) {
					case 0: return new Add();
					case 1: return new Mul();
					case 2: return new Cos();
					case 3: return new Sin();
					case 4: return new Sub();
					case 5: return new Neg();
					case 6: return new Pow((int) (Math.random() * 5));
					default: return new Abs();
				}
			}
		}

		@Override
		public Operation getRandomGene(Operation context) {
			// Pick an operation of the same type as the context; nullary
			// operations will stay nullary, etc.
			if(context instanceof NullaryOperation) {
				switch((int) (Math.random() * 1)) {
					case 0: return new Constant(Math.pow(Math.random(), 10) * Double.MAX_VALUE);
					default: return _x;
				}
			} else if(context instanceof UnaryOperation) {
				switch((int) (Math.random() * 4)) {
					case 0: return new Abs();
					case 1: return new Cos();
					case 2: return new Neg();
					case 3: return new Pow((int) (Math.random() * 5));
					default: return new Sin();
				}
			} else if(context instanceof BinaryOperation) {
				switch((int) (Math.random() * 2)) {		
					case 0: return new Add();
					case 1: return new Mul();
					default: return new Sub();
				}
			}
			
			// If the gene is not a recognized type, then return a random one
			return getRandomGene();
		}

		@Override
		public Chromosome<StackExpression, Operation> getRandomChromosome() {
			return new ExpressionChromosome(new StackExpression(_x, getRandomOperations()));
		}
		
		private List<Operation> getRandomOperations() {
			List<Operation> operations = new ArrayList<Operation>();
			Operation root = getRandomGene();
			operations.add(0, root);
			for(int i = 0; i < root.arity(); i++)
				operations.addAll(0, getRandomOperations());
			return operations;
		}
	}
}

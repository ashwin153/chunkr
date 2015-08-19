package com.chunkr.genetics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Population<T, G> {

	private List<BigDecimal> _fitnesses;
	private Configuration<T, G> _config;
	private List<Chromosome<T, G>> _chromosomes;
	private Selector _selector;
	
	/**
	 * Constructs a new population from an existing list of chromosomes. This
	 * population is parameterized by the specified configuration and utilizes
	 * the specified selector to choose chromosomes during evolution.
	 * 
	 * @param chromosomes population
	 * @param config configuration
	 * @param selector type of selection mechanism
	 */
	public Population(List<Chromosome<T, G>> chromosomes, Configuration<T, G> config, Selector selector) {
		_chromosomes = chromosomes;
		_config = config;
		_selector = selector;
		
		try {
			// Create parallelized tasks for evaluating the fitness of chromosomes
			List<Callable<BigDecimal>> tasks = new ArrayList<Callable<BigDecimal>>();
			for(final Chromosome<T, G> chromosome : _chromosomes)
				tasks.add(new Callable<BigDecimal>() {
					@Override
					public BigDecimal call() throws Exception {
						return _config.getFitness(chromosome);
					}
				});
			
			// Evaluate the fitness of all chromosomes and retrieve the results
			ExecutorService executor = Executors.newCachedThreadPool();
			_fitnesses = new ArrayList<BigDecimal>();
			for(Future<BigDecimal> fitness : executor.invokeAll(tasks))
				_fitnesses.add(fitness.get());
			executor.shutdownNow();
		} catch(Exception e) {
			// If there is a problem parallelizing the algorithm, then fall back
			// on the slow way of calculating fitness.
			_fitnesses = new ArrayList<BigDecimal>();
			for(Chromosome<T, G> chromosome : _chromosomes)
				_fitnesses.add(_config.getFitness(chromosome));
		}
	}
	
	/**
	 * Returns the best specified number of chromosomes in the population. These
	 * chromosomes will be returned in ascending order (smallest fitness first).
	 * 
	 * @param size
	 * @return
	 */
	public List<Chromosome<T, G>> getBestChromosomes(int size) {
		// Sort the permutation matrix; this allows us to find the best
		// chromosomes without altering the one-to-one correspondence it has
		// with the fitnesses array.
		List<Integer> perm = new ArrayList<Integer>();
		for(int i = 0; i < _chromosomes.size(); i++)
			perm.add(i);
		
		Collections.sort(perm, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return - _fitnesses.get(o1).compareTo(_fitnesses.get(o2));
			}
		});
		
		List<Chromosome<T, G>> best = new ArrayList<Chromosome<T, G>>();
		for(int i = 0; i < size && i < _chromosomes.size(); i++)
			best.add(_chromosomes.get(perm.get(i)));
		return best;
	}
	
	/**
	 * Performs an evolution of this population using the specified rates for
	 * elitism, crossover, and mutation. The evolution process involves
	 * repeatedly selecting chromosomes, crossing them over, and mutating their
	 * children.
	 * 
	 * @param elitismRate
	 * @param crossoverRate
	 * @param mutationRate
	 * @return
	 */
	public Population<T, G> evolve(double elitismRate, double crossoverRate, double mutationRate) {
		List<Chromosome<T, G>> next = new ArrayList<>(_chromosomes.size());
		next.addAll(getBestChromosomes((int) (_chromosomes.size() * elitismRate)));
		
		while(next.size() < _chromosomes.size()) {
			Chromosome<T, G> parent1 = _chromosomes.get(_selector.select(_fitnesses));
			Chromosome<T, G> parent2 = _chromosomes.get(_selector.select(_fitnesses));
			
			Chromosome<T, G> child = parent1.crossover(parent2, crossoverRate);
			child.mutate(_config, mutationRate);
			next.add(child);
		}
		
		return new Population<T, G>(next, _config, _selector);
	}
	
}

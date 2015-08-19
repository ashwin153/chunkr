package com.chunkr.genetics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.chunkr.genetics.chromosomes.Chromosome;
import com.chunkr.genetics.configurations.Configuration;
import com.chunkr.genetics.selectors.Selector;

public class Population<C extends Chromosome<C, G>, G> {

	private List<BigDecimal> _fitnesses;
	private Configuration<C, G> _config;
	private List<C> _chromosomes;
	private Selector _selector;
	
	/**
	 * Constructs a new, randomized population of the specified size that is
	 * parameterized by the specified configuration and utilizes the specified
	 * selector to choose chromosomes during evolution.
	 * 
	 * @param size population size
	 * @param config configuration
	 * @param selector type of selection mechanism
	 */
	public Population(int size, Configuration<C, G> config, Selector selector) {
		_config = config;
		_selector = selector;
		
		_chromosomes = new ArrayList<C>();
		_fitnesses = new ArrayList<BigDecimal>();
		
		for(int i = 0; i < size; i++) {
			C chromosome = _config.getRandomChromosome();
			_chromosomes.add(chromosome);
			_fitnesses.add(_config.getFitness(chromosome));
		}
	}
	
	/**
	 * Constructs a new population from an existing list of chromosomes. This
	 * population is parameterized by the specified configuration and utilizes
	 * the specified selector to choose chromosomes during evolution.
	 * 
	 * @param chromosomes population
	 * @param config configuration
	 * @param selector type of selection mechanism
	 */
	public Population(List<C> chromosomes, Configuration<C, G> config, Selector selector) {
		_chromosomes = chromosomes;
		_config = config;
		_selector = selector;
		
		_fitnesses = new ArrayList<BigDecimal>();
		for(C chromosome : _chromosomes)
			_fitnesses.add(_config.getFitness(chromosome));
	}
	
	/**
	 * Returns the best specified number of chromosomes in the population. These
	 * chromosomes will be returned in ascending order (smallest fitness first).
	 * 
	 * @param size
	 * @return
	 */
	public List<C> getBestChromosomes(int size) {
		// Sort the permutation matrix; this allows us to find the best
		// chromosomes without altering the one-to-one correspondence it has
		// with the fitnesses array.
		List<Integer> perm = new ArrayList<Integer>();
		for(int i = 0; i < _chromosomes.size(); i++)
			perm.add(i);
		
		Collections.sort(perm, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return _fitnesses.get(o1).compareTo(_fitnesses.get(o2));
			}
		});
		
		List<C> best = new ArrayList<C>();
		for(int i = 0; i < size && i < _chromosomes.size(); i++)
			best.add(_chromosomes.get(i));
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
	public Population<C, G> evolve(double elitismRate, double crossoverRate, double mutationRate) {
		List<C> next = new ArrayList<C>(_chromosomes.size());
		next.addAll(getBestChromosomes((int) (_chromosomes.size() * elitismRate)));
		
		while(next.size() < _chromosomes.size()) {
			C parent1 = _chromosomes.get(_selector.select(_fitnesses));
			C parent2 = _chromosomes.get(_selector.select(_fitnesses));
			
			C child = parent1.crossover(parent2, crossoverRate);
			child.mutate(_config, mutationRate);
			next.add(child);
		}
		
		return new Population<C, G>(next, _config, _selector);
	}
	
}

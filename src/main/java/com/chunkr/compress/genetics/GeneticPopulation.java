package com.chunkr.compress.genetics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class GeneticPopulation<T extends GeneticChromosome<T>> {

	private GeneticAlgorithm<T, ?> _algorithm;
	private List<T> _chromosomes;
	
	/**
	 * Constructs a new genetic population. Genetic populations utilize an
	 * executor service to parallelize fitness calculations; which, in turn,
	 * massively increases runtime performance.
	 * 
	 * @param algorithm
	 * @param chromosomes
	 */
	public GeneticPopulation(GeneticAlgorithm<T, ?> algorithm, List<T> chromosomes) { 
		_algorithm = algorithm;
	
		// Sort the list of chromosomes by their fitness; utilizes an executor
		// service to greatly increase runtime performance by parallelizing
		// fitness calculations.
		int threads = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		
		List<Pair<T, Future<BigDecimal>>> sorted = new ArrayList<Pair<T, Future<BigDecimal>>>();
		for(T chromosome : chromosomes) {
			Future<BigDecimal> fitness = executor.submit(_algorithm.getFitness(chromosome));
			sorted.add(new ImmutablePair<T, Future<BigDecimal>>(chromosome, fitness));
		}
		
		Collections.sort(sorted, new Comparator<Pair<T, Future<BigDecimal>>>() {
			@Override
			public int compare(Pair<T, Future<BigDecimal>> o1, Pair<T, Future<BigDecimal>> o2) {
				try {
					BigDecimal f1 = o1.getValue().get();
					BigDecimal f2 = o2.getValue().get();
					return f1.compareTo(f2);
				} catch(Exception e) {
					// If we cannot retrieve the fitness, then we cannot evolve
					// this population; therefore, we throw a runtime error.
					throw new RuntimeException(e);
				}
			}
		});
		
		executor.shutdownNow();
		
		// Put the sorted chromosomes into the population list
		_chromosomes = new ArrayList<T>();
		for(Pair<T, Future<BigDecimal>> entry : sorted)
			_chromosomes.add(entry.getKey());
	}
	
	public GeneticPopulation<T> evolve() {
		return null;
	}
	
	public T select() {
		return null;
	}
	
}

package com.chunkr.genetics.chromosomes;

import java.util.ArrayList;
import java.util.List;

import com.chunkr.genetics.configurations.Configuration;

public class DoubleChromosome implements Chromosome<List<Double>, Double> {

	private List<Double> _values;
	
	public DoubleChromosome(List<Double> values) {
		_values = values;
	}
	
	@Override
	public List<Double> getGenome() {
		return _values;
	}
	
	@Override
	public DoubleChromosome crossover(Chromosome<List<Double>, Double> mate, double rate) {
		List<Double> other = mate.getGenome();
		assert _values.size() == other.size();
		
		List<Double> child = new ArrayList<Double>();
		for(int i = 0; i < _values.size(); i++)
			if(Math.random() < rate)
				child.add((_values.get(i) + other.get(i)) / 2.0);
			else
				child.add(_values.get(i));
		return new DoubleChromosome(child);
	}

	@Override
	public void mutate(Configuration<List<Double>, Double> factory, double rate) {
		for(int i = 0; i < _values.size(); i++)
			if(Math.random() < rate)
				_values.set(i, factory.getRandomGene(_values.get(i)));
	}

}

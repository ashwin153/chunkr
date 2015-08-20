package com.chunkr.genetics.configurations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import org.junit.Test;

import com.chunkr.genetics.Population;
import com.chunkr.genetics.chromosomes.Chromosome;
import com.chunkr.genetics.selectors.TournamentSelector;

public class BitWeightConfigurationTest {

	@Test
	public void testConfiguration() {
		// Input bytes for "Is this the real life? Or is this just fantasy?"
		int[] input = new int[] { 73, 115, 32, 116, 104, 105, 115, 32, 116,
				104, 101, 32, 114, 101, 97, 108, 32, 108, 105, 102, 101, 63,
				32, 79, 114, 32, 105, 115, 32, 116, 104, 105, 115, 32, 106,
				117, 115, 116, 32, 102, 97, 110, 116, 97, 115, 121, 63 };
		
		// Actual chunks for input bytes
		int[] chunks = new int[] { 18, 36, 9, 18, 37, 11, 23, 46, 28, 57, 51,
				38, 12, 25, 50, 36, 8, 16, 32, 0, 1, 3, 7, 14, 29, 58, 52, 40,
				17, 35, 6, 13, 26, 52, 40, 16, 33, 3, 6, 13, 26, 52, 41, 18,
				37, 11, 23, 46, 28, 57, 51, 38, 12, 25, 50, 36, 8, 16, 32, 0,
				1, 3, 7, 14, 29, 58, 52, 40, 17, 35, 6, 13, 26, 52, 40, 16, 33,
				3, 6, 12, 25, 50, 37, 10, 20, 41, 18, 36, 8, 16, 32, 0, 1, 3,
				7, 14, 28, 57, 50, 36, 9, 19, 38, 12, 25, 50, 37, 10, 21, 43,
				22, 44, 24, 48, 33, 2, 5, 11, 22, 45, 27, 54, 44, 24, 48, 33,
				2, 4, 8, 16, 32, 0, 1, 3, 6, 13, 27, 54, 44, 24, 49, 35, 6, 13,
				26, 52, 41, 18, 37, 11, 22, 44, 25, 51, 38, 12, 25, 51, 38, 12,
				25, 50, 37, 10, 20, 41, 19, 39, 15, 31, 63, 62, 60, 57, 50, 36,
				8, 16, 32, 0, 1, 2, 4, 9, 19, 39, 15, 30, 61, 59, 55, 46, 28,
				57, 50, 36, 8, 17, 34, 4, 8, 16, 32, 0, 1, 3, 6, 13, 26, 52,
				41, 18, 37, 11, 23, 46, 28, 57, 51, 38, 12, 25, 50, 36, 8, 16,
				32, 0, 1, 3, 7, 14, 29, 58, 52, 40, 17, 35, 6, 13, 26, 52, 40,
				16, 33, 3, 6, 13, 26, 52, 41, 18, 37, 11, 23, 46, 28, 57, 51,
				38, 12, 25, 50, 36, 8, 16, 32, 0, 1, 3, 6, 13, 26, 53, 42, 20,
				41, 19, 39, 14, 29, 58, 53, 42, 21, 43, 23, 46, 28, 57, 51, 38,
				13, 27, 55, 46, 29, 58, 52, 40, 16, 33, 2, 4, 8, 16, 32, 0, 1,
				3, 6, 12, 25, 51, 38, 12, 25, 51, 38, 12, 24, 48, 33, 2, 5, 11,
				22, 45, 27, 55, 46, 28, 57, 51, 39, 14, 29, 58, 52, 40, 17, 35,
				6, 12, 24, 48, 33, 2, 5, 11, 23, 46, 28, 57, 51, 38, 13, 27,
				55, 47, 30, 60, 57, 50, 36, 9, 19, 39, 15, 31, 63 };
		
		// Perturb the actual chunks
		for(int i = 0; i < chunks.length; i++) {
			int sign = (Math.random() < 0.5) ? 1 : -1;
			int delt = (int) (Math.random() * 10);
			chunks[i] = chunks[i] + sign * delt;
		}
		
		BitWeightConfiguration config = new BitWeightConfiguration(6, input, chunks);
		Population<List<Double>, Double> population = config.getRandomPopulation(1000, new TournamentSelector(5));

		for(int i = 0; i < 100; i++)
			population = population.evolve(0.02, 0.80, 0.10);
		
		Chromosome<List<Double>, Double> best = population.getBestChromosomes(1).get(0);
		System.out.println(best.getGenome());
		System.out.println(BigDecimal.ONE.divide(config.getFitness(best), new MathContext(20, RoundingMode.HALF_UP)).subtract(BigDecimal.ONE));
	}
	
}

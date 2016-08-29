package com.chunkr.genetics.configurations;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.chunkr.genetics.Chromosome;
import com.chunkr.genetics.Configuration;
import com.chunkr.genetics.Population;
import com.chunkr.genetics.chromosomes.SequenceChromosome;
import com.chunkr.genetics.selectors.TournamentSelector;

/**
 * This genetic algorithm evolves optimal paths through a maze. Path fitness is
 * determined by (1) proximity to the end and (2) length of path.
 * 
 * @author ashwin
 * 
 */
public class MazeConfiguration extends Configuration<List<Integer>, Integer> {

	public static void main(String[] args) {
		int[][] maze = new int[][] {
				new int[] {3, 3, 3, 3, 3, 3, 3, 3},
				new int[] {1, 0, 3, 0, 0, 0, 3, 3},
				new int[] {3, 0, 3, 0, 3, 0, 3, 2},
				new int[] {3, 0, 3, 0, 3, 0, 3, 0},
				new int[] {3, 0, 0, 0, 3, 0, 0, 0},
				new int[] {3, 3, 3, 3, 3, 3, 3, 3}
		};
		
		MazeConfiguration config = new MazeConfiguration(maze);
		Population<List<Integer>, Integer> pop = config.getRandomPopulation(1000, new TournamentSelector(10));
		for(int i = 0; i < 100; i++)
			pop = pop.evolve(0.02, 0.85, 0.05);
		System.out.println(config.getPath(pop.getBestChromosomes(1).get(0).genome()));
	}
	
	private int[][] _maze;
	private Point _start, _end;
	
	public MazeConfiguration(int[][] maze) {
		_maze = maze;
		
		// Locate the starting and ending points
		for(int i = 0; i < _maze.length; i++)
			for(int j = 0; j < _maze[i].length; j++)
				if(_maze[i][j] == 2)
					_start = new Point(i, j);
				else if(_maze[i][j] == 1)
					_end = new Point(i, j);
	}
	
	@Override
	public BigDecimal getFitness(Chromosome<List<Integer>, Integer> chromosome) {
		List<Point> path = getPath(chromosome.genome());
		Point last = path.get(path.size() - 1);
		int invalid = chromosome.genome().size() - path.size();
		
		// Penalize invalid instructions, so that the algorithm does not get
		// caught in false minima. Chromosomes who are closer to the end will
		// obviously have lower fitness scores.
		double fitness = last.distance(_end) + 4.0 * invalid / chromosome.genome().size();
		return BigDecimal.valueOf(1 / (1 + fitness));
	}

	@Override
	public Integer getRandomGene() {
		return (int) (Math.random() * 4);
	}

	@Override
	public Integer getRandomGene(Integer context) {
		return ((context + (Math.random() < 0.5 ? 1 : -1)) % 4 + 4) % 4;
	}

	@Override
	public Chromosome<List<Integer>, Integer> getRandomChromosome() {
		List<Integer> genome = new ArrayList<Integer>();
		int size = (int) (Math.random() * _maze.length * _maze[0].length) + 1;
		for(int i = 0; i < size * size; i++)
			genome.add(getRandomGene());
		return new SequenceChromosome<Integer>(genome);
	}

	private List<Point> getPath(List<Integer> genome) {
		List<Point> path = new ArrayList<Point>();
		path.add(_start);
		
		// Try to move in the direction of the genome values. If unable to move,
		// then disregard the instruction 0 -> North, 1 -> East, 2 -> South, 3 -> West.
		for(Integer value : genome) {
			Point cur = path.get(path.size() - 1);
			if(value == 0 && cur.x-1 >= 0 && _maze[cur.x-1][cur.y] <= 1)
				path.add(new Point(cur.x-1, cur.y));
			else if(value == 1 && cur.y+1 < _maze.length && _maze[cur.x][cur.y+1] <= 1)
				path.add(new Point(cur.x, cur.y+1));
			else if(value == 2 && cur.x+1 < _maze[0].length && _maze[cur.x+1][cur.y] <= 1)
				path.add(new Point(cur.x+1, cur.y));
			else if(value == 3 && cur.y-1 >= 0 && _maze[cur.x][cur.y-1] <= 1)
				path.add(new Point(cur.x, cur.y-1));
			if(path.equals(_end)) break;
		}
		
		return path;
	}
}

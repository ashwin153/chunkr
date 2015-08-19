package com.chunkr.genetics.selectors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TournamentSelector implements Selector {

	private int _tournamentSize;
	
	public TournamentSelector(int tournamentSize) {
		_tournamentSize = tournamentSize;
	}
	
	@Override
	public int select(List<BigDecimal> fitnesses) {
		// Create a permutation list and randomize it
		List<Integer> perm = new ArrayList<Integer>();
		for(int i = 0; i < fitnesses.size(); i++)
			perm.add(i);
		Collections.shuffle(perm);
		
		// Find the chromosome in the first "tournamentSize" elements with the
		// greatest fitness; this is the winner of the tournament.
		int winner = perm.get(0);
		for(int i = 1; i < _tournamentSize && i < perm.size(); i++)
			if (fitnesses.get(perm.get(i)).compareTo(
					fitnesses.get(perm.get(winner))) > 0)
				winner = i;
		return winner;
	}

}

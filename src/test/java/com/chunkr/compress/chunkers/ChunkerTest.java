package com.chunkr.compress.chunkers;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChunkerTest {
	
	@Test
	public void testUnchunk() {
		Chunker chunker = mock(Chunker.class, CALLS_REAL_METHODS);
		when(chunker.getChunkSize()).thenReturn(4);
		
		boolean[] unchunks = new boolean[] { true, false, true, false };
		int chunk = 10;
		assertArrayEquals(unchunks, chunker.unchunk(chunk));
	}
	
//	@Test
//	public void testModifiedChunker() {		
//		for(int chunkSize = 1; chunkSize <= 12; chunkSize ++) {
//			// Generate a randomized input sequence of 1000 chunks
//			boolean[] input = new boolean[10000 * chunkSize];
//			for(int i = 0; i < input.length; i++)
//				input[i] = (Math.random() < 0.50) ? true : false;
//			
//			Chunker chunker = new ModifiedChunker(chunkSize);
//			int[] chunked = chunker.chunk(input);
//			for(double marginOfError = 0.0; marginOfError <= 0.10; marginOfError += 0.025) {
//				// Randomly alter chunked elements within the marginOfError;
//				// this is to simulate the curve fitting algorithm's imprecise
//				// calculations.
//				int[] altered = new int[chunked.length];
//				for(int i = 0; i < altered.length; i++) {
//					int sgn = (Math.random() < 0.5) ? 1 : -1;
//					int val = (int) (Math.random() * Math.ceil(Math.pow(2, chunkSize) * marginOfError + 1));
//					altered[i] = chunked[i] + sgn * val;
//
//					if(altered[i] < 0)
//						altered[i] = 0;
//					else if(altered[i] >= Math.pow(2, chunkSize))
//						altered[i] = (int) Math.pow(2, chunkSize) - 1;
//				}
//				
//				// Unchunk the altered data points and calculate the total
//				// number of bit flips between the altered unchunked data and
//				// the original input data.
//				boolean[] unchunked = chunker.unchunk(altered);
//				int bitFlips = 0;
//				for(int i = 0; i < unchunked.length; i++)
//					if(unchunked[i] != input[i])
//						bitFlips++;
//				
//				System.out.println(String.format("%3d\t%5.3f\t%20d\t%15.3f",
//						chunkSize, marginOfError, bitFlips, (double) bitFlips
//								/ input.length));
//			}
//		}
//	}
	
}

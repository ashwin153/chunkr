package com.chunkr.compress.chunkers;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class ChunkerTest {
	
	@Test
	public void testUnchunk() {
		Chunker chunker = mock(Chunker.class, CALLS_REAL_METHODS);
		when(chunker.getChunkSize()).thenReturn(4);
		
		boolean[] unchunks = new boolean[] { true, false, true, false };
		int chunk = 10;
		assertArrayEquals(unchunks, chunker.unchunk(chunk));
	}
	
}

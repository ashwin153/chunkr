package com.chunkr.compress.chunkers;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.chunkr.compress.Chunker;

public class StandardChunkerTest {

	@Test
	public void testChunk() {
		boolean[] unchunks = new boolean[] { true, false, true, true, false, true };
		int[] chunks = new int[] { 5, 5 };
		
		Chunker chunker = new StandardChunker(3);
		assertArrayEquals(chunks, chunker.chunk(unchunks));
	}
	
	@Test
	public void testUnchunk() {
		boolean[] unchunks = new boolean[] { true, false, true, true, false, true };
		int[] chunks = new int[] { 5, 5 };
		
		Chunker chunker = new StandardChunker(3);
		assertArrayEquals(unchunks, chunker.unchunk(chunks));
	}
}

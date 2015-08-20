package com.chunkr.compress.chunkers;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ModifiedChunkerTest {

	@Test
	public void testChunk() {
		boolean[] unchunks = new boolean[] { true, false, true, true, false, true };
		int[] chunks = new int[] { 5, 3, 6, 5 };
		
		Chunker chunker = new ModifiedChunker((byte) 3);
		assertArrayEquals(chunks, chunker.chunk(unchunks));
	}
	
	@Test
	public void testUnchunk() {
		boolean[] unchunks = new boolean[] { true, false, true, true, false, true };
		int[] chunks = new int[] { 5, 3, 6, 5 };
		
		Chunker chunker = new ModifiedChunker((byte) 3);
		assertArrayEquals(unchunks, chunker.unchunk(chunks));
	}
}

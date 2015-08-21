package com.chunkr.compress.chunkers;

import java.util.Arrays;

/**
 * Implementation of the standard chunking algorithm. The standard chunking
 * algorithm groups each "chunkSize" block of bits together. This is what
 * computers use to group bits into bytes.
 * 
 * @author ashwin
 * @see Chunker
 */
public class StandardChunker extends Chunker {

	public StandardChunker(int chunkSize) {
		super(chunkSize);
	}
	
	@Override
	public int[] chunk(boolean[] bits) {
		int[] chunks = new int[bits.length / getChunkSize()];
		for(int i = 0; i < chunks.length; i++) {
			// Retrieve the next chunk of bits
			int index = i * getChunkSize();
			boolean[] chunk = Arrays.copyOfRange(bits, index, index + getChunkSize());
						
			// Convert the chunk into an integer and store it in the array
			for(int j = 0; j < getChunkSize(); j++)
				chunks[i] = (chunks[i] << 1) + (chunk[j] ? 1 : 0);
		}
		
		return chunks;
	}
	
	@Override
	public boolean[] unchunk(int[] chunks) {
		boolean[] bits = new boolean[chunks.length * getChunkSize()];
		for(int i = 0; i < chunks.length; i++)
			System.arraycopy(unchunk(chunks[i]), 0, bits, i * getChunkSize(), getChunkSize());
		return bits;
	}
	
}

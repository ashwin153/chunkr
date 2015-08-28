package com.chunkr.compress.chunkers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chunkr.compress.Chunker;

/**
 * Implementation of the special "modified" chunking algorithm. The modified
 * chunking exploits the locality of bits to recover original unchunked data
 * even if the chunked values are altered slightly.
 * 
 * @author ashwin
 * @see Chunker
 */
public class ModifiedChunker extends Chunker {
	
	private List<Double> _weights;
	
	/**
	 * Constructs a new ModifiedChunker using the default bit weightings. By
	 * default, the bit weights are uniform; therefore, the most significant bit
	 * will have the same weight as the least significant bit.
	 * 
	 * @param chunkSize size of chunks
	 */
	public ModifiedChunker(int chunkSize) {
		super(chunkSize);
		
		_weights = new ArrayList<Double>(chunkSize);
		for(int i = 0; i < chunkSize; i++)
			_weights.add(1.0);
	}
	
	/**
	 * Constructs a new ModifiedChunker using the specified weights. This
	 * enables us to determine the optimal set of bit weights to recover as much
	 * of the original data as possible.
	 * 
	 * @param weights bit weights
	 */
	public ModifiedChunker(List<Double> weights) {
		super(weights.size());
		_weights = weights;
	}
	
	@Override
	public int[] chunk(boolean[] bits) {
		int[] chunks = new int[bits.length - getChunkSize() + 1];
		for(int i = 0; i < chunks.length; i++) {
			// Retrieve the next chunk of bits
			boolean[] chunk = Arrays.copyOfRange(bits, i, i + getChunkSize());
			
			// Convert the chunk into an integer and store it in the array
			for(int j = 0; j < getChunkSize(); j++)
				chunks[i] = (chunks[i] << 1) + (chunk[j] ? 1 : 0);
		}
		
		return chunks;
	}
	
	@Override
	public boolean[] unchunk(int[] chunks) {
		// Decode all of the integers into bits and store them in a matrix
		boolean[][] unchunks = new boolean[chunks.length][getChunkSize()];
		for(int i = 0; i < unchunks.length; i++)
			System.arraycopy(unchunk(chunks[i]), 0, unchunks[i], 0, getChunkSize());
		
		boolean[] output = new boolean[chunks.length + getChunkSize() - 1];
		for(int i = 0; i < output.length; i++) {
			// Calculate the total number of 1's in the ith column. This for
			// loop is the result of too much fucking testing. Don't fuck with
			// this. It's magic.
			double total = 0.0, ones = 0.0;
			for (int chunk = Math.min(i, unchunks.length - 1), index = (i < unchunks.length) ? 0
					: i - unchunks.length + 1; chunk >= 0
					&& chunk > i - getChunkSize() && index <= i
					&& index < getChunkSize(); chunk--, index++) {
				
				if(unchunks[chunk][index])
					ones += _weights.get(index);
				total += _weights.get(index);
			}
			
			// If more than half the chunks have a '1', then set the value of
			// the output bit to true; otherwise, set the value to false.
			output[i] = (ones / total > 0.5) ? true : false;
		}
		
		return output;
	}
}

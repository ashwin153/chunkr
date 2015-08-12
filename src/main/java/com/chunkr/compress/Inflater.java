package com.chunkr.compress;

import java.io.InputStream;
import java.math.BigDecimal;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.chunkers.ModifiedChunker;

public class Inflater {
	
	public boolean[] inflate(InputStream input) {
		// Step 1: Decode the input stream into an archive
		Archive archive = new Encoder().read(input);
		
		// Step 2: Evaluate the input stream at each point in the file
		int[] chunks = new int[archive.getLength()];
		for(int i = 0; i < chunks.length; i++)
			chunks[i] = archive.getExpression().eval(BigDecimal.valueOf(i)).intValue();
		
		// Step 3: Decode and return the binary versions of the chunks
		Chunker chunker = new ModifiedChunker(archive.getChunkSize());
		return chunker.unchunk(chunks);
	}
	
}

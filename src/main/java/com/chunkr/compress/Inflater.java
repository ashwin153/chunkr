package com.chunkr.compress;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.chunkr.compress.expressions.Expression;

public class Inflater {
	
	private Encoder _encoder;
	
	public Inflater() {
		_encoder = new Encoder();
	}
	
	public boolean[] inflate(InputStream input) {
		// Step 1: Decode the input stream into an archive
		Archive archive = _encoder.read(input);
		
		// Step 2: Evaluate the input stream at each point in the file
		int[] chunks = new int[archive.getLength()];
		Expression expression = archive.getExpression();
		for(int i = 0; i < chunks.length; i++) {
			BigDecimal value = expression.eval(BigDecimal.valueOf(i));
			BigDecimal round = value.setScale(0, RoundingMode.HALF_UP);
			chunks[i] = round.intValue();
		}
		
		// Step 3: Decode and return the binary versions of the chunks
		return archive.getChunker().unchunk(chunks);
	}
	
}

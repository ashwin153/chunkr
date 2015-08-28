package com.chunkr.compress.inflaters;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.chunkr.compress.Inflater;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.chunkers.StandardChunker;
import com.chunkr.expressions.Expression;

public class ExpressionInflater implements Inflater {

	@Override
	public void inflate(InputStream in, OutputStream out) throws IOException, ClassNotFoundException {
		// Read the archive from the specified object input stream
		ObjectInput input = new ObjectInputStream(in);
		int chunkSize = input.readByte();
		int length = input.readInt();
		
		List<Double> weights = new ArrayList<>(chunkSize);
		for(int i = 0; i < chunkSize; i++)
			weights.add(input.readDouble());
		
		Expression expression = new Expression(null, null);
		expression.readExternal(input);
		input.close();
		
		// Evaluate the expression at each point in the file and ungroup the
		// integer chunks back into bytes; write these bytes to stream
		int[] results = expression.eval(0, length, 1);
		int[] bytes = new StandardChunker(8).chunk(new ModifiedChunker(weights).unchunk(results));
		for(int i = 0; i < bytes.length; i++)
			out.write(bytes[i]);
	}
	
}

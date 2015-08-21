package com.chunkr.compress;

import java.util.List;

import com.chunkr.compress.encoders.Encoder;
import com.chunkr.expressions.Expression;

/**
 * This class represents a compressed archive. Archives contain decompression
 * parameters (e.x., chunk size, length, etc.) and an evaluable expression.
 * Archives are immutable.
 * 
 * @author ashwin
 * @see Encoder
 */
public class Archive {

	private List<Double> _weights;
	private int _chunkSize, _length;
	private Expression _expression;
	
	public Archive(int chunkSize, int length, List<Double> weights, Expression expression) {
		_chunkSize = chunkSize;
		_length = length;
		_expression = expression;
		_weights = weights;
	}
	
	public int getChunkSize() {
		return _chunkSize;
	}
	
	public int getLength() {
		return _length;
	}
	
	public List<Double> getWeights() {
		return _weights;
	}
	
	public Expression getExpression() {
		return _expression;
	}
	
}

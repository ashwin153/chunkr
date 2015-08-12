package com.chunkr.compress;

import com.chunkr.compress.expressions.Expression;


/**
 * 
 * @author ashwin
 *
 */
public class Archive {

	private int _chunkSize, _length;
	private Expression _expression;
	
	public Archive(int chunkSize, int length, Expression expression) {
		_chunkSize = chunkSize;
		_length = length;
		_expression = expression;
	}
	
	public int getChunkSize() {
		return _chunkSize;
	}
	
	public int getLength() {
		return _length;
	}
	
	public Expression getExpression() {
		return _expression;
	}
	
}

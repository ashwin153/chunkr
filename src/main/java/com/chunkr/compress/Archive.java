package com.chunkr.compress;



/**
 * This class represents a compressed archive. Archives contain decompression
 * parameters (e.x., chunk size, length, etc.) and an evaluable expression.
 * Archives are immutable.
 * 
 * @author ashwin
 */
public class Archive {

	private Chunker _chunker;
	private int _length;
	private Expression _expression;
	
	public Archive(Chunker chunker, Expression expression, int length) {
		_chunker = chunker;
		_length = length;
		_expression = expression;
	}
	
	public Chunker getChunker() {
		return _chunker;
	}
	
	public Expression getExpression() {
		return _expression;
	}
	
	public int getLength() {
		return _length;
	}
	
}

package com.chunkr.compress.expressions.operations;

import com.chunkr.compress.expressions.Operation;

/**
 * Unary operations take a single argument and produce a single output. Some
 * examples of unary operations are the trigonometric functions and absolute
 * values.
 * 
 * @author ashwin
 * 
 */
public abstract class UnaryOperation implements Operation {

	@Override
	public int arity() {
		return 1;
	}
	
}
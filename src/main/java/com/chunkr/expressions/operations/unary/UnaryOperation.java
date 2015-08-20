package com.chunkr.expressions.operations.unary;

import com.chunkr.expressions.operations.Operation;


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
	public final int arity() {
		return 1;
	}
	
}

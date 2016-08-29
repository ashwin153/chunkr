package com.chunkr.curves.operations;

import com.chunkr.curves.Operation;

/**
 * Unary operations take a single argument and produce a single output. Some
 * examples of unary operations are the trigonometric functions and absolute
 * values.
 * 
 * @author ashwin
 * @see UnaryOperation
 */
public abstract class UnaryOperation extends Operation {

	private static final long serialVersionUID = 1L;

	@Override
	public final int arity() {
		return 1;
	}
	
}

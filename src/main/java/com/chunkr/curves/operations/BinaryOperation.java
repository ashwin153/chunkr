package com.chunkr.curves.operations;

import com.chunkr.curves.Operation;

/**
 * Binary operations take two arguments and produce a single output. Some
 * examples of binary operations are addition and multiplication.
 * 
 * @author ashwin
 * @see Operation
 */
public abstract class BinaryOperation extends Operation {

	private static final long serialVersionUID = 1L;

	@Override
	public final int arity() {
		return 2;
	}
	
}

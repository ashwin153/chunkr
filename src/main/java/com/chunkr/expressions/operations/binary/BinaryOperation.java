package com.chunkr.expressions.operations.binary;

import com.chunkr.expressions.operations.Operation;


/**
 * Binary operations take two arguments and produce a single output. Some
 * examples of binary operations are addition and multiplication.
 * 
 * @author ashwin
 * 
 */
public abstract class BinaryOperation implements Operation {

	@Override
	public final int arity() {
		return 2;
	}
	
}

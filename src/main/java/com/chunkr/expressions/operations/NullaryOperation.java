package com.chunkr.expressions.operations;

import com.chunkr.expressions.Operation;

/**
 * Nullary operations take no arguments, but still produce outputs; nullary
 * operations are commonly thought of as constants/variables.
 * 
 * @author ashwin
 * @see Operation
 */
public abstract class NullaryOperation implements Operation {

	@Override
	public final int arity() {
		return 0;
	}
	
}

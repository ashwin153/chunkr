package com.chunkr.compress.expressions.operations;

import com.chunkr.compress.expressions.Operation;

/**
 * Nullary operations take no arguments, but still produce outputs; nullary
 * operations are commonly thought of as constants/variables.
 * 
 * @author ashwin
 * 
 */
public abstract class NullaryOperation implements Operation {

	@Override
	public int arity() {
		return 0;
	}
	
}

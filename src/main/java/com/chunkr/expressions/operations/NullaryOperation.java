package com.chunkr.expressions.operations;



/**
 * Nullary operations take no arguments, but still produce outputs; nullary
 * operations are commonly thought of as constants/variables.
 * 
 * @author ashwin
 * 
 */
public abstract class NullaryOperation implements Operation {

	@Override
	public final int arity() {
		return 0;
	}
	
}

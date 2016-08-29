package com.chunkr.curves.operations;

import com.chunkr.curves.Operation;

/**
 * Nullary operations take no arguments, but still produce outputs; nullary
 * operations are commonly thought of as constants/variables.
 * 
 * @author ashwin
 * @see Operation
 */
public abstract class NullaryOperation extends Operation {

	private static final long serialVersionUID = 1L;

	@Override
	public final int arity() {
		return 0;
	}
	
}

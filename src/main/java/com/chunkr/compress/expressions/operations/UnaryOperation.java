package com.chunkr.compress.expressions.operations;

import com.chunkr.compress.expressions.Operation;

public abstract class UnaryOperation implements Operation {

	@Override
	public int arity() {
		return 1;
	}
	
}

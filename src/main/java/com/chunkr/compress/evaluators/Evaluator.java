package com.chunkr.compress.evaluators;

import com.chunkr.expressions.Expression;

public interface Evaluator {

	/**
	 * Evaluates the expression over the interval [0, length) and returns the
	 * resulting integer "chunks".
	 * 
	 * @param length
	 * @param expression
	 * @return
	 */
	public int[] eval(int length, Expression expression);
	
}

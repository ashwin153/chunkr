package com.chunkr.compress.evaluators;

import com.chunkr.expressions.Expression;

/**
 * Evaluators are responsible for evaluating expressions. Evaluators are a
 * significant performance bottleneck, because large files will often require
 * thousands if not millions of expression evaluations.
 * 
 * @author ashwin
 */
public interface Evaluator {

	/**
	 * Evaluates the expression over the interval [0, length) and returns the
	 * resulting integer "chunks".
	 * 
	 * @param length length of file
	 * @param expression evaluable representation of file
	 * @return integer chunks
	 */
	public int[] eval(int length, Expression expression);
	
}

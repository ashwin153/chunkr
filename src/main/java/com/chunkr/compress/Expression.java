package com.chunkr.compress;

import java.io.Serializable;
import java.math.BigDecimal;

import com.chunkr.compress.expressions.Evaluable;
import com.chunkr.compress.expressions.Variable;

/**
 * Expressions are essentially functions that can be evaluated at any point.
 * Expressions are the building block of all mathematical/statistical models.
 * 
 * @author ashwin
 */
public class Expression implements Serializable {
	
	private static final long serialVersionUID = 6069395250411328800L;
	
	private Evaluable _root;
	private Variable _var;

	public Expression(Evaluable root, Variable var) {
		_root = root;
		_var = var;
	}
	
	/**
	 * Evaluates the expression at the given coordinate. Note: this operation
	 * ought to be thread safe.
	 * 
	 * @param x
	 * @return result
	 */
	public BigDecimal eval(BigDecimal x) {
		// Because multiple expressions can reuse the same variables; we need to
		// make evaluations synchronized to ensure that evaluations are
		// thread-safe.
		synchronized(_var) {
			_var.set(x);
			return _root.eval();
		}
	}
	
	@Override
	public String toString() {
		return _root.toString();
	}
	
}

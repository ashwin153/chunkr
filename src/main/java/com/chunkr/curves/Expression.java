package com.chunkr.curves;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.collections.IteratorUtils;

import com.chunkr.curves.exceptions.EvaluationException;
import com.chunkr.curves.operations.nullary.Variable;

/**
 * Expressions are one-dimensional functions that can be evaluated at various
 * values in a thread-safe manner. There are two kinds of expressions: stack
 * expresssions and tree expressions. Stack expressions and tree expression
 * are both compositions simpler operations like addition and sine.
 * 
 * @author ashwin
 * @see com.chunkr.curves.expressions.StackExpression
 * @see com.chunkr.curves.expressions.TreeExpression
 * @see com.chunkr.curves.Operation
 */
public abstract class Expression implements Serializable, Iterable<Operation> {

	private static final long serialVersionUID = 1L;
	
	private Variable _variable;
	
	public Expression(Variable variable) {
		_variable = variable;
	}
	
	public Variable getVariable() {
		return _variable;
	}
	
	/**
	 * Evaluates the expression at the specified value. Evaluations are
	 * thread-safe; therefore, the same expression can be evaluated at multiple
	 * values concurrently.
	 * 
	 * @param x variable value
	 * @return function output
	 * @throws Exception
	 */
	public BigDecimal eval(BigDecimal x) throws Exception {
		synchronized(_variable) {
			_variable.setValue(x);
			return eval();
		}
	}

	/**
	 * Evaluates the expression at the specified values for its variable.
	 * Evaluation is thread-safe, but not guaranteed to be parallelized.
	 * 
	 * @param x variable values
	 * @return function outputs
	 * @throws Exception
	 */
	public BigDecimal[] eval(BigDecimal[] x) throws EvaluationException {
		try {
			BigDecimal[] y = new BigDecimal[x.length];
			for(int i = 0; i < x.length; i++)
				y[i] = eval(x[i]);
			return y;
		} catch(Exception e) {
			throw new EvaluationException(e);
		}
	}
	
	/**
	 * Evaluates the expression over the specified interval. Evaluation is
	 * thread-safe, but not guaranteed to be parallelized.
	 * 
	 * @param beg beginning value
	 * @param end ending value
	 * @param inc increment
	 * @return function output
	 * @throws Exception
	 */
	public BigDecimal[] eval(BigDecimal beg, BigDecimal end, BigDecimal inc) throws EvaluationException {
		BigDecimal ceil = beg.divide(inc, Operation.CONTEXT).setScale(0, RoundingMode.UP);
		BigDecimal flor = end.divide(inc, Operation.CONTEXT).setScale(0, RoundingMode.DOWN);
		BigDecimal[] y  = new BigDecimal[flor.subtract(ceil).intValue()];
		
		try {
			for(int i = 0; i < y.length; i++)
				y[i] = eval(beg.add(inc.multiply(BigDecimal.valueOf(i))));
			return y;
		} catch(Exception e) {
			throw new EvaluationException(e);
		}
	}
	
	/**
	 * Evaluates the expression over the given integer interval and rounds all
	 * the outputs to the closest integer.
	 * 
	 * @param beg beginning value
	 * @param end ending value
	 * @param inc increment
	 * @return
	 * @throws Exception
	 */
	public int[] eval(int beg, int end, int inc) throws EvaluationException {
		BigDecimal[] y = eval(BigDecimal.valueOf(beg), BigDecimal.valueOf(end), BigDecimal.valueOf(inc));
		int[] n = new int[y.length];
		for(int i = 0; i < y.length; i++)
			n[i] = y[i].setScale(0, RoundingMode.HALF_UP).intValueExact();
		return n;
	}
	
	abstract protected BigDecimal eval() throws Exception;
	
	@Override
	public int hashCode() {
		return IteratorUtils.toList(iterator()).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof Expression))
			return false;
		
		return IteratorUtils.toList(iterator()).equals(
				IteratorUtils.toList(((Expression) obj).iterator()));
	}
	
	@Override
	public String toString() {
		return IteratorUtils.toList(iterator()).toString();
	}
	
}

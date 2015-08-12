package com.chunkr.compress.expressions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Stack;

import com.chunkr.compress.expressions.operations.nullary.Variable;

/**
 * Expressions are functions that can be evaluated at any point. Expressions are
 * the building block of all mathematical/statistical models. Expressions store
 * operations in postfix order (reverse polish notation), and evaluate them
 * using a stack.
 * 
 * @author ashwin
 */
public class Expression {
	
	private List<Operation> _operations;
	private Variable _variable;
	
	public Expression(Variable variable, List<Operation> operations) {
		_variable = variable;
		_operations = operations;
	}
	
	public Variable getVariable() {
		return _variable;
	}
	
	public List<Operation> getOperations() {
		return _operations;
	}
	
	/**
	 * Evaluates the expression at the specified coordinate. This operation is
	 * thread-safe.
	 * 
	 * @param x input
	 * @return output
	 */
	public BigDecimal eval(BigDecimal x) {
		// Because multiple expressions can reuse the same variables; we need to
		// make evaluations synchronized to ensure that evaluations are
		// thread-safe.
		synchronized(_variable) {
			_variable.set(x);
			
			Stack<BigDecimal> stack = new Stack<BigDecimal>();
			for(int i = 0; i < _operations.size(); i++) {
				BigDecimal[] operands = new BigDecimal[_operations.get(i).arity()];
				for(int j = 0; j < operands.length; j++)
					operands[j] = stack.pop();
				stack.push(_operations.get(i).eval(operands));
			}
			
			return stack.pop();
		}
	}
	
	@Override
	public String toString() {
		return _operations.toString();
	}
}

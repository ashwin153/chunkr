package com.chunkr.curves.expressions;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.chunkr.curves.Expression;
import com.chunkr.curves.Operation;
import com.chunkr.curves.expressions.TreeExpression.Node;
import com.chunkr.curves.operations.nullary.Variable;

/**
 * Stack expressions are expressions that store their operations in reverse
 * polish notation (postfix). Stack expressions can be converted to and from
 * tree expressions and both types of expressions are equivalent, but will have
 * different performance and memory usage.
 * 
 * @author ashwin
 */
public class StackExpression extends Expression {
		
	private static final long serialVersionUID = 1L;
	
	private List<Operation> _operations;
	
	public StackExpression() {
		super(null);
	}
	
	public StackExpression(Variable variable, List<Operation> operations) {
		super(variable);
		_operations = operations;
	}
	
	public StackExpression(Variable variable, Operation... operations) {
		super(variable);
		_operations = new ArrayList<Operation>(Arrays.asList(operations));
	}
	
	public StackExpression(TreeExpression expr) {
		super(expr.getVariable());
		
		_operations = new ArrayList<Operation>();
		Stack<Object[]> stack = new Stack<Object[]>();
		stack.push(new Object[] { expr.getRoot(), 0 } );
		
		while(!stack.isEmpty()) {
			Node node = (Node) stack.peek()[0];
			int value = (Integer) stack.pop()[1];
			
			if(node.getChildren().size() <= value) {
				_operations.add(node.getOperation());
			} else {
				stack.push(new Object[] { node, ++value });
				stack.push(new Object[] { node.getChildren().get(value-1), 0 });
			}
		}
	}
	
	public List<Operation> getOperations() {
		return _operations;
	}
	
	@Transient
	public int length() {
		return _operations.size();
	}
	
	/**
	 * Returns the sub-expression beginning at the specified index.
	 * 
	 * @param index
	 * @return sub-expression
	 */
	public StackExpression subexpr(int index) {
		List<Operation> operations = new ArrayList<Operation>();
		int arity = _operations.get(index).arity();
		operations.add(0, _operations.get(index));

		while(arity > 0) {
			arity -= 1;
			index -= 1;
			arity += _operations.get(index).arity();
			operations.add(0, _operations.get(index));
		}
		
		return new StackExpression(getVariable(), operations);
	}

	/**
	 * Replaces the sub-expression at the specified index with the specified
	 * expression.
	 * 
	 * @param index
	 * @param replacements
	 * @return expression with replaced sub-expression
	 */
	public StackExpression replace(int index, StackExpression expr) {
		List<Operation> operations = new ArrayList<Operation>(_operations);
		int arity = operations.get(index).arity();
		operations.remove(index);
		
		while(arity > 0) {
			arity -= 1;
			index -= 1;
			arity += _operations.get(index).arity();
			operations.remove(index);
		}
		
		List<Operation> other = new ArrayList<Operation>(expr.getOperations());
		for(int i = 0; i < other.size(); i++)
			if(other.get(i) instanceof Variable)
				other.set(i, getVariable());
		operations.addAll(index, other);
		return new StackExpression(getVariable(), operations);
	}
	
	@Override
	public BigDecimal eval() throws Exception {
		Stack<BigDecimal> stack = new Stack<BigDecimal>();
		for(int i = 0; i < _operations.size(); i++) {
			BigDecimal[] operands = new BigDecimal[_operations.get(i).arity()];
			for(int j = 0; j < operands.length; j++)
				operands[j] = stack.pop();
			stack.push(_operations.get(i).eval(operands));
		}
		
		if(stack.size() != 1) 
			throw new IllegalArgumentException("Invalid stack expression");
		return stack.pop();
	}

	@Override
	public Iterator<Operation> iterator() {
		return _operations.iterator();
	}
	
}
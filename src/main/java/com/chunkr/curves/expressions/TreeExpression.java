package com.chunkr.curves.expressions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.chunkr.curves.Expression;
import com.chunkr.curves.Operation;
import com.chunkr.curves.operations.nullary.Variable;
import com.google.common.collect.Iterators;

/**
 * Tree expressions are expression that store their operations in a parse tree.
 * This makes it much simpler to parallelize the computation. Tree expressions
 * can be converted to and from stack expressions and both types of expressions
 * are equivalent, but will have different performance and memory usage.
 * 
 * @author ashwin
 * 
 */
public class TreeExpression extends Expression {

	private static final long serialVersionUID = 1L;
	
	private Node _root;
	
	public TreeExpression(Variable variable, Node root) {
		super(variable);
		_root = root;
	}
	
	public TreeExpression(StackExpression expr) {
		super(expr.getVariable());
		
		Stack<Node> stack = new Stack<Node>();
		List<Operation> oper = expr.getOperations();
		for(int i = 0; i < oper.size(); i++) {
			Node[] children = new Node[oper.get(i).arity()];
			for(int j = 0; j < children.length; j++)
				children[j] = stack.pop();
			stack.push(new Node(oper.get(i), children));
		}
		
		if(stack.size() != 1) 
			throw new IllegalArgumentException("Invalid stack expression");
		_root = stack.pop();
	}
	
	public Node getRoot() {
		return _root;
	}
	
	@Override
	public BigDecimal eval() throws Exception {
//		ExecutorService executor = Executors.newCachedThreadPool();
//		BigDecimal y = _root.eval(executor);
//		executor.shutdownNow();
//		return y;
		return _root.eval();
	}
	
	@Override
	public Iterator<Operation> iterator() {
		return _root.iterator();
	}
	
	/**
	 * Nodes are individual elements in the parse tree. A node has an operation
	 * and pointers to child nodes whose outputs are inputs to the node's
	 * operation. Because evaluation of children is independent, we can easily
	 * parallelize child evaluation.
	 * 
	 * @author ashwin
	 * 
	 */
	public static class Node implements Iterable<Operation> {
		
		private Operation _operation;
		private List<Node> _children;
		
		public Node(Operation operation, Node... children) {
			_operation = operation;
			_children = new ArrayList<Node>(Arrays.asList(children));
		}
		
		public Operation getOperation() {
			return _operation;
		}

		public List<Node> getChildren() {
			return _children;
		}
		
		public BigDecimal eval() throws Exception {
			// Retrieve the value of the future tasks and perform the operation
			BigDecimal[] inputs = new BigDecimal[_operation.arity()];
			for(int i = 0; i < inputs.length; i++)
				inputs[i] = _children.get(i).eval();
			return _operation.eval(inputs);
		}
		
		public BigDecimal eval(final ExecutorService executor) throws Exception {
			// Evaluates the node in parallel; we can do this because each child
			// can be evaluated independently. This should lead to a huge
			// performance boost of tree expression evaluation.
			List<Future<BigDecimal>> tasks = new ArrayList<Future<BigDecimal>>();
			for(final Node child : _children) {
				tasks.add(executor.submit(new Callable<BigDecimal>() {
					@Override
					public BigDecimal call() throws Exception {
						return child.eval(executor);
					}
				}));
			}
			
			// Retrieve the value of the future tasks and perform the operation
			BigDecimal[] inputs = new BigDecimal[_operation.arity()];
			for(int i = 0; i < inputs.length; i++)
				inputs[i] = tasks.get(i).get();
			return _operation.eval(inputs);
		}
		
		@Override
		public Iterator<Operation> iterator() {
			Iterator<Operation> iter = Collections.emptyIterator();
			for(Node child : _children)
				iter = Iterators.concat(child.iterator(), iter);
			return Iterators.concat(iter, Iterators.forArray(_operation));
		}
	}

}

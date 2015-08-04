package com.chunkr.compress.expressions;


/**
 * This class represents a mathematical operation. Operations perform some
 * specified action on their operands. Operations must have at least one operand
 * (nullary operations are not permitted). Operations are mutable; their
 * operands can change. Operations can be thought of as a map f: R x R x ... x R
 * -> R.
 * 
 * Operations are chained together through implicit composition in a tree-like
 * data structure; in which internal nodes are operations and leaf nodes are
 * constants/variables.
 * 
 * @author ashwin
 * 
 */
public abstract class Operation implements Evaluable {
	
	private static final long serialVersionUID = 1876567909012093450L;
	
	private Evaluable[] _operands;

	public Operation(Evaluable... operands) {
		_operands = operands;
	}
	
	/**
	 * Returns the "arity," or the number of operands that this operation takes.
	 * For example; a binary operation takes 2 operands and, therefore, has an
	 * arity of 2.
	 * 
	 * @return number of operands
	 */
	public int arity() {
		return _operands.length;
	}
	
	/**
	 * Returns the operand at the specified index. Operands are zero-indexed.
	 * Therefore, a binary operation will have an operand at index 0 and at
	 * index 1.
	 * 
	 * @param index
	 * @return operand
	 */
	public Evaluable get(int index) {
		return _operands[index];
	}
	
	/**
	 * Sets the operand at the specified index to the specified evaluable.
	 * Operands are zero-indexed. Therefore, a binary operation will have an
	 * operand at index 0 and at index 1.
	 * 
	 * @param index
	 * @param evaluable
	 */
	public void set(int index, Evaluable evaluable) {
		_operands[index] = evaluable;
	}
	
}

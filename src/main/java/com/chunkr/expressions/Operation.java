package com.chunkr.expressions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import com.chunkr.expressions.operations.BinaryOperation;
import com.chunkr.expressions.operations.NullaryOperation;
import com.chunkr.expressions.operations.UnaryOperation;

/**
 * Operations are the building blocks of mathematical expressions; operations
 * take a specified number of operands (arity) and produce an output. Operations
 * must be valid over all the real numbers (domain = (-inf, +inf)) and produce
 * outputs on the real numbers (codomain subset of (-inf, +inf)).
 * 
 * @author ashwin
 * @see NullaryOperation
 * @see UnaryOperation
 * @see BinaryOperation
 */
public interface Operation {
	
	public static final MathContext CONTEXT = new MathContext(25, RoundingMode.HALF_UP);
	
	/**
	 * The order that operations are declared in this enumeration will determine
	 * their operation codes. Therefore, it is imperative that these enumerated
	 * constants stay in order. OperationCode names must be the same as the
	 * associated operation class simple name.
	 * 
	 * @author ashwin
	 * 
	 */
	public enum Type {
		CONSTANT, VARIABLE, ABS, COS, NEG, POW, SIN, ADD, MUL, SUB;
	}
	
	/**
	 * Returns the "arity," or the number of operands that this operation takes.
	 * For example; a binary operation takes 2 operands and, therefore, has an
	 * arity of 2.
	 * 
	 * @return number of operands
	 */
	public int arity();
	
	/**
	 * Evaluates the expression over the specified operands.
	 * 
	 * @param operands inputs
	 * @return output
	 */
	public BigDecimal eval(BigDecimal... operands);
}

package com.chunkr.curves;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import com.chunkr.curves.operations.BinaryOperation;
import com.chunkr.curves.operations.NullaryOperation;
import com.chunkr.curves.operations.UnaryOperation;

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
public abstract class Operation implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
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
	abstract public int arity();
	
	/**
	 * Evaluates the expression over the specified operands.
	 * 
	 * @param operands inputs
	 * @return output
	 */
	abstract public BigDecimal eval(BigDecimal... operands);

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof Operation))
			return false;
		
		Operation oth = (Operation) obj;
		return this.getClass().equals(oth.getClass());
	}
}

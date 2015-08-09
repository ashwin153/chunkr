package com.chunkr.compress.expressions;
import java.math.BigDecimal;

/**
 * Operations are the building blocks of mathematical expressions; operations
 * take a specified number of operands (arity) and produce an output. Operations
 * must be valid over all the real numbers (domain = (-inf, +inf)) and produce
 * outputs on the real numbers (codomain subset of (-inf, +inf)).
 * 
 * @author ashwin
 * 
 */
public interface Operation {
	
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
	 * @param operands
	 * @return
	 */
	public BigDecimal eval(BigDecimal... operands);
}

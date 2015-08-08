package com.chunkr.compress.expressions;
import java.math.BigDecimal;

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

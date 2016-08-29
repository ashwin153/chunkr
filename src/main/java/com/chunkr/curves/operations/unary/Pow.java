package com.chunkr.curves.operations.unary;

import java.math.BigDecimal;

import com.chunkr.curves.operations.UnaryOperation;

public class Pow extends UnaryOperation {
	
	private static final long serialVersionUID = 1L;

	private int _pow;

	/**
	 * Constructs a power function of the specified degree. The degree must be a
	 * non-negative integer to ensure that it is valid over the real numbers.
	 * 
	 * @param pow
	 */
	public Pow(int pow) {
		_pow = pow;
	}
	
	public int getPower() {
		return _pow;
	}
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].pow(_pow, CONTEXT);
	}
	
	@Override
	public String toString() {
		return "pow" + _pow;
	}
	
	@Override
	public int hashCode() {
		return new Integer(_pow).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof Pow))
			return false;
		
		Pow oth = (Pow) obj;
		return _pow == oth.getPower();
	}
}

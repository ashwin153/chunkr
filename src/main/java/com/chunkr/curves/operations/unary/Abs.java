package com.chunkr.curves.operations.unary;

import java.math.BigDecimal;

import com.chunkr.curves.operations.UnaryOperation;

public class Abs extends UnaryOperation {
	
	private static final long serialVersionUID = 1L;

	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].abs();
	}
	
	@Override
	public String toString() {
		return "abs";
	}
	
}

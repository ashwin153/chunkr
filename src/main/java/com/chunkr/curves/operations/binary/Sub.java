package com.chunkr.curves.operations.binary;

import java.math.BigDecimal;

import com.chunkr.curves.operations.BinaryOperation;

public class Sub extends BinaryOperation {

	private static final long serialVersionUID = 1L;

	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].subtract(operands[1], CONTEXT);
	}
	
	@Override
	public String toString() {
		return "sub";
	}
	
}

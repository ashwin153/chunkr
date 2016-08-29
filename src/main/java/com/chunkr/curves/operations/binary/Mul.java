package com.chunkr.curves.operations.binary;

import java.math.BigDecimal;

import com.chunkr.curves.operations.BinaryOperation;

public class Mul extends BinaryOperation {
	
	private static final long serialVersionUID = 1L;

	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].multiply(operands[1], CONTEXT);
	}
	
	@Override
	public String toString() {
		return "mul";
	}
	
}

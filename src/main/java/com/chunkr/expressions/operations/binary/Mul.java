package com.chunkr.expressions.operations.binary;

import java.math.BigDecimal;

public class Mul extends BinaryOperation {
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].multiply(operands[1]);
	}
	
	@Override
	public String toString() {
		return "mul";
	}
	
}

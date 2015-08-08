package com.chunkr.compress.expressions.operations.binary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.operations.BinaryOperation;

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

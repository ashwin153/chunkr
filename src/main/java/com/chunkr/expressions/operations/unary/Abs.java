package com.chunkr.expressions.operations.unary;

import java.math.BigDecimal;

import com.chunkr.expressions.operations.UnaryOperation;

public class Abs extends UnaryOperation {
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].abs();
	}
	
	@Override
	public String toString() {
		return "abs";
	}
	
}

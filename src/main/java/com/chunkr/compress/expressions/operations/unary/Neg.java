package com.chunkr.compress.expressions.operations.unary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.operations.UnaryOperation;

public class Neg extends UnaryOperation {
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].negate();
	}
	
	@Override
	public String toString() {
		return "neg";
	}
	
}

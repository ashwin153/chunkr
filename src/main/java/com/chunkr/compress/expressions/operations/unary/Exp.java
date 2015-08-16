package com.chunkr.compress.expressions.operations.unary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.operations.UnaryOperation;

public class Exp extends UnaryOperation {

	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return null;
	}
	
	@Override
	public String toString() {
		return "exp";
	}
	
}

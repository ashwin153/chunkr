package com.chunkr.expressions.operations.unary;

import java.math.BigDecimal;

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

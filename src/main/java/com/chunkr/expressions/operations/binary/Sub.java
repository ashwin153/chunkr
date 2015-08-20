package com.chunkr.expressions.operations.binary;

import java.math.BigDecimal;

public class Sub extends BinaryOperation {

	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].subtract(operands[1]);
	}
	
	@Override
	public String toString() {
		return "sub";
	}
	
}

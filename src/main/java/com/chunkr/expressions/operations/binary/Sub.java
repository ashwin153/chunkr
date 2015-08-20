package com.chunkr.expressions.operations.binary;

import java.math.BigDecimal;

import com.chunkr.expressions.operations.BinaryOperation;

public class Sub extends BinaryOperation {

	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].subtract(operands[1], CONTEXT);
	}
	
	@Override
	public String toString() {
		return "sub";
	}
	
}

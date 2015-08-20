package com.chunkr.expressions.operations.binary;

import java.math.BigDecimal;

import com.chunkr.expressions.operations.BinaryOperation;

public class Add extends BinaryOperation {
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].add(operands[1], CONTEXT);
	}
	
	@Override
	public String toString() {
		return "add";
	}
	
}

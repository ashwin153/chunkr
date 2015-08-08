package com.chunkr.compress.expressions.operations.unary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.operations.UnaryOperation;

public class Cos extends UnaryOperation {
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		BigDecimal mod = operands[0].remainder(BigDecimal.valueOf(2 * Math.PI));
		return BigDecimal.valueOf(Math.cos(mod.doubleValue()));
	}
	
	@Override
	public String toString() {
		return "cos";
	}
	
}

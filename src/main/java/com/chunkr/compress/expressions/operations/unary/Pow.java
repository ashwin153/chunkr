package com.chunkr.compress.expressions.operations.unary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.operations.UnaryOperation;

public class Pow extends UnaryOperation {
	
	private int _pow;

	public Pow(int pow) {
		_pow = pow;
	}
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return operands[0].pow(_pow);
	}
	
	@Override
	public String toString() {
		return "pow" + _pow;
	}
	
}

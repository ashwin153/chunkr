package com.chunkr.expressions.operations.nullary;

import java.math.BigDecimal;

import com.chunkr.expressions.operations.NullaryOperation;

public class Constant extends NullaryOperation {
	
	private BigDecimal _value;
	
	public Constant(double value) {
		_value = BigDecimal.valueOf(value);
	}
	
	public Constant(BigDecimal value) {
		_value = value;
	}
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return _value;
	}
	
	@Override
	public String toString() {
		return _value.toString();
	}
	
}

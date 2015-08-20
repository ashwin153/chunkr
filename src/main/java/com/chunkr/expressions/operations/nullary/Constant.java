package com.chunkr.expressions.operations.nullary;

import java.math.BigDecimal;

import com.chunkr.expressions.operations.NullaryOperation;

public class Constant extends NullaryOperation {
	
	private double _value;
	
	public Constant(double value) {
		_value = value;
	}
	
	public double getValue() {
		return _value;
	}
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return BigDecimal.valueOf(_value);
	}
	
	@Override
	public String toString() {
		return String.valueOf(_value);
	}
	
}

package com.chunkr.expressions.operations.nullary;

import java.math.BigDecimal;

import com.chunkr.expressions.operations.NullaryOperation;

public class Variable extends NullaryOperation {

	private String _name;
	private BigDecimal _value;
	
	public Variable(String name) {
		this(name, BigDecimal.ZERO);
	}
	
	public Variable(String name, BigDecimal value) {
		_name = name;
		_value = value;
	}
	
	public void set(BigDecimal value) {
		_value = value;
	}
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return _value;
	}
	
	@Override
	public String toString() {
		return _name;
	}
	
}

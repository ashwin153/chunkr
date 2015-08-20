package com.chunkr.expressions.operations.nullary;

import java.math.BigDecimal;

import com.chunkr.expressions.operations.NullaryOperation;

public class Variable extends NullaryOperation {

	private char _name;
	private BigDecimal _value;
	
	public Variable(char name) {
		this(name, BigDecimal.ZERO);
	}
	
	public Variable(char name, BigDecimal value) {
		_name = name;
		_value = value;
	}
	
	public char getName() {
		return _name;
	}
	
	public void setValue(BigDecimal value) {
		_value = value;
	}
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return _value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(_name);
	}
	
}

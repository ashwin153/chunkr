package com.chunkr.compress.expressions;

import java.math.BigDecimal;

public class Variable implements Evaluable {
	
	private static final long serialVersionUID = -9012760626144059311L;
	
	private String _name;
	private BigDecimal _value;
	
	/**
	 * Constructs a new named variable with an initial value of zero.
	 * @param name variable name
	 */
	public Variable(String name) {
		this(name, BigDecimal.ZERO);
	}

	/**
	 * Constructs a new named variable with the specified initial value.
	 * @param name variable name
	 * @param value initial value
	 */
	public Variable(String name, BigDecimal value) {
		_name = name;
		_value = value;
	}
	
	/**
	 * Unlike constants, variables are mutable; this method sets the value of
	 * the internal BigDecimal. Future evaluations of this variable will return
	 * the updated value.
	 * 
	 * @param value
	 */
	public void set(BigDecimal value) {
		_value = value;
	}

	@Override
	public BigDecimal eval() {
		return _value;
	}
	
	@Override
	public Variable copy() {
		return this;
	}

	@Override
	public String toString() {
		return _name;
	}
	
}
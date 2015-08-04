package com.chunkr.compress.expressions.operations.unary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.Evaluable;
import com.chunkr.compress.expressions.Operation;

public class Abs extends Operation {

	private static final long serialVersionUID = 7755200680119304430L;

	public Abs(Evaluable e1) {
		super(e1);
	}
	
	@Override
	public BigDecimal eval() {
		return get(0).eval().abs();
	}
	
	@Override
	public String toString() {
		return "abs(x)";
	}
	
}

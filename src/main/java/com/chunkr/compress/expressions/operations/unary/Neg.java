package com.chunkr.compress.expressions.operations.unary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.Evaluable;
import com.chunkr.compress.expressions.Operation;

public class Neg extends Operation {

	private static final long serialVersionUID = 5845074939494491895L;

	public Neg(Evaluable e1) {
		super(e1);
	}
	
	@Override
	public BigDecimal eval() {
		return get(0).eval().negate();
	}
	
	@Override
	public String toString() {
		return "neg(x)";
	}
	
}

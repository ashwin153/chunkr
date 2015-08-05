package com.chunkr.compress.expressions.operations.binary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.Evaluable;
import com.chunkr.compress.expressions.Operation;

public class Mul extends Operation {

	private static final long serialVersionUID = 8424334186533531621L;

	public Mul(Evaluable e1, Evaluable e2) {
		super(e1, e2);
	}
	
	@Override
	public BigDecimal eval() {
		return get(0).eval().multiply(get(1).eval());
	}
	
	@Override
	public Mul copy() {
		return new Mul(get(0).copy(), get(1).copy());
	}
	
	@Override
	public String toString() {
		return "(" + get(0) + ") * (" + get(1) + ")";
	}
	
}

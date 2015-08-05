package com.chunkr.compress.expressions.operations.unary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.Evaluable;
import com.chunkr.compress.expressions.Operation;

public class Pow extends Operation {

	private static final long serialVersionUID = 2508108118592641099L;
	
	private int _pow;

	public Pow(Evaluable e1, int pow) {
		super(e1);
		_pow = pow;
	}
	
	@Override
	public BigDecimal eval() {
		return get(0).eval().pow(_pow);
	}
	
	@Override
	public Pow copy() {
		return new Pow(get(0).copy(), _pow);
	}
	
	@Override
	public String toString() {
		return "pow" + _pow + "(x)";
	}
	
}

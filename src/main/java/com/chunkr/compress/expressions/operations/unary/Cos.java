package com.chunkr.compress.expressions.operations.unary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.Evaluable;
import com.chunkr.compress.expressions.Operation;

public class Cos extends Operation {

	private static final long serialVersionUID = 5952448482202385669L;

	public Cos(Evaluable e1) {
		super(e1);
	}

	@Override
	public BigDecimal eval() {
		BigDecimal mod = get(0).eval().remainder(BigDecimal.valueOf(2 * Math.PI));
		return BigDecimal.valueOf(Math.cos(mod.doubleValue()));
	}
	
	@Override
	public Cos copy() {
		return new Cos(get(0).copy());
	}

	@Override
	public String toString() {
		return "cos(x)";
	}
	
}

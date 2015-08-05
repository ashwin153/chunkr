package com.chunkr.compress.expressions.operations.unary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.Evaluable;
import com.chunkr.compress.expressions.Operation;

public class Sin extends Operation {
	
	private static final long serialVersionUID = 8844886240455780640L;

	public Sin(Evaluable e1) {
		super(e1);
	}
	
	@Override
	public BigDecimal eval() {
		BigDecimal mod = get(0).eval().remainder(BigDecimal.valueOf(2 * Math.PI));
		return BigDecimal.valueOf(Math.sin(mod.doubleValue()));
	}
	
	@Override
	public Sin copy() {
		return new Sin(get(0).copy());
	}

	@Override
	public String toString() {
		return "sin(x)";
	}
	
}

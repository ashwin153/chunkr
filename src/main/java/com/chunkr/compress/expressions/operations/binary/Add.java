package com.chunkr.compress.expressions.operations.binary;

import java.math.BigDecimal;

import com.chunkr.compress.expressions.Evaluable;
import com.chunkr.compress.expressions.Operation;

public class Add extends Operation {
		
	private static final long serialVersionUID = -8183255276834473756L;

	public Add(Evaluable e1, Evaluable e2) {
		super(e1, e2);
	}
	
	@Override
	public BigDecimal eval() {
		return get(0).eval().add(get(1).eval());
	}
	
	@Override
	public String toString() {
		return "(" + get(0) + ") + (" + get(1) + ")";
	}
	
}

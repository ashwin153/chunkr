package com.chunkr.compress.expressions;

import java.math.BigDecimal;

/**
 * Constants are BigDecimals that also inherit the evaluable interface, and
 * therefore can be used inside evaluable operation trees.
 * 
 * @author ashwin
 */
public class Constant extends BigDecimal implements Evaluable {

	private static final long serialVersionUID = -1929552382099879994L;

	public Constant(double val) {
		super(val);
	}

	@Override
	public BigDecimal eval() {
		return this;
	}

}

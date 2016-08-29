package com.chunkr.curves.operations.nullary;

import java.math.BigDecimal;

import com.chunkr.curves.operations.NullaryOperation;

public class Constant extends NullaryOperation {
	
	private static final long serialVersionUID = 1L;

	private double _value;
	
	public Constant(double value) {
		_value = value;
	}
	
	public double getValue() {
		return _value;
	}
	
	@Override
	public BigDecimal eval(BigDecimal... operands) {
		return BigDecimal.valueOf(_value);
	}
	
	@Override
	public String toString() {
		return String.valueOf(_value);
	}
	
	@Override
	public int hashCode() {
		return Double.valueOf(_value).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof Constant))
			return false;
		
		Constant oth = (Constant) obj;
		return _value == oth.getValue();
	}
}

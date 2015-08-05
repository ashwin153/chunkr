package com.chunkr.compress.expressions;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The Evaluable interfaces is the super interface of all types that can be
 * evaluated.
 * 
 * @author ashwin
 * 
 */
public interface Evaluable extends Serializable {

	/**
	 * Calculates and returns the value of this operation for the specified
	 * variables.
	 * 
	 * @return result
	 */
	public BigDecimal eval();
	
	/**
	 * Returns a new copy of this evaluable; copies can be deep or shallow.
	 * (e.x., it is impossible to deep copy a variable; but it is possible to
	 * deep copy an operation)
	 * 
	 * @return copy
	 */
	public Evaluable copy();
	
}

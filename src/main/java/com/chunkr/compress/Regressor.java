package com.chunkr.compress;

import java.awt.geom.Point2D;

/**
 * Regressors are responsible for fitting a smooth curve (Expression) to a set
 * of data points. Regressors are essentially curve fitters.
 * 
 * @author ashwin
 * 
 */
public interface Regressor {

	/**
	 * Constructs an evaluable curve that best fits the specified data points.
	 * The points are of the form (x, y).
	 * 
	 * @param data
	 * @return smooth curve
	 */
	public Expression fit(Point2D[] data);
	
}

package com.chunkr.curves;



/**
 * Regressors are responsible for fitting a smooth curve (Expression) to a set
 * of data points. Regressors are essentially curve fitters.
 * 
 * @author ashwin
 */
public abstract class Regressor {

	/**
	 * Constructs an evaluable curve that best fits the specified data points.
	 * The x value is implied to be the index in the array; therefore, points
	 * must be organized in a uniformly equidistant, sequential manner.
	 * 
	 * @param chunks integer chunks
	 * @param accuracy accuracy of regression on [0.0, 1.0]
	 * @return evaluable expression
	 */
	abstract public Expression fit(double[] points);
	
	public final Expression fit(int[] points) {
		double[] values = new double[points.length];
		for(int i = 0; i < points.length; i++)
			values[i] = points[i];
		return fit(values);
	}
	
}

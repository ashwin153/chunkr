package com.chunkr.compress.regressors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

import com.chunkr.compress.Expression;
import com.chunkr.compress.Regressor;
import com.chunkr.compress.expressions.Operation;
import com.chunkr.compress.expressions.operations.binary.Add;
import com.chunkr.compress.expressions.operations.binary.Mul;
import com.chunkr.compress.expressions.operations.nullary.Constant;
import com.chunkr.compress.expressions.operations.nullary.Variable;
import com.chunkr.compress.expressions.operations.unary.Pow;

public class LeastSquaresRegressor implements Regressor {
	
	private int _degree;
	
	public LeastSquaresRegressor(int degree) {
		_degree = degree;
	}

	@Override
	public Expression fit(int[] chunks) {
		Collection<WeightedObservedPoint> points = new ArrayList<WeightedObservedPoint>();
		for(int i = 0; i < chunks.length; i++)
			points.add(new WeightedObservedPoint(1.0, i, chunks[i]));
		double[] coeffs = PolynomialCurveFitter.create(_degree).fit(points);
		
		// Translate the coefficients into an evaluable expression
		Variable x = new Variable("x");
		List<Operation> operations = new ArrayList<Operation>();
		
		operations.add(0, new Constant(coeffs[0]));
		for(int i = 1; i < coeffs.length; i++) {
			operations.add(0, new Mul());
			operations.add(0, new Pow(i));
			operations.add(0, x);
			operations.add(0, new Constant(coeffs[i]));
			operations.add(operations.size(), new Add());
		}
		
		return new Expression(x, operations);
	}

}

package com.chunkr.expressions.regressors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

import com.chunkr.expressions.Expression;
import com.chunkr.expressions.Operation;
import com.chunkr.expressions.Regressor;
import com.chunkr.expressions.operations.binary.Add;
import com.chunkr.expressions.operations.binary.Mul;
import com.chunkr.expressions.operations.nullary.Constant;
import com.chunkr.expressions.operations.nullary.Variable;
import com.chunkr.expressions.operations.unary.Pow;

public class LeastSquaresRegressor extends Regressor {
	
	private int _degree;
	
	public LeastSquaresRegressor(int degree) {
		_degree = degree;
	}
	
	@Override
	public Expression fit(double[] points) {
		Collection<WeightedObservedPoint> weighted = new ArrayList<WeightedObservedPoint>();
		for(int i = 0; i < points.length; i++)
			weighted.add(new WeightedObservedPoint(1.0, i, points[i]));
		double[] coeffs = PolynomialCurveFitter.create(_degree).fit(weighted);
		
		// Translate the coefficients into an evaluable expression
		Variable x = new Variable('x');
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

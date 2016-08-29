package com.chunkr.curves.regressors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

import com.chunkr.curves.Expression;
import com.chunkr.curves.Operation;
import com.chunkr.curves.Regressor;
import com.chunkr.curves.expressions.StackExpression;
import com.chunkr.curves.operations.binary.Add;
import com.chunkr.curves.operations.binary.Mul;
import com.chunkr.curves.operations.nullary.Constant;
import com.chunkr.curves.operations.nullary.Variable;
import com.chunkr.curves.operations.unary.Pow;

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
		
		return new StackExpression(x, operations);
	}

}

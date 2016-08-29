package com.chunkr.curves.regressors;

import java.util.ArrayList;
import java.util.List;

import com.chunkr.curves.Expression;
import com.chunkr.curves.Operation;
import com.chunkr.curves.Regressor;
import com.chunkr.curves.expressions.StackExpression;
import com.chunkr.curves.operations.binary.Add;
import com.chunkr.curves.operations.binary.Mul;
import com.chunkr.curves.operations.nullary.Constant;
import com.chunkr.curves.operations.nullary.Variable;
import com.chunkr.curves.operations.unary.Cos;
import com.chunkr.curves.operations.unary.Sin;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

public class DiscreteFourierRegressor extends Regressor {

	@Override
	public Expression fit(double[] points) {
		// Compute the fast fourier transform (fft) of the chunked integers
		int N = points.length;
		
		double[] fft = new double[N];
		System.arraycopy(points, 0, fft, 0, N);
		DoubleFFT_1D transform = new DoubleFFT_1D(N);
		transform.realForward(fft);
	
		// Convert the output of the fft into an evaluable expression consisting
		// of sines and cosines. The ffreq term represents the fundamental
		// frequency; the fundamental frequency is Math.PI / 6 (trial and error).
		double ffreq = 2 * Math.PI / N; // Math.toRadians(360.0 / N);// (2 * Math.PI) / 360;
		Variable x = new Variable('x');
		List<Operation> operations = new ArrayList<Operation>();
		
		operations.add(new Constant(fft[0] / N));
		for(int i = 2; i < N; i++) {
			// The output of the fft alternates between real and imaginary
			// values; therefore, fft[2 * k] are the real values and fft[2 * k +
			// 1] are the imaginary values for k in [0, N / 2).
			operations.add(0, new Mul());
			
			double val = 2.0 / N * fft[i];
			operations.add(0, (i % 2 == 0) ? new Constant(val) : new Constant(-val));
			operations.add(0, (i % 2 == 0) ? new Cos() : new Sin());
			
			operations.add(0, new Mul());
			operations.add(0, new Constant((i / 2) * ffreq));
			operations.add(0, x);
			operations.add(operations.size(), new Add());
		}
		
		return new StackExpression(x, operations);
	}
	
}

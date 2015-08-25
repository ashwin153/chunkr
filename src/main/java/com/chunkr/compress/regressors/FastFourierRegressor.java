package com.chunkr.compress.regressors;

import java.util.ArrayList;
import java.util.List;

import com.chunkr.expressions.Expression;
import com.chunkr.expressions.operations.Operation;
import com.chunkr.expressions.operations.binary.Add;
import com.chunkr.expressions.operations.binary.Mul;
import com.chunkr.expressions.operations.nullary.Constant;
import com.chunkr.expressions.operations.nullary.Variable;
import com.chunkr.expressions.operations.unary.Cos;
import com.chunkr.expressions.operations.unary.Sin;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

public class FastFourierRegressor implements Regressor {

	@Override
	public Expression fit(int[] chunks) {
		// Compute the fast fourier transform (fft) of the chunked integers
		int N = chunks.length;
		
		double[] fft = new double[N];
		for(int i = 0; i < N; i++)
			fft[i] = chunks[i];
		
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
		
		return new Expression(x, operations);
	}
	
}

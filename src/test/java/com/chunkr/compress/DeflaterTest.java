package com.chunkr.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.junit.Test;

import com.chunkr.compress.encoders.Encoder;
import com.chunkr.compress.encoders.KryoEncoder;
import com.chunkr.compress.evaluators.Evaluator;
import com.chunkr.compress.evaluators.ParallelEvaluator;
import com.chunkr.compress.regressors.LeastSquaresRegressor;
import com.chunkr.compress.regressors.Regressor;

public class DeflaterTest {

	@Test
	public void testDeflate() throws Exception {		
		ByteArrayInputStream input = new ByteArrayInputStream("Is".getBytes());
		FileOutputStream output = new FileOutputStream(new File("./archive.mad"));
		
		Regressor regressor = new LeastSquaresRegressor(6);
		Encoder encoder = new KryoEncoder();
		Evaluator evaluator = new ParallelEvaluator();
		
		Deflater deflater = new Deflater(6, input, output, regressor, encoder, evaluator);
		deflater.run();
		output.close();
		
		FileInputStream fis = new FileInputStream(new File("./archive.mad"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Inflater inflater = new Inflater(fis, baos, encoder, evaluator);
		inflater.run();
		
		System.out.println(Arrays.toString("Is".getBytes()));
		System.out.println(Arrays.toString(baos.toByteArray()));
		System.out.println(baos.toString());
	}
	
}

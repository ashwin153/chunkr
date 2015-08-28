package com.chunkr.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import com.chunkr.compress.compressors.ExpressionCompressor;
import com.chunkr.compress.inflaters.ExpressionInflater;
import com.chunkr.expressions.regressors.LeastSquaresRegressor;

public class CompressorTest {

	@Test
	public void testDeflate() throws Exception {			
		// Deflate and inflate the data and see how different the data is; this
		// test is more to see if the algorithm is working properly than
		// anything else.
		byte[] data = "Is".getBytes();
		InputStream deflateIn = new ByteArrayInputStream(data);
		ByteArrayOutputStream deflateOut = new ByteArrayOutputStream();
		Deflater deflater = new ExpressionCompressor(6, new LeastSquaresRegressor(7));
		deflater.deflate(deflateIn, deflateOut);
		deflateIn.close();
		deflateOut.close();
		
		System.out.println("Before: " + data.length + ", After: " + deflateOut.size());
		
		InputStream inflateIn = new ByteArrayInputStream(deflateOut.toByteArray());
		ByteArrayOutputStream inflateOut = new ByteArrayOutputStream();
		Inflater inflater = new ExpressionInflater();
		inflater.inflate(inflateIn, inflateOut);
		
		inflateIn.close();
		inflateOut.close();
		
		System.out.println("Before: " + Arrays.toString(data) + 
				", After: " + Arrays.toString(inflateOut.toByteArray()));
	}
	
}

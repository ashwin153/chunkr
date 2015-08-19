package com.chunkr.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.junit.Test;

import com.chunkr.compress.regressors.LeastSquaresRegressor;

public class DeflaterTest {

	@Test
	public void testDeflate() throws Exception {		
		ByteArrayInputStream input = new ByteArrayInputStream("Is".getBytes());
		FileOutputStream output = new FileOutputStream(new File("./archive.mad"));
		Regressor regressor = new LeastSquaresRegressor(9);
		Encoder encoder = new Encoder();
		
		Deflater deflater = new Deflater(6, input, output, regressor, encoder);
		deflater.run();
		output.close();
		
		FileInputStream fis = new FileInputStream(new File("./archive.mad"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Inflater inflater = new Inflater(fis, baos, encoder);
		inflater.run();
		
		System.out.println(Arrays.toString("Is".getBytes()));
		System.out.println(Arrays.toString(baos.toByteArray()));
		System.out.println(baos.toString());
	}
	
}

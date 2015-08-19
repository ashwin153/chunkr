package com.chunkr.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.junit.Test;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.regressors.LeastSquaresRegressor;
import com.chunkr.compress.regressors.Regressor;

public class DeflaterTest {

	@Test
	public void testDeflate() throws Exception {
		String text = "Is";
		
		ByteArrayInputStream input = new ByteArrayInputStream(text.getBytes());
		FileOutputStream output = new FileOutputStream(new File("./archive.mad"));
		Regressor regressor = new LeastSquaresRegressor(9);
		Encoder encoder = new Encoder();
		
		Deflater deflater = new Deflater(input, output, 6, regressor, encoder);
		deflater.run();
		output.close();
		
		FileInputStream fis = new FileInputStream(new File("./archive.mad"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Inflater inflater = new Inflater(fis, baos, encoder);
		inflater.run();
		
		System.out.println(Arrays.toString(text.getBytes()));
		System.out.println(Arrays.toString(baos.toByteArray()));
		System.out.println(baos.toString());
		
		// public Deflater(InputStream input, OutputStream output, Chunker
		// chunker,
		// Regressor regressor, Encoder encoder) {
	}
	
}

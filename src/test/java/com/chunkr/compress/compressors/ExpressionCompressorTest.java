package com.chunkr.compress.compressors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import com.chunkr.compress.Compressor;

public class ExpressionCompressorTest {

	@Test
	public void testDeflate() throws Exception {	
		Compressor compressor = new ExpressionCompressor();
		
		// Deflate and inflate the data and see how different the data is; this
		// test is more to see if the algorithm is working properly than
		// anything else.
		byte[] data = "Is".getBytes();
		InputStream deflateIn = new ByteArrayInputStream(data);
		ByteArrayOutputStream deflateOut = new ByteArrayOutputStream();
		compressor.deflate(deflateIn, deflateOut, 6);
		deflateIn.close();
		deflateOut.close();
		
		System.out.println("Before: " + data.length + ", After: " + deflateOut.size());
		
		InputStream inflateIn = new ByteArrayInputStream(deflateOut.toByteArray());
		ByteArrayOutputStream inflateOut = new ByteArrayOutputStream();
		compressor.inflate(inflateIn, inflateOut);
		
		inflateIn.close();
		inflateOut.close();
		
		System.out.println("Before: " + Arrays.toString(data) + 
				", After: " + Arrays.toString(inflateOut.toByteArray()));
	}
	
}

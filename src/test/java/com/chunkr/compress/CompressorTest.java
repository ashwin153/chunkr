package com.chunkr.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Test;

import com.chunkr.compress.compressors.ExpressionCompressor;

public class CompressorTest {

	@Test
	public void testDeflate() throws Exception {	
		ExpressionCompressor compressor = new ExpressionCompressor();
		
		// Deflate and inflate the data and see how different the data is; this
		// test is more to see if the algorithm is working properly than
		// anything else.
		byte[] data = "Is this the real life or is this just fantasy".getBytes();
		InputStream deflateIn = new ByteArrayInputStream(data);
		ByteArrayOutputStream deflateOut = new ByteArrayOutputStream();
		compressor.deflate(deflateIn, deflateOut);
		deflateIn.close();
		deflateOut.close();
		
		System.out.println("Before: " + data.length + ", After: " + deflateOut.size());
		
		InputStream inflateIn = new ByteArrayInputStream(deflateOut.toByteArray());
		ByteArrayOutputStream inflateOut = new ByteArrayOutputStream();
		compressor.inflate(inflateIn, inflateOut);
		
		inflateIn.close();
		inflateOut.close();
		
		System.out.println("Before: " + new String(data) + 
				", After: " + new String(inflateOut.toByteArray()));
	}
	
}

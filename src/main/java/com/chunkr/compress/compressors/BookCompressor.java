package com.chunkr.compress.compressors;

import java.io.InputStream;
import java.io.OutputStream;

import com.chunkr.compress.Compressor;

public class BookCompressor implements Compressor {

	public BookCompressor(int pow) {
		
	}
	
	@Override
	public void deflate(InputStream input, OutputStream output) throws Exception {
		// 0 0 1 1 0 1 0
		
		// 0 0 -> 0
		// 0 1 -> 1
		// 1 1 -> 2
		// 1 0 -> 3
		// 0 0 1 -> 0
		// 0 1 1 -> 1
		// 1 1 0 -> 2
		// 1 0 1 -> 3*
		// 0 1 0 -> 4*
		// 0 0 1 1 -> 0
		// 0 1 1 0 -> 1
		// 1 1 0 1 -> 2*
		// 1 0 1 0 -> 3*
		// 0 0 1 1 0 -> 0
		// 0 1 1 0 1 -> 1*
		// 1 1 0 1 0 -> 2*
		
		
	}

	@Override
	public void inflate(InputStream input, OutputStream output)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}

package com.chunkr.compress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Compressor {

	/**
	 * Deflates the bytes in the specified input stream and places the
	 * compressed bytes into the specified output stream.
	 * 
	 * @param input input stream
	 * @param output output stream
	 * @param chunkSize size of chunks
	 * @throws IOException read/write error
	 */
	public void deflate(InputStream input, OutputStream output) throws Exception;

	/**
	 * Inflates the bytes in the specified input stream and places the
	 * uncompressed bytes into the specified output stream.
	 * 
	 * @param input input stream
	 * @param output output stream
	 * @throws IOException read/write error
	 * @throws ClassNotFoundException read error
	 */
	public void inflate(InputStream input, OutputStream output) throws Exception;
	
}
package com.chunkr.compress.encoders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.chunkr.compress.Archive;

public interface Encoder {

	/**
	 * Reads an archive from an input stream.
	 * 
	 * @param stream
	 * @return
	 */
	public Archive read(InputStream stream) throws IOException ;
	
	/**
	 * Writes an archive to an output stream.
	 * 
	 * @param archive
	 * @param stream
	 */
	public void write(Archive archive, OutputStream stream) throws IOException;
	
}

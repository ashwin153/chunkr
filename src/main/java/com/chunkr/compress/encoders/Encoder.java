package com.chunkr.compress.encoders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.chunkr.compress.Archive;

/**
 * Encoders are objects that read and write objects to and from streams.
 * Encoders can have a significant impact on compression ratios.
 * 
 * @author ashwin
 * @see Archive
 */
public interface Encoder {

	/**
	 * Reads an archive from an input stream.
	 * 
	 * @param stream input stream
	 * @return decoded archive
	 */
	public Archive read(InputStream stream) throws IOException ;
	
	/**
	 * Writes an archive to an output stream.
	 * 
	 * @param archive input archive
	 * @param stream output stream
	 */
	public void write(Archive archive, OutputStream stream) throws IOException;
	
}

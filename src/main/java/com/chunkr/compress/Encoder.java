package com.chunkr.compress;

import java.io.InputStream;
import java.io.OutputStream;

public interface Encoder {
	
	/**
	 * Deserializes the stream back into an expression.
	 * 
	 * @param ois
	 * @return
	 */
	public Expression read(InputStream stream);
	
	/**
	 * Serializes the expression into a stream of bytes; this stream can be
	 * wrapped into other streams (e.x., FileOutputStream) to manipulate the
	 * data.
	 * 
	 * @param expression
	 * @return
	 */
	public void write(Expression expression, OutputStream stream);

}
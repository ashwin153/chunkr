package com.chunkr.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.junit.Test;


public class DeflaterTest {

	@Test
	public void testDeflate() throws Exception {		
		ByteArrayInputStream input = new ByteArrayInputStream("Is".getBytes());
		FileOutputStream output = new FileOutputStream(new File("./archive.mad"));

		Deflater deflater = new Deflater(input, output);
		deflater.run();
		output.close();
		
		FileInputStream fis = new FileInputStream(new File("./archive.mad"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Inflater inflater = new Inflater(fis, baos);
		inflater.run();
		
		System.out.println(Arrays.toString("Is".getBytes()));
		System.out.println(Arrays.toString(baos.toByteArray()));
		System.out.println(baos.toString());
	}
	
}

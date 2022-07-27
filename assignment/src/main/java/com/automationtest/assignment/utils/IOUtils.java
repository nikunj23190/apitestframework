package com.automationtest.assignment.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {
	
	private IOUtils() {
		
	}
	
	
	/**
	 * Read all data from <code> inputStream</code> and convert to array of bytes
	 */

	public static byte[] readAll(InputStream inputStream) throws IOException{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];
		while((nRead = inputStream.read(data,0,data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}
}

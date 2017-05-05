/**
 * @author daelly
 * @date 2017年5月4日
 */
package com.daelly.sample.io.nio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author daelly
 *
 */
public class FileChannelDemo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		RandomAccessFile afile = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\datas.txt", "rw");
		FileChannel inChannel = afile.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int byteRead = inChannel.read(buffer);
		while(byteRead != -1) {
			System.out.println("Read: " + byteRead);
			buffer.flip();
			byte[] bytes = new byte[buffer.remaining()];
			if(buffer.hasRemaining()) {
				buffer.get(bytes);
				System.out.println(new String(bytes, "UTF-8"));
			}
			buffer.clear();
			byteRead = inChannel.read(buffer);
		}
		afile.close();
	}

}

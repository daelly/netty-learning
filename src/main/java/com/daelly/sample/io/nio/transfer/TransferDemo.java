/**
 * @author daelly
 * @date 2017年5月4日
 */
package com.daelly.sample.io.nio.transfer;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author daelly
 *
 */
public class TransferDemo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		RandomAccessFile fromFile = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\from.txt", "rw");
		RandomAccessFile toFile = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\to.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();
		FileChannel toChannel = toFile.getChannel();
		long position = 0;
		long count = fromChannel.size();
		toChannel.transferFrom(fromChannel, position, count);
//		fromChannel.transferTo(position, count, toChannel);
		fromFile.close();
		toFile.close();
	}

}

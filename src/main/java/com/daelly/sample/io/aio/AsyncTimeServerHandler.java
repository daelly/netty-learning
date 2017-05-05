/**
 * @author daelly
 * @date 2017年5月4日
 */
package com.daelly.sample.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author daelly
 *
 */
public class AsyncTimeServerHandler implements Runnable {
	
	
	private AsynchronousServerSocketChannel channel;
	
	private CountDownLatch latch;
	
	public AsyncTimeServerHandler(int port) {
		try {
			channel = AsynchronousServerSocketChannel.open();
			channel.bind(new InetSocketAddress(port));
			System.out.println("The time server is start in port : " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AsynchronousServerSocketChannel getChannel() {
		return channel;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void doAccept() {
		channel.accept(this, new AcceptCompletionHandler());
	}

}

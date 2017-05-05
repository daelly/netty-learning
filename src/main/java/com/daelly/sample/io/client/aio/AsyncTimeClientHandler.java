/**
 * @author daelly
 * @date 2017年5月5日
 */
package com.daelly.sample.io.client.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author daelly
 *
 */
public class AsyncTimeClientHandler implements CompletionHandler<Void, AsyncTimeClientHandler>, Runnable {
	
	private AsynchronousSocketChannel client;
	private String host;
	private int port;
	private CountDownLatch latch;
	
	/**
	 * 
	 */
	public AsyncTimeClientHandler(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			client = AsynchronousSocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立连接成功的回调
	 */
	@Override
	public void completed(Void result, AsyncTimeClientHandler attachment) {
		byte[] bytes = "QUERY TIME ORDER".getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		client.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {

			/**
			 * 写数据成功的回调
			 */
			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				if(attachment.hasRemaining()) {
					client.write(attachment, null, this);
				} else {
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {

						/**
						 * 读数据成功的回调
						 */
						@Override
						public void completed(Integer result, ByteBuffer attachment) {
							attachment.flip();
							byte[] bytes = new byte[attachment.remaining()];
							attachment.get(bytes);
							String body = null;
							try {
								body = new String(bytes, "UTF-8");
								System.out.println("Now is:" + body);
								latch.countDown();
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void failed(Throwable exc, ByteBuffer attachment) {
							try {
								client.close();
								latch.countDown();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				try {
					client.close();
					latch.countDown();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
		exc.printStackTrace();
		try {
			client.close();
			latch.countDown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		//连接，设置回调，在回调里写
		client.connect(new InetSocketAddress(host, port), this, this);
		try {
			latch.await();
		} catch (InterruptedException e) {  
			e.printStackTrace();
		}
		
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

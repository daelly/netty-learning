/**
 * @author daelly
 * @date 2017年4月28日
 */
package com.daelly.sample.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author daelly
 *
 */
public class MultiplexerTimeServer implements Runnable {
	
	private Selector selector;
	
	private ServerSocketChannel serverChannel;
	
	private volatile boolean stop = false;
	
	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(new InetSocketAddress(port), 1024);
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("The time server is start in port : " + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void stop() {
		this.stop = true;
	}

	public void run() {
		while(!stop) {
			try {
				selector.select(1000);//这里select方法会隔1秒返回，取出所有就绪状态的Channel，然后下面遍历这些准备好的key，依次做处理
				//select也有一个不带参数的方法，不带参数的方法会一直停住，直到有一个就绪的Channel时，selector返回该就绪状态的SelectionKey集合
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				while(it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (IOException e) {
						e.printStackTrace();
						if(key != null) {
							key.cancel();
							if(key.channel() != null) {
								key.channel().close();
							}
						}
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException {
		if(key.isValid()) {
			if(key.isAcceptable()) {//是否是一个新的socket连接进来
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();//完成TCP三次握手,TCP物理链路正式建立
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);//注册当前socket的read事件到selector中。
			}
			
			if(key.isReadable()) {//是否是一个读操作准备就绪了
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(buffer);
				if(readBytes > 0) {
					buffer.flip();
					byte[] bytes = new byte[buffer.remaining()];
					buffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("The time server receive order : " + body);
					String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
					doWrite(sc, currentTime);
				} else if(readBytes < 0) {
					key.cancel();
					sc.close();
				} else {
					//pass
				}
			}
		}
	}
	
	private void doWrite(SocketChannel channel, String response) throws IOException {
		if(response != null && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
			buffer.put(bytes);
			buffer.flip();
			channel.write(buffer);
			if(!buffer.hasRemaining()) {
				System.out.println("Return client : " + response);
			}
		}
	}

}

/**
 * @author daelly
 * @date 2017年4月28日
 */
package com.daelly.sample.io.nio;

/**
 * @author daelly
 *
 */
public class TimeServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 8080;
		if(args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				//pass
			}
		}
		
		MultiplexerTimeServer server = new MultiplexerTimeServer(port);
		new Thread(server, "NIO-MultiplexerTimeServer-001").start();
	}

}

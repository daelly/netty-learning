/**
 * @author daelly
 * @date 2017年4月28日
 */
package com.daelly.sample.io.client.nio;

/**
 * @author daelly
 *
 */
public class TimeClient {

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
		
		new Thread(new TimeClientHandler("127.0.0.1", port), "TimeClient-001").start();
	}

}

/**
 * @author daelly
 * @date 2017年5月4日
 */
package com.daelly.sample.io.aio;

/**
 * @author daelly
 *
 * aio就是每个操作都会设置一个回调，在回调中处理下一步需要执行的操作
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
		
		AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(port);
		new Thread(timeServerHandler, "AIO-AsyncTimeServerHandler-001").start();
	}

}

/**
 * @author daelly
 * @date 2017年4月28日
 */
package com.daelly.sample.io.faio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.daelly.sample.io.faio.executor.TimeServerHandlerExcutePool;
import com.daelly.sample.io.handler.TimeServerHandler;

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
		if( args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				//pass
			}
		}
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("The time server is start in port : " + port);
			Socket socket = null;
			TimeServerHandlerExcutePool executor = new TimeServerHandlerExcutePool(50, 1000);
			while(true) {
				socket = server.accept();
				executor.execute(new TimeServerHandler(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(server != null) {
				System.out.println("The time server close");
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				server = null;
			}
		}
	}

}

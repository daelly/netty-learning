package com.daelly.sample.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.daelly.sample.io.handler.TimeServerHandler;

public class TimeServer {

	public static void main(String[] args) {
		int port = 8080;
		if(args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("The time server is start in port : " + port);
			Socket socket = null;
			while(true) {
				socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();				
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

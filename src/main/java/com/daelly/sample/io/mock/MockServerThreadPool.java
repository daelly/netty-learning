/**
 * @author daelly
 * @date 2017年5月5日
 */
package com.daelly.sample.io.mock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author daelly
 *
 */
public class MockServerThreadPool {

	public static void main(String[] args) {
		MockController controller = new MockController();
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for(int i=0; i<10; i++) {
			executor.execute(new MockRequest(controller));
		}
	}
}

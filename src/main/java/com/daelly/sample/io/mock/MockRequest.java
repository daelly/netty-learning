/**
 * @author daelly
 * @date 2017年5月5日
 */
package com.daelly.sample.io.mock;

/**
 * @author daelly
 *
 */
public class MockRequest implements Runnable {

	private final MockController controller;
	
	public MockRequest(MockController controller) {
		this.controller = controller;
	}
	
	@Override
	public void run() {
		controller.doSth();
	}
	
}

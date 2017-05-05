/**
 * @author daelly
 * @date 2017年5月5日
 */
package com.daelly.sample.io.mock;

/**
 * @author daelly
 *
 */
public class MockController {

	public void doSth() {
		Thread thread = Thread.currentThread();
		String ci = thread.getName();
		System.out.println(ci);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

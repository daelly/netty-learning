/**
 * @author daelly
 * @date 2017年4月28日
 */
package com.daelly.sample.io.faio.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author daelly
 *
 */
public class TimeServerHandlerExcutePool {

	private ExecutorService executor;
	
	public TimeServerHandlerExcutePool(int maxPoolSize, int queueSize) {
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(queueSize);
		executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS, workQueue);
	}
	
	public void execute(Runnable task) {
		executor.execute(task);
	}
}

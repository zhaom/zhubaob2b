package com.zhubao.b2b.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Xinyu
 * 
 */
public class NamedThreadFactory implements ThreadFactory {
	private final String prefix;
	private final boolean isDaemon;
	private final AtomicInteger threadCount = new AtomicInteger(1);
	private final ThreadGroup threadGroup;

	/**
	 * Constructor
	 * 
	 * @param prefix
	 *            thread prefix name
	 * @param isDaemon
	 *            is a daemon thread
	 */
	public NamedThreadFactory(String prefix, boolean isDaemon) {
		this.prefix = prefix + "-thread-";
		this.isDaemon = isDaemon;
		SecurityManager s = System.getSecurityManager();
		threadGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s
		        .getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable r) {
		String name = prefix + threadCount.incrementAndGet();
		Thread ret = new Thread(threadGroup, r, name, 0);
		ret.setDaemon(isDaemon);
		return ret;
	}

	/**
	 * @return thread group
	 */
	public ThreadGroup getThreadGroup() {
		return threadGroup;
	}
}
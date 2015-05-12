package com.zhubao.b2b.common.thread;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Thread pool object for monitor
 * 
 */
public class ThreadPool implements ThreadPoolMBean {

	private final ThreadPoolExecutor threadPoolExecutor;
	private final String poolName;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            pool name
	 * @param threadPoolExecutor
	 *            executor
	 */
	public ThreadPool(String name, ThreadPoolExecutor threadPoolExecutor) {
		super();
		this.poolName = name;
		this.threadPoolExecutor = threadPoolExecutor;
	}

	@Override
	public String getPoolName() {
		return poolName;
	}

	@Override
	public int getActiveCount() {
		return this.threadPoolExecutor.getActiveCount();
	}

	@Override
	public int getCorePoolSize() {
		return this.threadPoolExecutor.getCorePoolSize();
	}

	@Override
	public int getMaximumPoolSize() {
		return this.threadPoolExecutor.getMaximumPoolSize();
	}

	@Override
	public int getPoolSize() {
		return this.threadPoolExecutor.getPoolSize();
	}

	@Override
	public int getQueueLength() {
		return this.threadPoolExecutor.getQueue().size();
	}

	@Override
	public boolean isAllowCoreThreadTimeout() {
		return this.threadPoolExecutor.allowsCoreThreadTimeOut();
	}

	@Override
	public void setCorePoolSize(int corePoolSize) {
		this.threadPoolExecutor.setCorePoolSize(corePoolSize);
	}

	@Override
	public void setMaximumPoolSize(int maximumPoolSize) {
		this.threadPoolExecutor.setMaximumPoolSize(maximumPoolSize);
	}

	@Override
	public long getKeepAliveTimeInSec() {
		return this.threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS);
	}

	@Override
	public void setKeepAliveTimeInSec(long seconds) {
		this.threadPoolExecutor.setKeepAliveTime(seconds, TimeUnit.SECONDS);
	}
}

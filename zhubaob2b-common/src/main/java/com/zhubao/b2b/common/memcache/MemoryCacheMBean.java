package com.zhubao.b2b.common.memcache;

/**
 * @author Xinyu
 * 
 */
public interface MemoryCacheMBean {
	/**
	 * @return memory cahce name
	 */
	String getMemoryCacheName();

	/**
	 * @return current size
	 */
	int getMemoryCacheSize();

	/**
	 * @return max size
	 */
	int getMemoryCacheCapacity();

	/**
	 * @return current reflesh interval
	 */
	long getRefleshInterval();

	/**
	 * @param newInterval
	 *            new interval
	 * @return return true if set successful
	 */
	boolean setRefleshInterval(long newInterval);

	/**
	 * @return return true if memory cache in working
	 */
	boolean isMemoryCacheWorking();

	/**
	 * @return stop memory cache
	 */
	boolean disableMemoryCache();

	/**
	 * @return start memory cache
	 */
	boolean enableMemoryCache();
}

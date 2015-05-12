package com.zhubao.b2b.common.memcache;

import com.zhubao.b2b.common.mbean.MBeanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 
 */
public class MemoryCache implements MemoryCacheMBean {
	private static final Logger logger = LoggerFactory
	        .getLogger(MemoryCache.class);

	private static AtomicInteger count = new AtomicInteger(1);

	private final ConcurrentHashMap<String, Object> cacheTable;
	private final int initialCapacity;
	private final String name;
	private long refleshInterval;
	private boolean inWorking = false;
	private Timer updateDate = new Timer(true);
	private TimerTask refleshTask = createTask();

	private TimerTask createTask() {
		return new TimerTask() {
			@Override
			public void run() {
				logger.debug("[MemoryCache] Cache clear!");
				cacheTable.clear();
			}
		};
	}

	/**
	 * Constructor
	 */
	public MemoryCache() {
		this("MemoryCache-" + count.getAndIncrement(), -1, 60 * 1000);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param initialCapacity
	 *            size
	 * @param refleshInterval
	 *            interval
	 */
	public MemoryCache(String name, int initialCapacity, int refleshInterval) {
		this.name = name;
		this.initialCapacity = initialCapacity;
		this.refleshInterval = refleshInterval;
		if (initialCapacity > 0) {
			cacheTable = new ConcurrentHashMap<String, Object>(initialCapacity);
		} else {
			cacheTable = new ConcurrentHashMap<String, Object>();
		}

		refleshInterval = 5 * 60 * 1000;
		updateDate.scheduleAtFixedRate(refleshTask, refleshInterval,
		        refleshInterval);
		inWorking = true;
		MBeanManager.registerMBean(
                "MemoryCache." + MemoryCache.class.getSimpleName() + ":name="
                        + name, this);
	}

	/**
	 * Register key-object to table
	 * 
	 * @param key
	 *            key
	 * @param obj
	 *            object
	 * @return return true if register successful.
	 */
	public boolean register(final String key, final Object obj) {
		boolean ret = false;
		if (!inWorking) {
			logger.error("[MemoryCache] Disabled!");
			return false;
		}
		if (key == null || obj == null) {
			logger.error("[MemoryCache] Invalid parameter!");
			return false;
		}
		if (initialCapacity > 0) {
			if (cacheTable.size() < initialCapacity) {
				if (!cacheTable.containsKey(key)) {
					cacheTable.put(key, obj);
				}
				logger.debug("[MemoryCache] {} registered", key);
				ret = true;
			} else {
				logger.error("[MemoryCache] {} failed caused by full!", key);
			}
		} else {
			if (!cacheTable.containsKey(key)) {
				cacheTable.put(key, obj);
			}
			logger.debug("[MemoryCache] {} registered", key);
			ret = true;
		}
		return ret;
	}

	/**
	 * Find object by key
	 * 
	 * @param key
	 *            key
	 * @return object
	 */
	public Object resolve(String key) {
		if (!inWorking) {
			logger.error("[MemoryCache] Disabled!");
			return null;
		}
		if (cacheTable.containsKey(key)) {
			return cacheTable.get(key);
		} else {
			logger.error("[MemoryCache] {} missed! ", key);
			return null;
		}
	}

	/**
	 * Remove pair key - object from table by key
	 * 
	 * @param key
	 *            key
	 */
	public void unRegister(String key) {
		if (!inWorking) {
			logger.error("[MemoryCache] Disabled!");
			return;
		}
		if (cacheTable.containsKey(key)) {
			cacheTable.remove(key);
			logger.debug("[MemoryCache] {} unregister.", key);
		} else {
			logger.error("[MemoryCache] {} is not in cache! ", key);
		}
	}

	@Override
	public String getMemoryCacheName() {
		return name;
	}

	@Override
	public int getMemoryCacheSize() {
		return cacheTable.size();
	}

	@Override
	public int getMemoryCacheCapacity() {
		if (initialCapacity > 0) {
			return initialCapacity;
		}
		return Integer.MAX_VALUE;
	}

	@Override
	public long getRefleshInterval() {
		return refleshInterval;
	}

	@Override
	public synchronized boolean setRefleshInterval(long refleshInterval) {
		if (!inWorking) {
			logger.error("[MemoryCache] Disabled!");
			return false;
		}
		if (refleshTask.cancel()) {
			logger.debug("new reflesh interval = {}", refleshInterval);
			updateDate.scheduleAtFixedRate(createTask(), refleshInterval,
			        refleshInterval);
			return true;
		}
		return false;
	}

	@Override
	public boolean isMemoryCacheWorking() {
		return inWorking;
	}

	@Override
	public boolean disableMemoryCache() {
		if (inWorking) {
			if (refleshTask.cancel()) {
				inWorking = false;
				logger.debug("[MemoryCache] Switch to disable!");
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean enableMemoryCache() {
		if (!inWorking) {
			updateDate.scheduleAtFixedRate(createTask(), refleshInterval,
			        refleshInterval);
			logger.debug("[MemoryCache] Switch to enable!");
			inWorking = true;
			return true;
		}
		return true;
	}
}

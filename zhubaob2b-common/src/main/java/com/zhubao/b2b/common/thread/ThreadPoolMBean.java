package com.zhubao.b2b.common.thread;

/**
 * Interface for monitor executor
 */
public interface ThreadPoolMBean {
    /**
     * @return thread pool name
     */
    String getPoolName();

    /**
     * @return the approximate number of threads that are actively executing
     *         tasks.
     */
    int getActiveCount();

    /**
     * @return the core number of threads.
     */
    int getCorePoolSize();

    /**
     * @return the maximum allowed number of threads
     */
    int getMaximumPoolSize();

    /**
     * @return the current number of threads in the pool.
     */
    int getPoolSize();

    /**
     * @return the size of task queue used by this executor
     */
    int getQueueLength();

    /**
     * @return Returns true if this pool allows core threads to time out and
     *         terminate if no tasks arrive within the keepAlive time, being
     *         replaced if needed when new tasks arrive. When true, the same
     *         keep-alive policy applying to non-core threads applies also to
     *         core threads. When false (the default), core threads are never
     *         terminated due to lack of incoming tasks.
     */
    boolean isAllowCoreThreadTimeout();

    /**
     * @return the thread keep-alive time in seconds
     */
    long getKeepAliveTimeInSec();

    /**
     * @param corePoolSize the new core size
     */
    void setCorePoolSize(int corePoolSize);

    /**
     * @param getMaximumPoolSize the new maximum
     */
    void setMaximumPoolSize(int getMaximumPoolSize);

    /**
     * @param seconds the thread keep-alive time in seconds
     */
    void setKeepAliveTimeInSec(long seconds);
}

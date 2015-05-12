package com.zhubao.b2b.common.mbean;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaomin
 */
public class MBeanManager {
    private static final Logger logger = LoggerFactory
            .getLogger(MBeanManager.class);

    /**
     * Register MBean
     *
     * @param name  bean name
     * @param mbean MBean object
     * @return return true if register successful.
     */
    public static boolean registerMBean(String name, Object mbean) {
        boolean ret = false;
        try {
            ObjectName mbeanName = new ObjectName(name);
            logger.debug("Registering mbean: {}", mbeanName.getCanonicalName());
            MBeanServer mBeanServer = ManagementFactory
                    .getPlatformMBeanServer();

            if (!mBeanServer.isRegistered(mbeanName)) {
                mBeanServer.registerMBean(mbean, mbeanName);
                ret = true;
            } else {
                logger.error("mbean {} had been registered!", mbeanName);
            }
        } catch (Exception e) {
            logger.error("Error register mbean.Name: {}, E: {}", name,
                    e.getMessage());
        }

        return ret;
    }
}

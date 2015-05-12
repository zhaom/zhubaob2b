package com.zhubao.b2b.common.id;

import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

import com.sun.corba.se.spi.ior.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IdFactory {
	private final static Logger logger = LoggerFactory
			.getLogger(IdFactory.class);
    private static AtomicInteger nextInc = new AtomicInteger((new java.util.Random()).nextInt());

    private static final int machineToken;
    private static final int proccessToken;
    static {
        try {
            // build a 2-byte machine piece based on NICs info
            {
                StringBuilder sb = new StringBuilder();
                Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
                while ( e.hasMoreElements() ){
                    NetworkInterface ni = e.nextElement();
                    sb.append( ni.toString() );
                }
                machineToken = sb.toString().hashCode();
                logger.info("[IdFactory] Machine Token = " + machineToken);
            }

            // add a 2 byte process piece. It must represent not only the JVM but the class loader.
            // Since static var belong to class loader there could be collisions otherwise
            {
                int processId = new java.util.Random().nextInt();
                try {
                    processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
                }
                catch ( Throwable t ){
                }

                ClassLoader loader = ObjectId.class.getClassLoader();
                int loaderId = loader != null ? System.identityHashCode(loader) : 0;

                StringBuilder sb = new StringBuilder();
                sb.append(Integer.toHexString(processId));
                sb.append(Integer.toHexString(loaderId));
                proccessToken = sb.toString().hashCode();
                logger.info("[IdFactory] Proccess Token = " + proccessToken);
            }
        } catch (java.io.IOException ioe) {
        	logger.error("[IdFactory] Exception!!!! {}", ioe.getMessage());
            throw new RuntimeException(ioe);
        }
    }
	
	
	public static String generateId() {
        byte b[] = new byte[16];
        ByteBuffer bb = ByteBuffer.wrap( b );
        // by default BB is big endian like we need
        bb.putInt((int)(System.currentTimeMillis() / 1000));
        bb.putInt(machineToken);
        bb.putInt(proccessToken);
        bb.putInt(nextInc.getAndIncrement());
        
        StringBuilder buf = new StringBuilder(32);
        
        for (int i=0; i < b.length; i++){
            int x = b[i] & 0xFF;
            String s = Integer.toHexString(x);
            if (s.length() == 1) {
                buf.append("0");
            }
            buf.append(s);
        }

        return buf.toString();
	}
}

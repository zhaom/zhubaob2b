package com.zhubao.b2b.web.auth;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;

import net.spy.memcached.MemcachedClient;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.utils.Assert;

public class MemcacheSessionDao extends AbstractSessionDAO {

	private static final Logger logger = LoggerFactory.getLogger(MemcacheSessionDao.class);

	@Resource
	private MemcachedClient memcachedClient;

	private int timeout = 3600;

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout < 0 ? 0 : timeout;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		if (session == null) {
			throw new NullPointerException("session argument cannot be null.");
		}

		Serializable sessionId = session.getId();
		if (sessionId != null) {
			memcachedClient.replace(sessionId.toString(), timeout, session);
			// logger.info("update session {}", sessionId);
		}
	}

	@Override
	public void delete(Session session) {
		if (session == null) {
			throw new NullPointerException("session argument cannot be null.");
		}

		Serializable sessionId = session.getId();
		if (sessionId != null) {
			memcachedClient.delete(sessionId.toString());
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return null;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);

		Assert.isNotNull(sessionId, "id argument cannot be null.");
		assignSessionId(session, sessionId);

		memcachedClient.add(sessionId.toString(), timeout, session);
		logger.info("add session {}", sessionId);

		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return sessionId == null ? null : (Session) memcachedClient.get(sessionId.toString());
	}

}
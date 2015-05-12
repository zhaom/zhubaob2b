package com.zhubao.b2b.web.auth;

import com.zhubao.b2b.platform.model.Customer;

public final class SimpleAuthenticationInfo extends org.apache.shiro.authc.SimpleAuthenticationInfo {

	private static final long serialVersionUID = 1995195442009852499L;

	private Customer user;

	public SimpleAuthenticationInfo() {
	}

	public SimpleAuthenticationInfo(Object principal, Object credentials, String realmName,
			Customer user) {
		super(principal, credentials, realmName);
		this.setUser(user);
	}

	public Customer getUser() {
		return user;
	}

	public void setUser(Customer user) {
		this.user = user;
	}

}
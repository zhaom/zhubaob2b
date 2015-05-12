package com.zhubao.b2b.web.auth;

public final class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = -6497433198512485513L;

	public UsernamePasswordToken() {
	}

	public UsernamePasswordToken(String username, String password) {
		super(username, password);
	}

	public UsernamePasswordToken(String username, String password, boolean rememberMe) {
		super(username, password, rememberMe);
	}

}
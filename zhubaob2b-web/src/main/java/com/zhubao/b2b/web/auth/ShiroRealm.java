package com.zhubao.b2b.web.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.service.UserService;
import com.zhubao.b2b.web.utils.Constants;

public final class ShiroRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		String username = token.getUsername();

		if (username != null && "".equals(username) == false) {
			Customer customer = null;

			String password = new String(token.getPassword());
			customer = userService.getIsLegalCustomer(username, password);

			if (customer != null) {
				AuthenticationInfo info = new SimpleAuthenticationInfo(customer.getKshopUserName(),
						password, username, customer);

				Subject subject = SecurityUtils.getSubject();
				Session session = subject.getSession();
				session.setAttribute(Constants.USER_LOGIN_SESSION_KEY, customer);
				logger.info("login user: {}=>{} success", username, customer.getKshopUserName());

				return info;
			} else {
				logger.error("Could not find username: {}", username);
				throw new UnknownAccountException("Could not find username: " + username);
			}
		} else {
			logger.error("Username could not be empty");
			throw new AuthenticationException("Username could not be empty");
		}
	}
}
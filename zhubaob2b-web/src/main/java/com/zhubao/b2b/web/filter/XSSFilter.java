package com.zhubao.b2b.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public final class XSSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		if (request != null) {
			Map<String, String[]> parameters = request.getParameterMap();

			if (parameters != null) {
				for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
					entry.setValue(replace(entry.getValue()));
				}
			}
		}

		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	private static String[] replace(String[] str) {
		if (str != null && str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				if (str[i] != null && str[i].length() > 0) {
					str[i] = str[i].replace("&", "&lt;");
					str[i] = str[i].replace("<", "&lt;").replace(">", "&gt;");
					str[i] = str[i].replace("'", "\'").replace("\"", "\\\"");
				}
			}
		}

		return str;
	}

}

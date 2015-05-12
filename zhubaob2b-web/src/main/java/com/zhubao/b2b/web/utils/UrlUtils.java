package com.zhubao.b2b.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

public final class UrlUtils {

	HttpServletRequest request;

	public UrlUtils(HttpServletRequest request) {
		Assert.notNull(request, "Request must not be null");

		this.request = request;
	}

	public String parseUrl(Map<String, List<String>> parameters) {
		String url = request.getPathInfo();

		if (parameters == null) {
			String queryString = request.getQueryString();
			if (queryString != null && queryString.length() > 0) {
				url += "?" + queryString;
			}
		} else {
			String queryString = parseParameters(request, parameters);

			if (queryString != null && queryString.length() > 0) {
				url += "?" + queryString;
			}
		}

		return url;
	}

	public String parseUrl(String name, String[] values) {
		Assert.hasLength(name, "Parameter name must not be empty");

		Map<String, List<String>> parameters = new HashMap<String, List<String>>();
		List<String> params = null;

		if (values != null && values.length > 0) {
			params = new ArrayList<String>();
			for (int i = 0; i < values.length; i++) {
				params.add(values[i]);
			}
		}

		parameters.put(name, params);

		return parseUrl(parameters);
	}

	public String parseUrl(String name, List<String> values) {
		Assert.hasLength(name, "Parameter name must not be empty");

		Map<String, List<String>> parameters = new HashMap<String, List<String>>();

		if (values == null) {
			parameters.put(name, null);
		} else {
			parameters.putAll(parameters);
		}

		return parseUrl(parameters);
	}

	public String parseUrl(String name, Object value) {
		if (value == null) {
			Assert.hasLength(name, "Parameter name must not be empty");

			Map<String, List<String>> parameters = new HashMap<String, List<String>>();

			parameters.put(name, null);

			return parseUrl(parameters);
		} else {
			return parseUrl(name, new String[]{value.toString()});
		}
	}

	private static String parseParameters(HttpServletRequest request,
			Map<String, List<String>> parameters) {
		Map<String, String[]> params = request.getParameterMap();
		Map<String, List<String>> _params = new TreeMap<String, List<String>>();
		String str = "";

		if (params != null) {
			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				List<String> values = null;
				String[] vals = entry.getValue();

				for (int i = 0; i < vals.length; i++) {
					if (i == 0) {
						values = new ArrayList<String>();
					}

					values.add(vals[i]);
				}

				_params.put(entry.getKey(), values);
			}
		}

		if (parameters != null) {
			_params.putAll(parameters);
		}

		int i = 0;

		for (Map.Entry<String, List<String>> entry : _params.entrySet()) {
			List<String> vals = entry.getValue();

			if (vals == null) {
				continue;
			} else if (vals.size() == 0) {
				if (i > 0) {
					str += "&";
				}

				str += entry.getKey();
				++i;
				continue;
			}

			int j = 0;
			for (String v : vals) {
				if (i == 0 && j > 0 || i > 0) {
					str += "&";
				}

				str += entry.getKey() + "=" + (v == null ? "" : v);
				++j;
			}

			++i;
		}

		return str;
	}
}
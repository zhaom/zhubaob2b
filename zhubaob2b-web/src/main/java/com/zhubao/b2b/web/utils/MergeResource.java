package com.zhubao.b2b.web.utils;

import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;
import org.apache.velocity.tools.generic.FormatConfig;

@DefaultKey("mergeResource")
@ValidScope(Scope.APPLICATION)
public final class MergeResource extends FormatConfig {

	public String css(String files) {
		return css(files, "css");
	}

	public String css(String files, String baseDir) {
		String baseUrl = Constants.IMAGE_URL + (baseDir != null ? baseDir : "");

		try {
			return com.zhubao.common.utils.MergeResource.css(files, baseUrl);
		} catch (NoClassDefFoundError e) {
			return "";
		}
	}

	public String css(String... files) {
		return css("css", files);
	}

	public String css(String baseDir, String... files) {
		String baseUrl = Constants.IMAGE_URL + (baseDir != null ? baseDir : "");

		try {
			return com.zhubao.common.utils.MergeResource.css(baseUrl, files);
		} catch (NoClassDefFoundError e) {
			return "";
		}
	}

	public String javascript(String files) {
		return javascript(files, "js");
	}

	public String javascript(String files, String baseDir) {
		String baseUrl = Constants.IMAGE_URL + (baseDir != null ? baseDir : "");

		try {
			return com.zhubao.common.utils.MergeResource.javascript(files, baseUrl);
		} catch (NoClassDefFoundError e) {
			return "";
		}
	}

	public String javascript(String... files) {
		return javascript("js", files);
	}

	public String javascript(String baseDir, String... files) {
		String baseUrl = Constants.IMAGE_URL + (baseDir != null ? baseDir : "");

		try {
			return com.zhubao.common.utils.MergeResource.javascript(baseUrl, files);
		} catch (NoClassDefFoundError e) {
			return "";
		}
	}

}
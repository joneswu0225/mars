package com.jones.mars.util;

import com.jones.mars.exception.InternalException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;

public class CodecUtil {

	public static final Charset GB2312 = Charset.forName("GB2312");

	public static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();


	private CodecUtil() {
		// dummy
	}

	public static String base64Encode(String content, Charset charset) {
		return BASE64_ENCODER.encodeToString(content.getBytes(charset));
	}


	public static String base64EncodeWithGB2312(String content) {
		return base64Encode(content, GB2312);
	}


	public static String urlEncode(String content) {
		return urlEncode(content, "UTF-8");
	}

	public static String urlEncode(String content, String charset) {
		try {
			return URLEncoder.encode(content, charset);
		} catch (UnsupportedEncodingException e) {
			throw new InternalException(e);
		}
	}

	public static String urlDecode(String content) {
		return urlDecode(content, "UTF-8");
	}

	public static String urlDecode(String content, String charset) {
		try {
			return URLDecoder.decode(content, charset);
		} catch (UnsupportedEncodingException e) {
			throw new InternalException(e);
		}
	}
}

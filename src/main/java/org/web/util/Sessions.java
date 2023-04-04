package org.web.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Sessions {

	private static Map<String, String> sessions = new HashMap<>();

	public static void add(String sessionId, String value) {
		sessions.put(sessionId, value);
	}

	public static String get(String sessionId) {
		return sessionId == null ? null : sessions.get(sessionId);
	}

	public static void delete(String sessionId) {
		sessions.remove(sessionId);
	}

	public static String createSessionId() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[10];
		random.nextBytes(bytes);
		return new BigInteger(bytes).abs().toString(16);
	}
}
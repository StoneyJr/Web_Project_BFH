package org.web.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {

	private static final String TEMPLATE_ROOT = "templates/";

	public static Map<String, String> parseParameters(String paramString) {
		Map<String, String> result = new HashMap<>();
		if (paramString != null) {
			paramString = URLDecoder.decode(paramString, StandardCharsets.UTF_8);
			for (String param : paramString.split("&")) {
				String[] pair = param.split("=");
				if (pair.length > 1) {
					result.put(pair[0], pair[1]);
				} else {
					result.put(pair[0], "");
				}
			}
		}
		return result;
	}

	public static Map<String, String> parseCookies(HttpExchange exchange) {
		List<String> cookies = exchange.getRequestHeaders().get("Cookie");
		Map<String, String> parsedCookies = new HashMap<>();
		if (cookies != null) {
			for (String cookiesEntry : cookies) {
				for (String cookie : cookiesEntry.split(";")) {
					String[] tokens = cookie.trim().split("=");
					if (tokens.length > 1) {
						parsedCookies.put(tokens[0], tokens[1]);
					}
				}
			}
		}
		return parsedCookies;
	}

	public static void sendResponse(HttpExchange exchange, String doc) throws IOException {
		byte[] bytes = doc.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().set("Content-Type", "text/html");
		exchange.sendResponseHeaders(200, bytes.length);
		exchange.getResponseBody().write(bytes);
		exchange.close();
	}

	public static void renderTemplate(String templateName, HttpExchange exchange) throws IOException {
		String doc;
		int status = 200;
		Path templatePath = Paths.get(TEMPLATE_ROOT, templateName);
		if (!Files.isRegularFile(templatePath)) {
			status = 500;
			doc = "<h2>500 - Internal Server Error</h2><p>Loading template '" + TEMPLATE_ROOT + templateName + "' failed.</p>";
		} else {
			Map<String, Object> values = exchange.getHttpContext().getAttributes();
			doc = new String(Files.readAllBytes(templatePath));
			for (String name : values.keySet()) {
				String value = values.get(name).toString();
				doc = doc.replaceAll("\\{" + name + "}", value);
			}
			doc = doc.replaceAll("\\{.*}", "");
		}
		byte[] bytes = doc.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().set("Content-Type", "text/html");
		exchange.sendResponseHeaders(status, bytes.length);
		exchange.getResponseBody().write(bytes);
		exchange.close();
	}
}

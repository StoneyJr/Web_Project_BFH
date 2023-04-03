package org.example.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticContentHandler implements HttpHandler {
    private static final String DOCUMENT_ROOT = "public";
	private static final String INDEX_FILE = "index.html";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getRequestURI().getPath();
		if (path.endsWith("/")) path += INDEX_FILE;

		Path filePath = Paths.get(DOCUMENT_ROOT + path);
		if (!Files.isRegularFile(filePath)) {
			String response = "404 - Not Found";
			exchange.sendResponseHeaders(404, response.length());
			exchange.getResponseBody().write(response.getBytes());
		} else {
			exchange.getResponseHeaders().set("Content-Type", Files.probeContentType(filePath));
			exchange.sendResponseHeaders(200, Files.size(filePath));
			exchange.getResponseBody().write(Files.readAllBytes(filePath));
		}
		exchange.close();
	}
}

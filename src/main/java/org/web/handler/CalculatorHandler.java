package org.web.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CalculatorHandler implements HttpHandler {

    private static final String DOCUMENT_ROOT = "vue";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        Path filePath = Paths.get(DOCUMENT_ROOT + "/calculator.html");
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

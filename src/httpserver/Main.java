package httpserver;

import httpserver.handler.*;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Main {
    private final static int SERVER_PORT = 8080;

	public static void main(String[] args) throws Exception {

		HttpServer server = HttpServer.create(new InetSocketAddress("localhost", SERVER_PORT), 0);
        server.createContext("/process_contact", new ContactFormHandler());
		server.createContext("/", new StaticContentHandler());
		server.start();
		System.out.println("Server ready on port " + SERVER_PORT);
	}
}

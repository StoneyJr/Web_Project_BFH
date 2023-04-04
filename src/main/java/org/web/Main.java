package org.web;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

import org.web.handler.ContactFormHandler;
import org.web.handler.ProductsHandler;
import org.web.handler.StaticContentHandler;

public class Main {

    private final static int SERVER_PORT = 8080;

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", SERVER_PORT), 0);
        server.createContext("/process_contact", new ContactFormHandler());
        server.createContext("/treatments", new ProductsHandler());
        server.createContext("/", new StaticContentHandler());
        server.start();
        System.out.println("Server ready on port " + SERVER_PORT);
    }


}

package org.example.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.util.Helper;


import java.io.IOException;
import java.util.Map;

public class ContactFormHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> params = Helper.parseParameters(new String(exchange.getRequestBody().readAllBytes()));
        StringBuilder formHeader = new StringBuilder();
        StringBuilder sentForm = new StringBuilder();

        formHeader.append("<h1>Vielen Dank folgene Informationen wurden von Ihnen an uns weitergeleitet:</h1>");
        formHeader.append("<br>");
        sentForm.append("<h2>Kontaktdaten</h2>");
        
        sentForm.append("<p class =\"middleInput\">");
        
        sentForm.append(params.get("anrede").equals("none") ? "" : (params.get("anrede") + " "));

        sentForm.append(params.get("name") + " ").append(params.get("surname") + "<br>");
        sentForm.append(params.get("mail") + "<br>");
        sentForm.append(params.get("telefon") + "</p>");

        sentForm.append("<h2>Nachricht</h2>");
        sentForm.append("<p class =\"longInput\">" + params.get("text").replaceAll("\n", "<br>") + "</p>");

        exchange.setAttribute("formHeader", formHeader);
        exchange.setAttribute("sentForm", sentForm);
        Helper.renderTemplate("contact.html", exchange);
    }
    
}

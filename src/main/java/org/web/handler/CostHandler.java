package org.web.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.web.util.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CostHandler implements HttpHandler {
    private HashMap<String, String> data = new HashMap<String, String>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> params = Helper.parseParameters(exchange.getRequestURI().getQuery());
        String filter = params.get("filter"); //url looks like costs?filter=...
        StringBuilder doc = new StringBuilder();


        //TODO for javaScript
        //doc.append("<input type=\"text\" value=\"Suche\" id=\"nameBox\">");
        //doc.append("<input type=\"button\" value=\"Go!\" id=\"submit\" onClick=\"setFilter()\"");

        doc.append("<p>Die Therapiekosten liegen je nach Praxis zwischen CHF 140.- und CHF 160.- pro Stunde. 70% bis 90% der Kosten werden in der Regel von der Zusatzversicherung übernommen.");
        doc.append("Vor Therapiebeginn empfiehlt sich aber immer die Leistungs-Abklärung bei der Krankenkasse. Die Tabelle ist eine Orientierungshilfe ohne Gewähr.</p>");

        doc.append("<ul>");
        for(String kk : getDataFromDB(filter)){
            doc.append("<li>").append(kk + ":").append("</li>");
            doc.append(data.get(kk)).append("<br>");
        }
        doc.append("</ul>\n");

        exchange.setAttribute("costs", doc);
        Helper.renderTemplate("treatments.html", exchange);
    }

    private List<String> getDataFromDB(String filter) throws IOException {
        if(data.isEmpty()){
            data.put("Aquilana" , "90%, max 1000.- resp. 2000.-");
            data.put("Assura" , "50.- bis 130.- pro Sitzung, Franchise 200.-");
            data.put("Atupri " , "50%, max. 1500.- rep. 75% max. 2500.-");
            data.put("Concordia " , "75%, max. 1500.- resp. 75% max. 2000.-");
            data.put("CSS " , "75%, max. 1000.- oder 3000.- oder 10'000.-");
            data.put("EGK " , "CHF 70.- pro Sitzung, max. 12 x p.a");
            data.put("Galenos " , "90% max 300.- / 500.- / 700.-");
            data.put("Group Mutuel" , " je nach Produkt unterschiedlich");
            data.put("Helsana " , "75%, jährliche Limite fallbezogen");
            data.put("KPT" , "90%, max. 3500.-(3) /1500.- resp. 200.- Franchise (2+3)");
        }

        List<String> kk = new ArrayList<String>(data.keySet());
        if (filter != null){
            kk = kk.stream()
					.filter(item -> item.toLowerCase().contains(filter.toLowerCase()))
					.collect(Collectors.toList());
        }
        return kk;
    }
}


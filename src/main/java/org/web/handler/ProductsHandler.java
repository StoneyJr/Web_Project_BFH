package org.web.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.web.model.Product;
import org.web.util.Helper;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductsHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> params = Helper.parseParameters(exchange.getRequestURI().getQuery());
        String filter = params.get("filter");
        StringBuilder doc = new StringBuilder();


        doc.append("<ul>");
        for (Product product : getProductsFromDB(filter)) {
            doc.append("<li>").append(product.getProductName()).append("</li>");
            doc.append(product.getDescription());
            doc.append("\n");
            doc.append("<img src=\"" + product.getImage() + "\" />");
            doc.append("</ul>\n");




        }


        exchange.setAttribute("products", doc);
        Helper.renderTemplate("treatments.html", exchange);
    }


    private List<Product> getProductsFromDB(String filter) throws IOException {


        String jsonInput = IOUtils.toString(
                Objects.requireNonNull(this.getClass().getResourceAsStream("../../../Products.json")),
                StandardCharsets.UTF_8
        );
        ObjectMapper mapper = new ObjectMapper();
        List<Product> myObjects = new ArrayList<>();
        try {

            myObjects = mapper.readValue(jsonInput, new TypeReference<>() {
            });
        } catch (
                Exception ex) {
            ex.printStackTrace();
        }

        System.out.println();


        return myObjects;
    }

}


package httpserver.handler;



import httpserver.util.Helper;
import httpserver.util.Sessions;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
//import httpserver.resources.Products;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ProductHandler implements HttpHandler {
    


	@Override
	public void handle(HttpExchange exchange)  throws IOException {
		Map<String, String> params = Helper.parseParameters(exchange.getRequestURI().getQuery());
		String filter = params.get("filter");
		StringBuilder doc = new StringBuilder();

		doc.append("<ul>");
		for(String product : getProductsFromDB(filter)) {
			doc.append("<li>").append(product).append("</li>");
		}
		doc.append("</ul>\n");

		exchange.setAttribute("products", doc);
		Helper.renderTemplate("treatments.html", exchange);
	}

	
	private List<String> getProductsFromDB(String filter) {
        
		String[] data = {"Book", "Bicycle", "Computer", "Camera", "Coffee", "Chocolate", "Table", "Chair", "Sofa", "Shoes", "Trousers", "Pullover", "Shirt"};
		List<String> products = Arrays.asList(data);
		if (filter != null) {
			products = products.stream()
					.filter(item -> item.toLowerCase().contains(filter.toLowerCase()))
					.collect(Collectors.toList());
		}
		return products;
	}
}
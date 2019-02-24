package ir.aziz.karam.pages;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class NotFound404Page extends APage implements IPage {
    private String message = "Page not found.";

    public NotFound404Page() {
    }

    public NotFound404Page(String message) {
        this.message = message;
    }


    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        String response
                = "<html>"
                + "<body>" + message + "</body>"
                + "</html>";
        this.sendPage(httpExchange, response, 404);
    }
}

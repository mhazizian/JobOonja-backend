
package ir.aziz.karam.pages;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class PermissionDenied403Page extends APage implements IPage {
    private String message = "Access Denied.";

    public PermissionDenied403Page() {
    }

    public PermissionDenied403Page(String message) {
        this.message = message;
    }


    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        String response
                = "<html>"
                + "<body>" + message + "</body>"
                + "</html>";
        this.sendPage(httpExchange, response, 403);
    }
}


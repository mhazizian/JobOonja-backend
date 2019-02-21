package ir.aziz.karam.pages;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public class ProjectListPage implements  IPage{
    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        String response = "we are good :D";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}


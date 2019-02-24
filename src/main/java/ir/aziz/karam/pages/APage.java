package ir.aziz.karam.pages;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

abstract class APage {
    void sendPage(HttpExchange httpExchange, String response, int statusCode) throws IOException {
        byte[] responseByteArray = response.getBytes(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(statusCode, responseByteArray.length);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(responseByteArray);
        }
    }


}

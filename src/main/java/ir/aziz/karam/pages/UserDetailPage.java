/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.pages;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public class UserDetailPage implements IPage {

    private final String pageNumber;

    public UserDetailPage(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        String response = "we are good as user" + pageNumber;
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.pages;

import com.sun.net.httpserver.HttpExchange;
import ir.aziz.karam.exception.UserNotFoundException;
import ir.aziz.karam.manager.UserManager;
import ir.aziz.karam.types.User;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDetailPage implements IPage {

    private final String id;

    public UserDetailPage(String id) {
        this.id = id;
    }

    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        try {
            User userById = UserManager.getInstance().getUserById(id);
            String response = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <title>User</title>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "    <ul>\n"
                    + "        <li>id: " + userById.getId() + "</li>\n"
                    + "        <li>first name: " + userById.getFirstName() + "</li>\n"
                    + "        <li>last name: " + userById.getLastName() + "</li>\n"
                    + "        <li>jobTitle: " + userById.getJobTitle() + "</li>\n"
                    + "        <li>bio: " + userById.getBio() + "</li>\n"
                    + "    </ul>\n"
                    + "</body>\n"
                    + "</html>";
            httpExchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (UserNotFoundException ex) {
            org.apache.log4j.Logger.getLogger(UserDetailPage.class).error(ex, ex);
            String response = "user not for this id!";
            httpExchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
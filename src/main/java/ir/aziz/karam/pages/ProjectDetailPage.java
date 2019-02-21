/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.pages;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ir.aziz.karam.exception.SkillNotFoundException;
import ir.aziz.karam.exception.UserNotFoundException;
import ir.aziz.karam.manager.ProjectManager;
import ir.aziz.karam.manager.UserManager;
import ir.aziz.karam.types.Project;
import ir.aziz.karam.types.User;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;

public class ProjectDetailPage implements IPage {

    private final String pageNumber;

    public ProjectDetailPage(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        try {
            Project projectById = ProjectManager.getInstance().getProjectById(pageNumber); // 404 maybe happend 
            User currentUser = UserManager.getInstance().getCurrentUser();
            ProjectManager.getInstance().userCanSolveProject(currentUser, projectById);
            Gson gson = new Gson();
//            String response = "sla";
            String response = gson.toJson(projectById);
            httpExchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (UserNotFoundException ex) { // 404
            Logger.getLogger(UserDetailPage.class).error(ex, ex);
            String response = "this project not available!!";
            httpExchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (SkillNotFoundException ex) { // 403
            Logger.getLogger(UserDetailPage.class).error(ex, ex);
            String response = "user can not access to this project!!";
            httpExchange.sendResponseHeaders(403, response.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}

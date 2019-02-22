/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.pages;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ir.aziz.karam.exception.ProjectNotFoundException;
import ir.aziz.karam.exception.SkillNotFoundException;
import ir.aziz.karam.exception.UserNotFoundException;
import ir.aziz.karam.manager.ProjectManager;
import ir.aziz.karam.manager.SkillPointIsNotEnoghException;
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
            String response = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <title>Project</title>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "    <ul>\n"
                    + "        <li>id: " + projectById.getId() + "</li>\n"
                    + "        <li>title: " + projectById.getTitle() + "</li>\n"
                    + "        <li>description:  " + projectById.getDescrption() + "</li>\n"
                    + "        <li>imageUrl: <img src=\" " + projectById.getImageURL() + "\" style=\"width: 50px; height: 50px;\"></li>\n"
                    + "        <li>budget: " + projectById.getBudget() + "</li>\n"
                    + "    </ul>\n"
                    + "</body>\n"
                    + "</html>";
            httpExchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (ProjectNotFoundException ex) { // 404
            Logger.getLogger(UserDetailPage.class).error(ex, ex);
            String response = "this project not available!!";
            httpExchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (SkillNotFoundException | SkillPointIsNotEnoghException ex) { // 403
            Logger.getLogger(UserDetailPage.class).error(ex, ex);
            String response = ex.getMessage();
            httpExchange.sendResponseHeaders(403, response.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}

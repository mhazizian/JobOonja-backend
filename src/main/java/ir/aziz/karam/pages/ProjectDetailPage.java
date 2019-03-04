/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.pages;

import com.sun.net.httpserver.HttpExchange;
import ir.aziz.karam.model.exception.ProjectNotFoundException;
import ir.aziz.karam.model.exception.SkillNotFoundException;
import ir.aziz.karam.model.manager.ProjectManager;
import ir.aziz.karam.model.exception.SkillPointIsNotEnoghException;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.Project;
import ir.aziz.karam.model.types.User;
import java.io.IOException;

import org.apache.log4j.Logger;

public class ProjectDetailPage extends APage implements IPage {

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
            this.sendPage(httpExchange, response, 200);
        } catch (ProjectNotFoundException ex) { // 404
            Logger.getLogger(UserDetailPage.class).error(ex, ex);
            NotFound404Page notFound404Page = new NotFound404Page("this project not available!!");
            notFound404Page.HandleRequest(httpExchange);
        } catch (SkillNotFoundException | SkillPointIsNotEnoghException ex) { // 403
            Logger.getLogger(UserDetailPage.class).error(ex, ex);
            PermissionDenied403Page permissionDenied403Page = new PermissionDenied403Page(ex.getMessage());
            permissionDenied403Page.HandleRequest(httpExchange);
        }
    }
}

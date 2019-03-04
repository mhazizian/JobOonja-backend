/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.controller;

import ir.aziz.karam.model.exception.ProjectNotFoundException;
import ir.aziz.karam.model.exception.SkillNotFoundException;
import ir.aziz.karam.model.exception.SkillPointIsNotEnoghException;
import ir.aziz.karam.model.manager.ProjectManager;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.Project;
import ir.aziz.karam.model.types.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet("/project/*")
public class ProjectServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String[] parts = request.getRequestURL().toString().split("/");

        if (parts.length == 5 || parts.length == 6 && parts[5].equals("")) {
            User currentUser = UserManager.getInstance().getCurrentUser();
            List<Project> allProject = ProjectManager.getInstance().getAllProjectsFeasibleByUser(currentUser);
            request.setAttribute("allProject", allProject);
            request.getRequestDispatcher("/projects.jsp").forward(request, response);
        } else {
            try {
                String projctId = parts[5];
                Project projectById = ProjectManager.getInstance().getProjectById(projctId); // 404 maybe happend 
                User currentUser = UserManager.getInstance().getCurrentUser();
                ProjectManager.getInstance().userCanSolveProject(currentUser, projectById);
                request.setAttribute("project", projectById);
                request.setAttribute("hasBided", projectById.hasBided(currentUser));
                request.getRequestDispatcher("/projectById.jsp").forward(request, response);
            } catch (ProjectNotFoundException ex) {
                Logger.getLogger(ProjectServlet.class).error(ex, ex);
            } catch (SkillNotFoundException ex) {
                Logger.getLogger(ProjectServlet.class).error(ex, ex);
            } catch (SkillPointIsNotEnoghException ex) {
                Logger.getLogger(ProjectServlet.class).error(ex, ex);
            }
        }
    }
}
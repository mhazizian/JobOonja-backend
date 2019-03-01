/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.servlets;

import ir.aziz.karam.exception.ProjectNotFoundException;
import ir.aziz.karam.manager.ProjectManager;
import ir.aziz.karam.types.Project;
import java.io.IOException;
import java.util.ArrayList;
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
        try {
            String projctId = request.getRequestURL().toString().split("/")[5];
            Project projectById = ProjectManager.getInstance().getProjectById(projctId);
            request.setAttribute("project", projectById);
            request.getRequestDispatcher("/projectById.jsp").forward(request, response);
        } catch (ProjectNotFoundException ex) {
            Logger.getLogger(ProjectServlet.class).error(ex, ex);
        }
    }
}

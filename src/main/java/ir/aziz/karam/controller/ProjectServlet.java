/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.controller;

import com.google.gson.Gson;
import ir.aziz.karam.model.exception.ProjectNotFoundException;
import ir.aziz.karam.model.exception.SkillNotFoundException;
import ir.aziz.karam.model.exception.SkillPointIsNotEnoghException;
import ir.aziz.karam.model.manager.ProjectManager;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.Project;
import ir.aziz.karam.model.types.ProjectDetails;
import ir.aziz.karam.model.types.ResponsePostMessage;
import ir.aziz.karam.model.types.User;
import ir.aziz.karam.model.types.projectsDetails;

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
        Gson gson = new Gson();
        request.setCharacterEncoding("UTF-8");
        String[] parts = request.getRequestURL().toString().split("/");
        if (parts.length == 5 || parts.length == 6 && parts[5].equals("")) {
            User currentUser = UserManager.getInstance().getCurrentUser();
            List<Project> allProject = ProjectManager.getInstance().getAllProjectsFeasibleByUser(currentUser);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            String currentID = UserManager.getInstance().getCurrentUser().getId();
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(202, gson.toJson(allProject), gson.toJson(new projectsDetails(currentID)));
            response.getWriter().write(gson.toJson(responsePostMessage));
        } else {
            try {
                String projectId = parts[5];
                Project projectById = ProjectManager.getInstance().getProjectById(projectId); // 404 maybe happend
                User currentUser = UserManager.getInstance().getCurrentUser();
                ProjectManager.getInstance().userCanSolveProject(currentUser, projectById);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                ResponsePostMessage responsePostMessage = new ResponsePostMessage(202, gson.toJson(projectById), gson.toJson(new ProjectDetails(projectById.hasBided(currentUser), currentUser.getId())));
                response.getWriter().write(gson.toJson(responsePostMessage));
            } catch (ProjectNotFoundException ex) {
                Logger.getLogger(this.getClass()).error(ex, ex);
                response.setStatus(404);
                ResponsePostMessage responsePostMessage = new ResponsePostMessage(404, "پروژه با این مشخصات یافت نشد.", null);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(gson.toJson(responsePostMessage));
            } catch (SkillPointIsNotEnoghException ex) {
                Logger.getLogger(this.getClass()).error(ex, ex);
                response.setStatus(400);
                ResponsePostMessage responsePostMessage = new ResponsePostMessage(400, "عدم مهارت کافی", null);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson(responsePostMessage));
            } catch (SkillNotFoundException ex) {
                Logger.getLogger(this.getClass()).error(ex, ex);
                response.setStatus(404);
                ResponsePostMessage responsePostMessage = new ResponsePostMessage(404, "مهارت با این مشخصات یافت نشد.", null);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(gson.toJson(responsePostMessage));
            } catch (Exception ex) {
                Logger.getLogger(this.getClass()).error(ex, ex);
                response.setStatus(400);
                ResponsePostMessage responsePostMessage = new ResponsePostMessage(400, "خطا در فراخوانی عملیات", null);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson(responsePostMessage));
            }
        }
    }
}

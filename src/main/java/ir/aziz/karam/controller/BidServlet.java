package ir.aziz.karam.controller;

import ir.aziz.karam.model.exception.ProjectNotFoundException;
import ir.aziz.karam.model.exception.SkillNotFoundException;
import ir.aziz.karam.model.exception.SkillPointIsNotEnoghException;
import ir.aziz.karam.model.manager.ProjectManager;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.Bid;
import ir.aziz.karam.model.types.Project;
import ir.aziz.karam.model.types.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bid")
public class BidServlet extends HttpServlet {
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String amount = request.getParameter("bidAmount");
        String projectId = request.getParameter("projectId");

        try {
            Project projectById = ProjectManager.getInstance().getProjectById(projectId); // 404 maybe happend
            User currentUser = UserManager.getInstance().getCurrentUser();
            ProjectManager.getInstance().userCanSolveProject(currentUser, projectById);
            if (!projectById.hasBided(currentUser)) {
                projectById.addBid(new Bid(currentUser.getId(), projectById.getId(), Integer.parseInt(amount)));
                request.setAttribute("message", "bid added.");
                request.getRequestDispatcher("/success.jsp").forward(request, response);

            } else {
                response.setStatus(403);
                request.setAttribute("message", "bid has already been submitted");
                request.getRequestDispatcher("/permission-denied403.jsp").forward(request, response);
            }


        } catch (ProjectNotFoundException ex) {
            Logger.getLogger(ProjectServlet.class).error(ex, ex);
            response.setStatus(404);
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/not-found404.jsp").forward(request, response);
        } catch (SkillPointIsNotEnoghException | SkillNotFoundException ex) {
            Logger.getLogger(ProjectServlet.class).error(ex, ex);
            response.setStatus(403);
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/permission-denied403.jsp").forward(request, response);
        }
    }
}

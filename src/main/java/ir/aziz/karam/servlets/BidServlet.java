package ir.aziz.karam.servlets;

import ir.aziz.karam.exception.ProjectNotFoundException;
import ir.aziz.karam.exception.SkillNotFoundException;
import ir.aziz.karam.exception.SkillPointIsNotEnoghException;
import ir.aziz.karam.manager.ProjectManager;
import ir.aziz.karam.manager.UserManager;
import ir.aziz.karam.types.Bid;
import ir.aziz.karam.types.Project;
import ir.aziz.karam.types.User;
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
                System.out.println("bid added");
            }
            System.out.println("already bided");

        } catch (ProjectNotFoundException ex) {
            Logger.getLogger(ProjectServlet.class).error(ex, ex);
        } catch (SkillNotFoundException ex) {
            Logger.getLogger(ProjectServlet.class).error(ex, ex);
        } catch (SkillPointIsNotEnoghException ex) {
            Logger.getLogger(ProjectServlet.class).error(ex, ex);
        }

    }
}

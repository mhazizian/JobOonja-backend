package ir.aziz.karam.controller;

import com.google.gson.Gson;
import ir.aziz.karam.model.exception.ProjectNotFoundException;
import ir.aziz.karam.model.exception.SkillNotFoundException;
import ir.aziz.karam.model.exception.SkillPointIsNotEnoghException;
import ir.aziz.karam.model.manager.ProjectManager;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.Bid;
import ir.aziz.karam.model.types.Project;
import ir.aziz.karam.model.types.ResponsePostMessage;
import ir.aziz.karam.model.types.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/bid")
public class BidServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        Gson gson = new Gson();
        String amount = request.getParameter("bidAmount");
        String projectId = request.getParameter("projectId");
        try {
            Project projectById = ProjectManager.getInstance().getProjectById(projectId);
            String currentUserId = (String) request.getAttribute("currentUserId");
            User currentUser = UserManager.getInstance().getUserById(currentUserId);
            ProjectManager.getInstance().userCanSolveProject(currentUser, projectById);

            projectById.addBid(new Bid(currentUser.getId(), projectById.getId(), Integer.parseInt(amount)));
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(202, "درخواست با موفقیت انجام شد.", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write(gson.toJson(responsePostMessage));

        } catch (SQLException ex) {
            response.setStatus(400);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(400, "این پروژه درخواست شده است.", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(responsePostMessage));
        } catch (ProjectNotFoundException ex) {
            Logger.getLogger(AddSkillUserRequestServlet.class).error(ex, ex);
            response.setStatus(404);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(404, "پروژه با این مشخصات یافت نشد.", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(gson.toJson(responsePostMessage));
        } catch (SkillPointIsNotEnoghException ex) {
            Logger.getLogger(this.getClass()).error(ex, ex);
            response.setStatus(400);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(400, "مهارت برای این پروژه کافی نیست", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(responsePostMessage));
        } catch (SkillNotFoundException ex) {
            Logger.getLogger(AddSkillUserRequestServlet.class).error(ex, ex);
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

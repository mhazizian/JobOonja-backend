/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.controller;

import ir.aziz.karam.model.exception.ReapeatSkillAddedToUserException;
import ir.aziz.karam.model.exception.UserNotFoundException;
import ir.aziz.karam.model.manager.SkillManager;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.User;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet("/addSkillUser/*")
public class AddSkillUserRequestServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String userId = parameterMap.get("user")[0];
            String skillName = parameterMap.get("skill")[0];
            User userById = UserManager.getInstance().getUserById(userId);
            try {
                UserManager.getInstance().addASkillFromAUser(userById, skillName);
            } catch (ReapeatSkillAddedToUserException ex) {
                Logger.getLogger(AddSkillUserRequestServlet.class).error(ex, ex);
            } finally {
                request.setAttribute("user", userById);
                request.setAttribute("skills", SkillManager.getInstance().getAllSkills());
                if (UserManager.getInstance().getCurrentUser().getId().equals(userId)) {
                    request.getRequestDispatcher("/user-single-logged-in.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/user-single-guest.jsp").forward(request, response);
                }
            }
        } catch (UserNotFoundException ex) {
            Logger.getLogger(AddSkillUserRequestServlet.class).error(ex, ex);
        }
    }
}

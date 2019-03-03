/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.servlets;

import ir.aziz.karam.exception.ReapeatSkillAddedToUserException;
import ir.aziz.karam.exception.SkillNotFoundException;
import ir.aziz.karam.exception.UserNotFoundException;
import ir.aziz.karam.manager.SkillManager;
import ir.aziz.karam.manager.UserManager;
import ir.aziz.karam.types.Endorse;
import ir.aziz.karam.types.Skill;
import ir.aziz.karam.types.User;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet("/addEndorse/*")
public class AddEndorseRequestServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String userId = parameterMap.get("userId")[0];
            String currentUserId = parameterMap.get("currentUserId")[0];
            String skillName = parameterMap.get("skillName")[0];
            User user = UserManager.getInstance().getUserById(userId);
            User currentUser = UserManager.getInstance().getUserById(currentUserId);
            currentUser.addEndorses(new Endorse(userId, skillName));
            Skill skillOfUserBySkillName = UserManager.getInstance().getSkillOfUserBySkillName(user, skillName);
            skillOfUserBySkillName.setPoints(skillOfUserBySkillName.getPoints() + 1);
            request.setAttribute("user", user);
            request.setAttribute("skills", SkillManager.getInstance().getAllSkills());
            request.setAttribute("currenUser", UserManager.getInstance().getCurrentUser());
            if (UserManager.getInstance().getCurrentUser().getId().equals(userId)) {
                request.getRequestDispatcher("/user-single-logged-in.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/user-single-guest.jsp").forward(request, response);
            }
        } catch (UserNotFoundException | SkillNotFoundException ex) {
            Logger.getLogger(AddSkillUserRequestServlet.class).error(ex, ex);
        }
    }
}

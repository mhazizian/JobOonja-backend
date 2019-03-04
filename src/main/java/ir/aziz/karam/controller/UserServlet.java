/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.controller;

import ir.aziz.karam.model.exception.UserNotFoundException;
import ir.aziz.karam.model.manager.SkillManager;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String[] parts = request.getRequestURL().toString().split("/");
        if (parts.length == 5) {
            List<User> allUsers = UserManager.getInstance().getAllUsers();
            request.setAttribute("allUser", allUsers);
            request.getRequestDispatcher("users.jsp").forward(request, response);
        } else {
            try {
                String userId = parts[5];
                User userById = UserManager.getInstance().getUserById(userId);
                request.setAttribute("user", userById);
                request.setAttribute("skills", SkillManager.getInstance().getAllSkills());
                request.setAttribute("currenUser", UserManager.getInstance().getCurrentUser());
                if (UserManager.getInstance().getCurrentUser().getId().equals(userId)) {
                    request.getRequestDispatcher("/user-single-logged-in.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/user-single-guest.jsp").forward(request, response);
                }
            } catch (UserNotFoundException ex) {
                Logger.getLogger(UserServlet.class).error(ex, ex);
            }
        }
    }
}

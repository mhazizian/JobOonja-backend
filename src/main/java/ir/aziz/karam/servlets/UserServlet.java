/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.servlets;

import ir.aziz.karam.exception.ProjectNotFoundException;
import ir.aziz.karam.exception.SkillNotFoundException;
import ir.aziz.karam.exception.SkillPointIsNotEnoghException;
import ir.aziz.karam.exception.UserNotFoundException;
import ir.aziz.karam.manager.ProjectManager;
import ir.aziz.karam.manager.UserManager;
import ir.aziz.karam.types.Project;
import ir.aziz.karam.types.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
            request.getRequestDispatcher("user.jsp").forward(request, response);
        } else {
            try {
                String userId = parts[5];
                User userById = UserManager.getInstance().getUserById(userId);
                request.setAttribute("user", userById);
                request.getRequestDispatcher("/projectById.jsp").forward(request, response);
            } catch (UserNotFoundException ex) {
                java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

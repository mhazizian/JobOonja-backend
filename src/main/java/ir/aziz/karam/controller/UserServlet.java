/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.controller;

import com.google.gson.Gson;
import ir.aziz.karam.model.exception.UserNotFoundException;
import ir.aziz.karam.model.manager.SkillManager;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.ResponsePostMessage;
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
        Gson gson = new Gson();
        String[] parts = request.getRequestURL().toString().split("/");
        if (parts.length == 5) {
            List<User> allUsers = UserManager.getInstance().getAllUsers();
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(allUsers));
        } else {
            try {
                String userId = parts[5];
                User userById = UserManager.getInstance().getUserById(userId);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(userById));
            } catch (UserNotFoundException ex) {
                Logger.getLogger(this.getClass()).error(ex, ex);
                response.setStatus(404);
                ResponsePostMessage responsePostMessage = new ResponsePostMessage(404, "کاربری با این مشخصات یافت نشد.");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(gson.toJson(responsePostMessage));
            } catch (Exception ex) {
                Logger.getLogger(this.getClass()).error(ex, ex);
                response.setStatus(400);
                ResponsePostMessage responsePostMessage = new ResponsePostMessage(400, "خطا در فراخوانی عملیات");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson(responsePostMessage));
            }
        }
    }
}

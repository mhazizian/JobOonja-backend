/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.controller;

import com.google.gson.Gson;
import ir.aziz.karam.model.dataLayer.dataMappers.user.UserMapper;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.ResponsePostMessage;
import ir.aziz.karam.model.types.User;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet("/addUserRequestServlet")
public class AddUserRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String name = parameterMap.get("name")[0];
            String familyName = parameterMap.get("familyName")[0];
            String username = parameterMap.get("username")[0];
            String password = parameterMap.get("pass")[0];
            password = UserManager.getInstance().convertPasswordToHash(password);
            String jobTitle = parameterMap.get("jobTitle")[0];
            String profileLink = parameterMap.get("profileLink")[0];
            String bio = parameterMap.get("bio")[0];
            Random id = new Random(45000000);
            String idString = new Integer(id.nextInt()).toString();
            User element = new User(idString, name, familyName, jobTitle, profileLink, bio, username, password);
            UserMapper.getInstance().insert(element);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(202, "درخواست با موفقیت انجام شد.", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write(gson.toJson(responsePostMessage));
        } catch (SQLException ex) {
            Logger.getLogger(AddUserRequestServlet.class).error(ex, ex);
            response.setStatus(400);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(400, "این کاربر ساخته شده است.", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import ir.aziz.karam.model.dataLayer.dataMappers.user.UserMapper;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.ResponsePostMessage;
import ir.aziz.karam.model.types.User;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet("/loginUserRequestServlet")
public class LoginUserRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String username = parameterMap.get("username")[0];
            String password = parameterMap.get("password")[0];
            User user = UserMapper.getInstance().getUserByUsernameAndPassword(username, UserManager.getInstance().convertPasswordToHash(password));
            String token = UserManager.getInstance().createJWTToken(user.getId());
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(202, "درخواست با موفقیت انجام شد.", token);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write(gson.toJson(responsePostMessage));
        } catch (JWTCreationException ex) {
            Logger.getLogger(this.getClass()).error(ex, ex);
            response.setStatus(400);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(400, "خطا در فراخوانی عملیات", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(responsePostMessage));
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass()).error(ex, ex);
            response.setStatus(403);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(403, "نام کاربری یا رمز عبور اشتباه است", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
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

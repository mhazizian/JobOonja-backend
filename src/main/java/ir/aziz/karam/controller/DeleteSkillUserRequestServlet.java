/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.controller;

import com.google.gson.Gson;
import ir.aziz.karam.model.exception.UserNotFoundException;
import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.ResponsePostMessage;
import ir.aziz.karam.model.types.User;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet("/deleteSkillUser/*")
public class DeleteSkillUserRequestServlet extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            int currentUserId = Integer.valueOf((String) request.getAttribute("currentUserId"));
            User currentUser = UserManager.getInstance().getUserById(currentUserId);
            String skillName = parameterMap.get("skill")[0];
            UserManager.getInstance().deleteASkillFromAUser(currentUser, skillName);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(202, "درخواست با موفقیت انجام شد.", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write(gson.toJson(responsePostMessage));
        } catch (UserNotFoundException ex) {
            Logger.getLogger(AddSkillUserRequestServlet.class).error(ex, ex);
            response.setStatus(404);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(404, "کاربری با این مشخصات یافت نشد.", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(gson.toJson(responsePostMessage));
        } catch (Exception ex) {
            Logger.getLogger(AddSkillUserRequestServlet.class).error(ex, ex);
            response.setStatus(400);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(400, "خطا در فراخوانی عملیات", null);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(responsePostMessage));
        }
    }

}

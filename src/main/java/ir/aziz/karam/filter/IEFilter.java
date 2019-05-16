/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import ir.aziz.karam.model.exception.NotFoundAnyHeadersFromRequestException;
import ir.aziz.karam.model.exception.NotSetJwtHeaderException;
import ir.aziz.karam.model.types.ResponsePostMessage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebFilter("/*")
public class IEFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        Gson gson = new Gson();

        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        String jwtHeader = null;
        try {
            if (!path.startsWith("/addUserRequestServlet") && !path.startsWith("/loginUserRequestServlet")) {
                Enumeration<String> headerNames = httpRequest.getHeaderNames();
                if (headerNames == null) {
                    throw new NotFoundAnyHeadersFromRequestException();
                }
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    if (headerName.equalsIgnoreCase("jwtHeader")) {
                        jwtHeader = httpRequest.getHeader(headerName);
                    }
                }
                if (jwtHeader == null || jwtHeader.equals("null")) {
                    throw new NotSetJwtHeaderException();
                }
                Algorithm algorithm = Algorithm.HMAC256("joboonja");
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("joboonja.com")
                        .build();
                DecodedJWT jwt = verifier.verify(jwtHeader);
                String userId = jwt.getClaim("userId").asString();
                request.setAttribute("currentUserId", userId);
            }
            long st = System.currentTimeMillis();
            chain.doFilter(request, resp);
            long et = System.currentTimeMillis();
            long tt = (et - st);
            Logger.getLogger(IEFilter.class).info("Time take to execute action " + path + "   is  :  " + tt);
        } catch (NotSetJwtHeaderException ex) {
            Logger.getLogger(IEFilter.class).error(ex, ex);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(401, "شما در سامانه لاگین نکرده اید!", null);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(responsePostMessage));
        } catch (NotFoundAnyHeadersFromRequestException ex) {
            Logger.getLogger(IEFilter.class).error(ex, ex);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(400, "حطا در فراخوانی عملیات", null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(responsePostMessage));
        } catch (JWTVerificationException ex) {
            Logger.getLogger(IEFilter.class).error(ex, ex);
            ResponsePostMessage responsePostMessage = new ResponsePostMessage(403, "شما دسترسی لازم را ندارید.", null);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(responsePostMessage));
        }
    }

    @Override
    public void destroy() {
    }

}

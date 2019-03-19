/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

@WebFilter("/*")
public class IEFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getServletPath();
        long st = System.currentTimeMillis();
        chain.doFilter(request, response);
        long et = System.currentTimeMillis();
        long tt = (et - st);
        Logger.getLogger(IEFilter.class).info("Time take to execute action " + path + "   is  :  " + tt);
    }

    @Override
    public void destroy() {
    }

}

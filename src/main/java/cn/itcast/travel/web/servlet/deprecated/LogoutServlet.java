package cn.itcast.travel.web.servlet.deprecated;

import cn.itcast.travel.domain.ResultInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        ResultInfo info = new ResultInfo();
        Cookie uid = new Cookie("uid", "");
        Cookie value = new Cookie("value","");

        uid.setMaxAge(0);
        value.setMaxAge(0);

        request.getSession().removeAttribute("username");

        response.addCookie(uid);
        response.addCookie(value);
        info.setFlag(true);
        response.getWriter().write(new ObjectMapper().writeValueAsString(info));
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        response.sendRedirect(request.getContextPath() + "/error/404.html");
    }
}

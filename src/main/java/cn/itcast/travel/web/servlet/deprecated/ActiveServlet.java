package cn.itcast.travel.web.servlet.deprecated;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//@WebServlet("/active")
public class ActiveServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        response.sendRedirect(request.getContextPath() + "/error/404.html");
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        Map<String, String[]> parameterMap = request.getParameterMap();
        String code = parameterMap.get("code") == null ? null : parameterMap.get("code")[0];
        if(code == null){
            response.sendRedirect(request.getContextPath() + "/error/500.html");
        }

        boolean flag = userService.activeUser(code);
        if(flag){
            response.sendRedirect(request.getContextPath() + "/active_success.html");
        }else{
            response.sendRedirect(request.getContextPath() + "/active_failed.html");
        }
    }
}

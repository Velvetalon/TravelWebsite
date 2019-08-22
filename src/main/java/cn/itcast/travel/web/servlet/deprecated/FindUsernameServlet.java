package cn.itcast.travel.web.servlet.deprecated;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/findUsernameServlet")
public class FindUsernameServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException{
        resp.sendRedirect(req.getContextPath() + "/error/404.html");
    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException{
        ResultInfo info = new ResultInfo();
        //如果session里有缓存则直接返回缓存里的username，降低对数据库访问频率
        //如果没有缓存则表示当前用户为第一次登录，则校验cookie
        if (req.getSession().getAttribute("username") != null) {
            info.setFlag(true);
            info.setData(req.getSession().getAttribute("username"));
            resp.getWriter().write(new ObjectMapper().writeValueAsString(info));
            return;
        }

        Cookie[] cookies = req.getCookies();
        Cookie uid = null;
        Cookie value = null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("uid")) {
                uid = cookie;
            } else if (cookie.getName().equals("value")) {
                value = cookie;
            }
        }

        if (uid == null || value == null) {
            info.setFlag(false);
        } else {
            User user = userService.decodeCookie(uid.getValue(), value.getValue());
            if (user == null) {
                info.setFlag(false);
                info.setErrorMsg("登录失效");
            } else {
                info.setFlag(true);
                info.setData(user.getUsername());
                //查询到数据后将username保存在session中作为缓存
                req.getSession().setAttribute("username", user.getUsername());
            }
        }
        resp.getWriter().write(new ObjectMapper().writeValueAsString(info));
    }
}

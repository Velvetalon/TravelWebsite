package cn.itcast.travel.web.servlet.deprecated;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.AESUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        Map<String, String[]> map = request.getParameterMap();
        ResultInfo info = new ResultInfo();

        //校验验证码
        String CHECKCODE_SERVER = request.getSession().getAttribute("CHECKCODE_SERVER") == null ? null : request.getSession().getAttribute("CHECKCODE_SERVER").toString().toLowerCase();
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        String check = map.get("check") == null ? null : map.get("check")[0].toLowerCase();

        if (!Objects.equals(check, CHECKCODE_SERVER)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            response.getWriter().write(new ObjectMapper().writeValueAsString(info));
            return;
        }

        User user = new User();

        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        User login_user = userService.login(user);

        if (login_user == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        } else if ("N".equals(login_user.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请<a herf=http://localhost" + request.getContextPath() + "/active?code=" + login_user.getCode() + ">激活</a>");
        } else {
            info.setFlag(true);
            info.setErrorMsg("登陆成功！欢迎您 " + login_user.getUsername());
            Cookie uid_cookie = new Cookie("uid", String.valueOf(login_user.getUid()));
            Cookie value_cookie = new Cookie("value", userService.encodeCookie(login_user));
            if ("on".equals(request.getParameter("auto_login"))){
                //设置一周内自动登录
                uid_cookie.setMaxAge(60 * 60 * 24 * 7);
                value_cookie.setMaxAge(60 * 60 * 24 * 7);
            }
            response.addCookie(uid_cookie);
            response.addCookie(value_cookie);
        }
        response.getWriter().write(new ObjectMapper().writeValueAsString(info));
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        response.sendRedirect(request.getContextPath() + "/error/404.html");
    }

}
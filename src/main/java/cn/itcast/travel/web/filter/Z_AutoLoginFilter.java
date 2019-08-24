package cn.itcast.travel.web.filter;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class Z_AutoLoginFilter implements Filter {
    private UserService userService = new UserServiceImpl();

    @Override
    public void init( FilterConfig filterConfig ) throws ServletException{

    }

    @Override
    public void doFilter( ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain ) throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Object login_status = request.getSession().getAttribute("login_status");
        if (login_status == null || !((boolean) login_status)) {
            //未登录，检测cookie
            Cookie[] cookies = request.getCookies();
            Cookie uid = null;
            Cookie value = null;

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("uid")) {
                    uid = cookie;
                } else if (cookie.getName().equals("value")) {
                    value = cookie;
                }
            }
            if (uid != null && value != null) {
                //有cookie且未登录，执行自动登录
                User user = userService.decodeCookie(uid.getValue(), value.getValue());
                if (user != null) {
                    //登录成功，设置session缓存。
                    System.out.println("登录成功");
                    request.getSession().setAttribute("username", user.getUsername());
                    request.getSession().setAttribute("login_status", true);
                    request.getSession().setAttribute("uid", user.getUid());
                }
            } else {
                //无cookie。删除session缓存的数据。
                request.getSession().removeAttribute("username");
                request.getSession().removeAttribute("login_status");
                request.getSession().removeAttribute("uid");
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy(){

    }
}

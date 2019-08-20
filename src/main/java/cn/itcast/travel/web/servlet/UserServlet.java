package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.AESUtils;
import cn.itcast.travel.util.UuidUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    /**
     * 用户登录
     * @param request
     * @param response
     * @throws IOException
     */
    public void login( HttpServletRequest request, HttpServletResponse response ) throws IOException{
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
            info.setErrorMsg("您尚未激活，请<a herf=http://localhost" + request.getContextPath() + "/user/active?code=" + login_user.getCode() + ">激活</a>");
        } else {
            info.setFlag(true);
            info.setErrorMsg("登陆成功！欢迎您 " + login_user.getUsername());
            Cookie uid_cookie = new Cookie("uid", String.valueOf(login_user.getUid()));
            Cookie value_cookie = new Cookie("value", userService.encodeCookie(login_user));
            if ("on".equals(request.getParameter("auto_login"))) {
                //设置一周内自动登录
                uid_cookie.setMaxAge(60 * 60 * 24 * 7);
                value_cookie.setMaxAge(60 * 60 * 24 * 7);
            }
            response.addCookie(uid_cookie);
            response.addCookie(value_cookie);
        }
        response.getWriter().write(new ObjectMapper().writeValueAsString(info));
    }

    public void logout( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        ResultInfo info = new ResultInfo();
        Cookie uid = new Cookie("uid", "");
        Cookie value = new Cookie("value", "");

        uid.setMaxAge(0);
        value.setMaxAge(0);

        request.getSession().removeAttribute("username");

        response.addCookie(uid);
        response.addCookie(value);
        info.setFlag(true);
        response.getWriter().write(new ObjectMapper().writeValueAsString(info));
    }

    /**
     * 用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        //转换参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        ResultInfo info = new ResultInfo();

        //针对用户名去重
        if ("checkRepeat".equals(parameterMap.get("action")[0])) {
            if (userService.checkRepeat(user)) {
                info.setFlag(false);
                info.setErrorMsg("用户已存在");
            } else {
                info.setFlag(true);
                info.setErrorMsg("用户不存在");
            }
            response.getWriter().write(new ObjectMapper().writeValueAsString(info));
            return;
        }

        //校验验证码
        String CHECKCODE_SERVER = request.getSession().getAttribute("CHECKCODE_SERVER") == null ? null : request.getSession().getAttribute("CHECKCODE_SERVER").toString().toLowerCase();
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        String check = parameterMap.get("check") == null ? null : parameterMap.get("check")[0].toLowerCase();
        if (!Objects.equals(check, CHECKCODE_SERVER)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            response.getWriter().write(new ObjectMapper().writeValueAsString(info));
            return;
        }

        //设置激活状态和激活码
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        user.setKey(AESUtils.getRandomKey());

        //注册用户
        boolean flag = new UserServiceImpl().register(user);

        if (flag) {
            //发送激活邮件
            userService.sendActivateMail(user, request.getContextPath());

            info.setErrorMsg("");
            info.setFlag(true);
            info.setData("");
            response.getWriter().write(new ObjectMapper().writeValueAsString(info));
        } else {
            info.setErrorMsg("注册失败，用户名重复");
            info.setFlag(false);
            info.setData("");
        }
    }

    /**
     * 查找用户名
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findUsername( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        ResultInfo info = new ResultInfo();
        //如果session里有缓存则直接返回缓存里的username，降低对数据库访问频率
        if (request.getSession().getAttribute("username") != null) {
            info.setFlag(true);
            info.setData(request.getSession().getAttribute("username"));
            response.getWriter().write(new ObjectMapper().writeValueAsString(info));
            return;
        }

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

        if (uid == null || value == null) {
            info.setFlag(false);
        } else {
            User user = userService.decodeCookie(uid.getValue(), value.getValue());
            if (user == null) {
                info.setFlag(false);
            } else {
                info.setFlag(true);
                info.setData(user.getUsername());
                //查询到数据后将username保存在session中作为缓存
                request.getSession().setAttribute("username", user.getUsername());
            }
        }
        response.getWriter().write(new ObjectMapper().writeValueAsString(info));
    }

    /**
     * 激活用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        Map<String, String[]> parameterMap = request.getParameterMap();
        String code = parameterMap.get("code") == null ? null : parameterMap.get("code")[0];
        if (code == null) {
            response.sendRedirect(request.getContextPath() + "/error/500.html");
        }

        boolean flag = userService.activeUser(code);
        if (flag) {
            response.sendRedirect(request.getContextPath() + "/active_success.html");
        } else {
            response.sendRedirect(request.getContextPath() + "/active_failed.html");
        }
    }
}

package cn.itcast.travel.web.servlet.deprecated;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.AESUtils;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
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
            userService.sendActivateMail(user,request.getContextPath());

            info.setErrorMsg("");
            info.setFlag(true);
            info.setData("");
            response.getWriter().write(new ObjectMapper().writeValueAsString(info));
        }else{
            info.setErrorMsg("注册失败，用户名重复");
            info.setFlag(false);
            info.setData("");
        }
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        response.sendRedirect(request.getContextPath() + "/error/404.html");
    }
}

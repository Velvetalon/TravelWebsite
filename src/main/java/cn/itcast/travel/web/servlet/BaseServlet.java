package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        String uri = request.getRequestURI();
        String[] split = uri.split("/");
        String method_name = split[split.length - 1];

        Method method = null;
        try {
            method = this.getClass().getMethod(method_name, HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            //方法不存在时跳转404页面
            response.sendRedirect(request.getContextPath() + "/error/404.html");
            e.printStackTrace();
        }

        try {
            method.invoke(this, request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            //方法执行错误时跳转500页面
            response.sendRedirect(request.getContextPath() + "/error/500.html");
            e.printStackTrace();
        }
    }

    public void writeValue( Object obj, HttpServletResponse response ) throws IOException{
        response.getWriter().write(new ObjectMapper().writeValueAsString(obj));
    }

    public String writeValueAsString( Object obj ) throws IOException{
        return new ObjectMapper().writeValueAsString(obj);
    }
}

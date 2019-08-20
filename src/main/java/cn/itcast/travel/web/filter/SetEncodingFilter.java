package cn.itcast.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SetEncodingFilter implements Filter {
    public void destroy(){
    }

    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws ServletException, IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");

        chain.doFilter(req, resp);
    }

    public void init( FilterConfig config ) throws ServletException{

    }
}

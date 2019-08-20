package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet{
    private RouteService routeService = new RouteServiceImpl();

    private String DEFULT_PAGEROWS = "10";
    private String DEFULT_CURRENTPAGE = "1";

    public void queryPage( HttpServletRequest request, HttpServletResponse response ) throws IOException{
        ResultInfo info = new ResultInfo();
        String cid_p = request.getParameter("cid");
        String pageRows_p = request.getParameter("pageRows") == null ? DEFULT_PAGEROWS : request.getParameter("pageRows");
        String currentPage_p = request.getParameter("currentPage") == null ? DEFULT_CURRENTPAGE : request.getParameter("currentPage");

        int cid,pageRows,currentPage;
        try{
            if(cid_p == null){
                throw new NumberFormatException();
            }
            cid = Integer.parseInt(cid_p);
            pageRows = Integer.parseInt(pageRows_p);
            currentPage = Integer.parseInt(currentPage_p);
        }catch (NumberFormatException e){
            info.setFlag(false);
            info.setErrorMsg("参数错误");
            writeValue(info,response);
            return;
        }

        info.setFlag(true);
        info.setData(routeService.getPageBean(cid,currentPage,pageRows));
        writeValue(info,response);
    }

}

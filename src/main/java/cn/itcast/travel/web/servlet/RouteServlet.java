package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();

    private String DEFULT_PAGEROWS = "10";
    private String DEFULT_CURRENTPAGE = "1";

    public void queryPage( HttpServletRequest request, HttpServletResponse response ) throws IOException{
        ResultInfo info = new ResultInfo();
        String cid_p = request.getParameter("cid") == null ? "%" : request.getParameter("cid");
        String pageRows_p = request.getParameter("pageRows") == null ? DEFULT_PAGEROWS : request.getParameter("pageRows");
        String currentPage_p = request.getParameter("currentPage") == null ? DEFULT_CURRENTPAGE : request.getParameter("currentPage");
        String rname_p = request.getParameter("rname") == null ? "%" : request.getParameter("rname");

        System.out.println(rname_p);

        int pageRows,currentPage;
        try {
            if (cid_p == null) {
                throw new NumberFormatException();
            }
            if (!"%".equals(cid_p)) {
                Integer.parseInt(cid_p);
            }
            pageRows = Integer.parseInt(pageRows_p);
            currentPage = Integer.parseInt(currentPage_p);
        } catch (NumberFormatException e) {
            info.setFlag(false);
            info.setErrorMsg("参数错误");
            writeValue(info, response);
            return;
        }

        info.setFlag(true);
        info.setData(routeService.getPageBean(cid_p, currentPage, pageRows,rname_p));
        writeValue(info, response);
    }

    public void queryDetails(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String rid_p = request.getParameter("rid");
        ResultInfo info = new ResultInfo();

        int rid;
        try {
            if(rid_p == null){
                throw new NumberFormatException();
            }
            rid = Integer.valueOf(rid_p);

            info.setFlag(true);
            info.setData(routeService.getRoute(rid));
        } catch (NumberFormatException e) {
            info.setFlag(false);
            info.setErrorMsg("参数错误");
        }
        System.out.println(info);
        writeValue(info,response);
    }
}

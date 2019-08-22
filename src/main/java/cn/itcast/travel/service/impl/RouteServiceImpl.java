package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> getPageBean( String cid, int currentPage, int pageRows ,String rname){
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageRows(pageRows);
        if(!"%".equals(rname)){
            pageBean.setKeyword(rname);
        }
        pageBean.setTotalCount(routeDao.queryCountByCid(cid,rname));

        pageBean.setTotalPage((int)(Math.ceil(pageBean.getTotalCount()*1.0 / pageBean.getPageRows())));

        pageBean.setDataList(routeDao.queryRouteList(cid,(currentPage - 1) * pageRows,pageRows,rname));
        return pageBean;
    }
}

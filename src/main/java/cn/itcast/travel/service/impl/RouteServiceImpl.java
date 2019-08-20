package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> getPageBean( int cid, int currentPage, int pageRows ){
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageRows(pageRows);
        pageBean.setTotalCount(routeDao.queryCountByCid(cid));

        pageBean.setTotalPage((int)(Math.ceil(pageBean.getTotalCount()*1.0 / pageBean.getPageRows())));

        pageBean.setDataList(routeDao.queryRouteList(cid,(currentPage - 1) * pageRows,pageRows));

        return pageBean;
    }
}

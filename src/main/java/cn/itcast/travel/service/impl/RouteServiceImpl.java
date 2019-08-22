package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();

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

    @Override
    public Route getRoute( int rid ){
        Route route = routeDao.queryRouteByRid(rid);
        if(route == null){
            return null;
        }

        route.setRouteImgList(routeImgDao.findRouteImgByRid(route.getRid()));
        route.setSeller(sellerDao.findSellerBySid(route.getSid()));

        return route;
    }
}

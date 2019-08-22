package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    /**
     * 根据cid，currentPage, pageRows创建分页对象
     */

    public abstract PageBean<Route> getPageBean(String cid,int begin, int rows , String rname);
}
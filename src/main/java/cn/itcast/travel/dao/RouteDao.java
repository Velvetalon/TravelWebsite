package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 根据cid查询记录总条数
     */
    public abstract Integer queryCountByCid(String cid,String rname);

    /**
     * 根据cid,currentPage,pageRows查询分页数据
     */
    public abstract List<Route> queryRouteList(String cid,int begin, int rows,String rname);

}

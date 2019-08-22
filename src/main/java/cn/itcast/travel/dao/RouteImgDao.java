package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 根据rid查询图片数据
     */
    public abstract List<RouteImg> findRouteImgByRid( int rid);
}

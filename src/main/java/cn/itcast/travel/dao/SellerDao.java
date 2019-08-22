package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 根据sid查询卖家信息
     */
    public abstract Seller findSellerBySid( int sid);
}

package cn.itcast.travel.service;

public interface FavoriteService {
    /**
     * 获取给定用户对给定线路的收藏状态
     */
    public abstract boolean getFavoriteStatus(int uid,int rid);

    /**
     * 设定用户收藏状态。
     */
    public abstract void setFavoriteStatus(int uid,int rid,String status);
}

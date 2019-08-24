package cn.itcast.travel.dao;

public interface FavoriteDao {
    /**
     * 根据用户uid和线路rid查询收藏状态
     */
    public abstract boolean getFavorite(int uid,int rid);

    /**
     * 为指定用户设置收藏
     */
    public abstract void favoriteRoute(int uid, int rid);

    /**
     * 为指定用户取消收藏
     */
    public abstract void removeFavorite(int uid,int rid);
}

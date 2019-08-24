package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean getFavoriteStatus( int uid, int rid ){
        return favoriteDao.getFavorite(uid,rid);
    }

    @Override
    public void setFavoriteStatus( int uid, int rid, String status ){
        if("true".equals(status)){
            favoriteDao.favoriteRoute(uid,rid);
        }else if("false".equals(status)){
            favoriteDao.removeFavorite(uid,rid);
        }
    }
}
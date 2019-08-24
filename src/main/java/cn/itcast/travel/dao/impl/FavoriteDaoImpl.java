package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();

    @Override
    public boolean getFavorite( int uid, int rid ){
        return jdbcTemplate.queryForObject("select count(*) from tab_favorite where uid = ? and rid = ?", Integer.class, uid, rid) != 0;
    }

    @Override
    public void favoriteRoute( int uid, int rid ){
        System.out.println(uid+"  "+rid);
        jdbcTemplate.update("insert into tab_favorite VALUES(?,?,?)",rid,new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()),uid);
    }

    @Override
    public void removeFavorite( int uid, int rid ){
        jdbcTemplate.update("delete from tab_favorite where uid = ? and rid = ?",uid,rid);
    }
}

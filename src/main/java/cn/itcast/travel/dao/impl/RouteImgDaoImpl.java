package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();

    @Override
    public List<RouteImg> findRouteImgByRid( int rid ){
        return jdbcTemplate.query("select * from tab_route_img where rid = ?",new BeanPropertyRowMapper<>(RouteImg.class),rid);
    }
}

package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();

    @Override
    public Integer queryCountByCid( String cid, String rname ){
        return jdbcTemplate.queryForObject("select count(*) from tab_route where cid like ? and rname like ?", Integer.class, cid, "%"+rname+"%");
    }

    @Override
    public List<Route> queryRouteList( String cid, int begin, int rows, String rname ){
        return jdbcTemplate.query("select * from tab_route where cid like ? and rname like ? limit ? , ?", new BeanPropertyRowMapper<>(Route.class), cid, "%"+rname+"%", begin, rows);
    }

    @Override
    public Route queryRouteByRid( int rid ){
        List<Route> list = jdbcTemplate.query("SELECT * FROM tab_route WHERE rid = ?", new BeanPropertyRowMapper<>(Route.class), rid);
        if(list == null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}

package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();

    @Override
    public Seller findSellerBySid( int sid ){
        List<Seller> list = jdbcTemplate.query("select * from tab_seller where sid = ?",new BeanPropertyRowMapper<>(Seller.class),sid);

        if( list == null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}

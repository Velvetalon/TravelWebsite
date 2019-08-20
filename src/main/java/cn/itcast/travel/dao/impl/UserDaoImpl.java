package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();

    @Override
    public User findUserByUsername( User user ){
        List<User> query = jdbcTemplate.query("select * from tab_user where username = ?", new BeanPropertyRowMapper<>(User.class), user.getUsername());
        if (query.isEmpty()) {
            return null;
        }
        return query.get(0);
    }

    @Override
    public void save( User user ){
        String sql = "insert into tab_user values(NULL,?,MD5(?),?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode(),user.getKey());
    }

    @Override
    public User findUserByCode( String code ){
        List<User> query = jdbcTemplate.query("select * from tab_user where code = ?", new BeanPropertyRowMapper<>(User.class), code);
        if (query.isEmpty()) {
            return null;
        }
        return query.get(0);
    }

    @Override
    public void activeUser( User user ){
        String sql = "update tab_user set status = 'Y' where uid = ?";
        jdbcTemplate.update(sql, user.getUid());
    }

    @Override
    public User findUserByUsernamePassword( User user ){
        List<User> query = jdbcTemplate.query("select * from tab_user where username = ? and password = MD5(?)", new BeanPropertyRowMapper<>(User.class),
                                            user.getUsername(),user.getPassword());
        if (query.isEmpty()) {
            return null;
        }
        return query.get(0);
    }

    @Override
    public User findUserByUid( String uid ){
        List<User> query = jdbcTemplate.query("select * from tab_user where uid = ?", new BeanPropertyRowMapper<>(User.class), uid);
        if (query.isEmpty()) {
            return null;
        }
        return query.get(0);
    }
}

package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 按用户名查找用户
     * @param user
     * @return
     */
    public abstract User findUserByUsername(User user);

    /**
     * 保存用户信息
     * @param user
     */
    public abstract void save( User user );

    /**
     * 以激活码查找用户
     * @param code
     * @return
     */
    public abstract User findUserByCode( String code );

    /**
     * 激活用户
     */
    public abstract void activeUser(User user);

    /**
     * 根据用户名和密码查找用户(等同于登陆)
     * @param user
     * @return
     */
    public abstract User findUserByUsernamePassword( User user );

    public abstract User findUserByUid(String uid);
}

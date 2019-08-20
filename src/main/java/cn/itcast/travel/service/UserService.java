package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 注册用户。
     * @param user 注册用户的信息。
     * @return 注册状态（成功或失败）
     */
    public abstract boolean register(User user);

    /**
     * 检测用户名是否存在
     * @param user
     * @return 已存在时返回true，否则返回false
     */
    public abstract boolean checkRepeat(User user);

    /**
     * 异步发送激活邮件。
     * @param user  用户信息。
     * @param contextPath 项目地址。
     */
    public abstract void sendActivateMail(User user,String contextPath);

    /**
     * 激活用户。
     * @param code
     * @return
     */
    public abstract boolean activeUser(String code);

    /**
     * 用户登录
     * @param user
     * @return
     */
    public abstract User login( User user );

    /**
     * 校验cookie
     */
    public abstract boolean checkUser(String uid,String cookieV);

    /**
     * 生成加密后的cookie
     */
    public abstract String encodeCookie(User user);

    /**
     * 加密/解密cookie，并根据cookie查询User
     */
    public abstract User decodeCookie( String uid, String cookieV );
}

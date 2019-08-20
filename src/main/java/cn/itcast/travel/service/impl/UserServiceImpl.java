package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.AESUtils;
import cn.itcast.travel.util.MailUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean register( User user ){
        //如果用户名不存在则注册
        if (!checkRepeat(user)) {
            userDao.save(user);
            return true;
        }
        return false;
    }

    /**
     * 检测用户名是否重复
     *
     * @param user
     * @return
     */
    @Override
    public boolean checkRepeat( User user ){
        return userDao.findUserByUsername(user) != null;
    }

    /**
     * 发送激活邮件
     *
     * @param user
     * @return
     */
    @Override
    public void sendActivateMail( User user, String contextPath ){
        new Thread(() -> {
            String url = "http://localhost" + contextPath + "/user/active?code=" + user.getCode();
            String html = "<h2 align='center'>尊敬的用户：" + user.getUsername() + " 以下是您的激活链接：</h2>" +
                    "<h3 align='center'><a href=" + url + ">激活您的账号</a></h3>" +
                    "<h5>如果无法点击，请复制到浏览器地址栏：" + url + "</h5>";
            MailUtils.sendMail(user.getEmail(), html, "账户激活");
        }).start();
    }

    @Override
    public boolean activeUser( String code ){
        User user = userDao.findUserByCode(code);
        if (user == null || user.getStatus().equals("Y")) {
            return false;
        }
        userDao.activeUser(user);
        return true;
    }

    @Override
    public User login( User user ){
        return userDao.findUserByUsernamePassword(user);
    }

    @Override
    public boolean checkUser(String uid, String cookieV ){
        return decodeCookie(uid,cookieV) != null;
    }

    @Override
    public String encodeCookie( User user ){
        Map<String,String> map = new HashMap<>();
        map.put("uid", String.valueOf(user.getUid()));
        map.put("username",user.getUsername());
        map.put("password",user.getPassword());

        try {
            return AESUtils.Encrypt(new ObjectMapper().writeValueAsString(map),user.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User decodeCookie(String uid, String cookieV ){
        User user = userDao.findUserByUid(uid);
        Map map = null;
        try {
            map = new ObjectMapper().readValue(AESUtils.Decrypt(cookieV, user.getKey()), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(map == null || map.isEmpty()){
            return null;
        }
        if (user.getUsername().equals(map.get("username")) && user.getPassword().equals(user.getPassword())){
            return user;
        }
        return null;
    }
}
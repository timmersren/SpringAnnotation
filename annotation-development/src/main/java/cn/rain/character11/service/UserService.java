package cn.rain.character11.service;

import cn.rain.character11.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/17 15:20
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void insertUser(){
        userDao.insertUser();
        System.out.println("用户插入完成...");
        // 这里通过一个异常来模拟其他操作数据库的操作抛出异常
        int i = 10/0;
    }
}

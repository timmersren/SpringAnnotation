package cn.rain.character11.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Random;
import java.util.UUID;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/17 15:14
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertUser(){
        String sql = "INSERT INTO tbl_user(username,age) VALUES(?,?)";
        String username = UUID.randomUUID().toString().substring(0, 5);
        int age = new Random().nextInt(100);
        jdbcTemplate.update(sql, username, age);
    }
}

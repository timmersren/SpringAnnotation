package cn.rain.character8.dao;

import org.springframework.stereotype.Repository;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/14 21:04
 */
@Repository
public class UserDao {
    /**
     * 为了标识两个UserDao的不同，这里设置一个token，并为其赋默认值 1。
     * 如果是通过组件扫描创建的UserDao，token为默认值1；如果是使用@Bean注解
     * 创建的UserDao，token值置为2。
     */
    private Integer token = 1;

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserDao{" +
                "token=" + token +
                '}';
    }
}

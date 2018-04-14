package cn.rain.character7.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * description: 演示使用@Value属性为bean的属性赋值
 * @author 任伟
 * @date 2018/4/14 15:46
 */
public class Person7 {
    @Value("张三")
    private String name;
    @Value("${person.nickName}")
    private String nickName;
    @Value("#{20+6}")
    private Integer age;

    public Person7() {
    }

    public Person7(String name, String nickName, Integer age) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person7{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", age=" + age +
                '}';
    }
}

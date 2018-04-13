package cn.rain.character6.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/13 14:01
 */
public class Dog {

    public Dog() {
        System.out.println("调用Dog的构造器，创建实例...");
    }

    @PostConstruct
    public void init(){
        System.out.println("Dog的初始化方法...");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Dog的销毁方法...");
    }
}

package cn.rain.character6.bean;

/**
 * description: 用于演示Bean的生命周期
 * @author 任伟
 * @date 2018/4/13 11:30
 */
public class Car {

    public Car() {
        System.out.println("调用Car的构造器，创建Car实例...");
    }

    public void init(){
        System.out.println("Car的初始化方法init()...");
    }

    public void destroy(){
        System.out.println("Car的销毁方法destroy()...");
    }
}

package cn.rain.character6.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
/**
 * description:
 * @author 任伟
 * @date 2018/4/13 13:38
 */
public class Cat implements InitializingBean, DisposableBean {

    public Cat() {
        System.out.println("调用Cat的构造器，创建Cat的实例...");
    }

    /**
     * bean的初始化方法。
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Cat实现了InitializingBean接口且重写了afterPropertiesSet方法，" +
                "因此spring在Cat初始化的时候调用该重写的afterPropertiesSet方法。");
    }
    /**
     * bean的销毁方法。
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("Cat实现了DisposableBean接口且重写了destroy方法，" +
                "因此spring在Cat初始化的时候调用该重写的destroy方法。");
    }

}

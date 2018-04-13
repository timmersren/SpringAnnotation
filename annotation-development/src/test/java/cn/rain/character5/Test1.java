package cn.rain.character5;

import cn.rain.character5.configuration.BeansConfiguration5;
import cn.rain.character5.factory.ColorFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/13 10:34
 */
public class Test1 {

    private ApplicationContext context = null;

    @Before
    public void initIoc() {
        // 通过注解形式获取IoC容器
        context = new AnnotationConfigApplicationContext(BeansConfiguration5.class);
    }

    @Test
    public void testFactoryBean() {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("=========================上面是容器中的组件==========================");
        // 获取colorFactory的bean
        Object colorFactory =  context.getBean("colorFactory");
        System.out.println("容器中colorFactory的类型是：" + colorFactory.getClass());
        System.out.println("=========================如果我们想获取工厂本身，获取bean的时候id前面加&==========================");
        Object colorFactory2 = context.getBean("&colorFactory");
        System.out.println("加了&以后colorFactory的类型是：" + colorFactory2.getClass());
    }
}

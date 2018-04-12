package cn.rain.character2;

import static org.junit.Assert.*;

import cn.rain.character1.beans.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/12 13:43
 */
public class Test1 {
    private ApplicationContext context = null;

    @Before
    public void initIoc() {
        // 通过注解形式获取IoC容器
        context = new AnnotationConfigApplicationContext(BeansConfiguration2.class);
    }

    @Test
    public void testScope() { // 测试bean的作用域（单例、多例）
        Person p1 = (Person) context.getBean("person2-1");
        Person p2 = (Person) context.getBean("person2-1");
        assertEquals(p1, p2);
    }

    @Test
    public void testLazyInit(){ // 测试懒加载
        /*
        在上边initIoc方法中，已经创建好了Ioc容器。
        1.我们如果不在Bean上使用@Lazy注解，那么单实例的person2-1的对象将会在容器启动时就创建，注掉下面的代码测试。
        2.如果我们标注了@Lazy,那么该bean的对象就不会在容器启动时创建,bean配置加上@Lazy并且注掉下面的代码测试。
        3.当我们第一次获取该bean的时候，该bean的对象才会被创建并加入到容器中，打开下面注解测试。
         */
        Person p1 = (Person) context.getBean("person2-1");
    }
}

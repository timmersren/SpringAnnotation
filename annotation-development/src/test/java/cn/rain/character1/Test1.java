package cn.rain.character1;

import static org.junit.Assert.*;

import cn.rain.character1.beans.Person;
import cn.rain.character1.configuration.BeansConfiguration;
import cn.rain.character1.component.controller.BookController;
import cn.rain.character1.component.dao.BookDao;
import cn.rain.character1.component.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/12 9:16
 */
public class Test1 {

    private ApplicationContext context = null;

    @Before
    public void initIoc() {
        // 通过注解形式获取IoC容器
        context = new AnnotationConfigApplicationContext(BeansConfiguration.class);
    }

    @Test
    public void testBean() { // 测试通过注解形式配置Bean。
        Person person = (Person) context.getBean("person002");
        System.out.println(person);
        // 根据bean的类型获取bean的id，由于多个id可以对应同一个类型，因此返回的是bean的id的String数组。
        String[] beanNames = context.getBeanNamesForType(Person.class);
        System.out.println(Arrays.toString(beanNames));
    }

    @Test
    public void testComponentScan() { // 测试注解配置形式的组件扫描
        // 查看Ioc容器中都有哪些组件
        String[] names = context.getBeanDefinitionNames();
        for (String name: names) {
            System.out.println(name);
        }
    }
}

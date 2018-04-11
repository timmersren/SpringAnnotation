package cn.rain.character1.test;

import cn.rain.character1.beans.Person;
import cn.rain.character1.configuration.BeansConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * description: 我们之前使用xml配置spring的时候，若想得到IoC容器，需要new ClasspathXMLApplicationContext("xxx.xml")。
 * 如果是使用注解的方式配置，获取容器的方式略有不同，请看下面演示。
 *
 * @author 任伟
 * @date 2018/4/11 14:50
 */
public class MainTest01 {
    public static void main(String[] args) {
        // 通过注解形式获取IoC容器
        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfiguration.class);
        Person person = (Person) context.getBean("person002");
        System.out.println(person);

        // 根据bean的类型获取bean的id，由于多个id可以对应同一个类型，因此返回的是bean的id的String数组。
        String[] beanNames = context.getBeanNamesForType(Person.class);
        System.out.println(Arrays.toString(beanNames));
    }
}

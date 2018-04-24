package cn.rain.character3;

import cn.rain.character1.beans.Person;
import cn.rain.character3.configuration.BeansConfiguration3;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/12 15:07
 */
public class Test1 {

    private ApplicationContext context = null;
    @Before
    public void initIoc() {
        // 通过注解形式获取IoC容器
        context = new AnnotationConfigApplicationContext(BeansConfiguration3.class);
    }

    @Test
    public void testConditional(){
        // 根据bean的类型获取bean的id，由于多个id可以对应同一个类型，因此返回的是bean的id的String数组。
        String[] beanNames = context.getBeanNamesForType(Person.class);
        System.out.println(Arrays.toString(beanNames));

        // 检验person的属性是不是在LinuxCondition中为person注册时设置的
        // todo 但是我在LinuxCondition注册person前为person设置了属性，但是这里显示null，此问题目前还不知道如何解决。
        Person person = (Person) context.getBean("person");
        System.out.println(person);

        System.out.println("===================================================");
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}

package cn.rain.character7;

import cn.rain.character6.configuration.BeansConfiguration6;
import cn.rain.character7.bean.Person7;
import cn.rain.character7.configuration.BeansConfiguration7;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/14 16:10
 */
public class Test1 {
    private ApplicationContext context = null;

    @Before
    public void initIoc() {
        context = new AnnotationConfigApplicationContext(BeansConfiguration7.class);
    }

    @Test
    public void testValue() {
        Person7 person7 = (Person7) context.getBean("person7");
        System.out.println(person7);

        // 验证person.properties被加载到了运行中的环境变量中了
        Environment environment = context.getEnvironment();
        String nickName = environment.getProperty("person.nickName");
        System.out.println("取出运行中环境变量的person.nickName-->" + nickName);
    }
}

package cn.rain.character8;

import cn.rain.character8.configurantion.BeansConfiguration8;
import cn.rain.character8.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        context = new AnnotationConfigApplicationContext(BeansConfiguration8.class);
    }

    @Test
    public void testAutowired() {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        UserService userService = (UserService) context.getBean("userService");
        System.out.println(userService);
    }
}

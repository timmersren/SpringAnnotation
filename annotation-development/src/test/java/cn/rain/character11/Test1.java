package cn.rain.character11;

import cn.rain.character11.configuration.BeansConfiguration11;
import cn.rain.character11.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/17 15:21
 */
public class Test1 {
    private ApplicationContext context = null;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext(BeansConfiguration11.class);
    }

    @Test
    public void testJdbcTemplate() {
        UserService userService = (UserService) context.getBean("userService");
        userService.insertUser();
    }
}

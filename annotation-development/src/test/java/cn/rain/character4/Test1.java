package cn.rain.character4;

import cn.rain.character3.configuration.BeansConfiguration3;
import cn.rain.character4.configuration.BeansConfiguration4;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/12 22:42
 */
public class Test1 {

    private ApplicationContext context = null;
    @Before
    public void initIoc() {
        // 通过注解形式获取IoC容器
        context = new AnnotationConfigApplicationContext(BeansConfiguration4.class);
    }

    @Test
    public void testImport(){
        // 获取容器中所有的组件的id
        String[] names = context.getBeanDefinitionNames();
        for (String name: names) {
            System.out.println(name);
        }
    }
}

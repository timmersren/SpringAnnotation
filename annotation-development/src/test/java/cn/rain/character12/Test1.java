package cn.rain.character12;

import cn.rain.character11.configuration.BeansConfiguration11;
import cn.rain.character12.configuration.BeansConfiguration12;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/17 17:00
 */
public class Test1 {
    private AnnotationConfigApplicationContext context = null;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext(BeansConfiguration12.class);
    }

    @Test
    public void testBeanFactoryPostProcessor(){
        // 这里以最简单的形式完成发布事件
        context.publishEvent(new ApplicationEvent(new String("任伟发布了事件！")) {
        });
        context.close();
    }
}

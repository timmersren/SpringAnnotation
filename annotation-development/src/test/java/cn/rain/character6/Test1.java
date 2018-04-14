package cn.rain.character6;

import cn.rain.character6.configuration.BeansConfiguration6;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/13 11:34
 */
public class Test1 {

    private AnnotationConfigApplicationContext context = null;

    @Before
    public void initIoc() {
        context = new AnnotationConfigApplicationContext(BeansConfiguration6.class);
    }

    @Test
    public void testBeanLifeCycle(){
        // 多实例的bean需要我们获取bean的时候才会创建实例并初始化
//        context.getBean("car");
        // 多实例的Bean即使容器关闭，也不会调用销毁方法，容器不会管理多实例Bean的销毁过程。

        // 测试第二种方式如果是多实例，spring对bean的生命周期的管理是否一致？
        // 结果是一致的。容器加载时不创建实例，容器销毁时不调用销毁方法。
//        context.getBean("cat");

        // 和前两种方式一样，JSR250注解的生命周期当Bean是多实例的话和之前是一样的，不再赘述。
//        context.getBean("dog");

        context.close();
    }
}

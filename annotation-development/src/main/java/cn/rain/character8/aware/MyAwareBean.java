package cn.rain.character8.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import javax.annotation.PostConstruct;

/**
 * description: 由于xxxAware都是通过xxxAwareProcessor进行处理的，而xxxAwareProcessor又都实现了BeanPostProcessor接口，
 * 因此xxxAware的方法都是会先于初始化方法执行。
 * @author 任伟
 * @date 2018/4/15 14:03
 */
@Component
public class MyAwareBean implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware, InitializingBean {

    /**
     *
     * @param applicationContext 我们通过实现ApplicationContextAware中的setApplicationContext方法，
     *                           spring通过该方法将ApplicationContext传给我们。
     * @throws BeansException .
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("传入的ioc："+applicationContext);
    }

    /**
     * @param name BeanNameAware接口的setBeanName方法会将当前Bean的id传给我们
     */
    @Override
    public void setBeanName(String name) {
        System.out.println("当前bean的名字："+name);
    }

    /**
     * @param resolver EmbeddedValueResolverAware接口的setEmbeddedValueResolver方法会将占位符解析器
     *                 传给我们，这个解析器用于解析spring的占位符，如${ }、#{ }等。
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String resolveStringValue = resolver.resolveStringValue("你好 ${os.name} 我是 #{20*18}");
        System.out.println("解析的字符串："+resolveStringValue);
    }


    /**
     * 上面三个实现xxxAware接口的方法都会先于初始化方法执行。
     */
    @Override
    public void afterPropertiesSet() {
        System.out.println("MyAwareBean的初始化。。。");
    }
}

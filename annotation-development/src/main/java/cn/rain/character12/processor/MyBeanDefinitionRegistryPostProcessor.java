package cn.rain.character12.processor;

import cn.rain.character1.beans.Blue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/17 21:12
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    /**
     * 这个方法是BeanDefinitionRegistryPostProcessor中的方法，它会先于BeanFactoryPostProcessor接口的
     * postProcessBeanFactory()方法执行，我们在这个方法中可以额外再给容器中添加一些组件。
     * @param registry Bean定义信息的注册中心，后面BeanFactory就是按照BeanDefinitionRegistry里面保存的每一个bean定义信息来创建bean的实例。
     * @throws BeansException 异常。
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        System.out.println("调用MyBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry()方法，此时容器中Bean的数量共【" + registry.getBeanDefinitionCount() + "】个。");
        // 额外给容器中添加组件，以下两种方式效果一样
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Blue.class).getBeanDefinition();
//        BeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
        registry.registerBeanDefinition("helloBlue", beanDefinition);
    }

    /**
     * 这个方法是BeanDefinitionRegistryPostProcessor的父接口BeanFactoryPostProcessor中的方法，解析请见
     * BeanFactoryPostProcessor接口的讲解。
     * @param beanFactory 所有尚未创建实例的Bean的信息
     * @throws BeansException 异常
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("调用MyBeanDefinitionRegistryPostProcessor.postProcessBeanFactory()方法，此时容器中Bean的数量共【" + beanFactory.getBeanDefinitionCount() + "】个。");
    }
}

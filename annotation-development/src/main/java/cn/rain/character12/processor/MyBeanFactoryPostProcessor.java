package cn.rain.character12.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/17 16:50
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("调用了MyBeanFactoryPostProcessor的postProcessBeanFactory方法...");
        // 由于BeanFactory的执行时机是所有的bean定义已经保存加载到beanFactory，但是bean的实例还未创建。
        // 因此我们可以通过beanFactory参数拿到所有尚未创建实例的Bean的信息。

        // 获取所有已经注册的Bean的总数
        int beanCount = beanFactory.getBeanDefinitionCount();
        System.out.println("当前BeanFactory中有【" + beanCount + "】个Bean。");
        // 获取所有已经注册了的Bean的id
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        System.out.println("这些Bean的id是：");
        for (String name : beanNames) {
            System.out.println(name);
        }
    }
}

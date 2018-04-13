package cn.rain.character6.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * description: 自定义的Bean后置处理器，需要实现BeanPostProcessor并实现其中的两个方法。
 * 这两个方法一个是在bean初始化之前执行，一个是在bean初始化之后执行。
 * 注意，我们自定义的这个Bean后置处理器一旦加入到容器中，将会对容器中所有的Bean起作用。
 *
 * @author 任伟
 * @date 2018/4/13 15:30
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * @param bean 容器中的bean，并且该bean的生命周期完成了创建实例，但是尚未初始化。
     * @param beanName 该bean的在容器中的id。
     * @return 可以直接返回传进来的bean，也可以将这个bean包装后返回
     * @throws BeansException Bean异常。
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在" + beanName + "---->" + bean.getClass() + "初始化之前执行bean的后置处理器的postProcessBeforeInitialization方法。");
        return bean;
    }

    /**
     * @param bean 容器中的bean，并且该bean的生命周期刚刚完成了初始化阶段。
     * @param beanName 该bean的在容器中的id。
     * @return 可以直接返回传进来的bean，也可以将这个bean包装后返回
     * @throws BeansException Bean异常。
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在" + beanName + "---->" + bean.getClass() + "初始化之前执行bean的后置处理器的postProcessAfterInitialization方法。");
        return bean;
    }
}

package cn.rain.character12.configuration;

import cn.rain.character1.beans.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * description: spring的扩展原理。
 *
 * 一、BeanFactoryPostProcessor
 *     1.概念
 *     我们之前学习过了BeanPostProcessor，它是bean的后置处理器，会在bean创建对象初始化前后进行拦截工作。
 *     BeanFactoryPostProcessor：beanFactory的后置处理器，它会在BeanFactory标准初始化之后调用，来定制和
 *     修改BeanFactory的内容。所有的bean定义已经保存加载到beanFactory，但是bean的实例还未创建。
 *     示例见 cn.rain.character12.processor.MyBeanFactoryPostProcessor
 *
 *     2.执行过程
 *    （1）创建IoC容器的对象，调用refresh()方法。
 *    （2）通过调用invokeBeanFactoryPostProcessors(beanFactory)方法执行BeanFactory  -->  AbstractApplicationContext 525 行
 *             如何找到所有的BeanFactoryPostProcessor并执行他们的方法？
 *             ① 直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
 *             ② 在初始化创建其他组件前面执行。因为invokeBeanFactoryPostProcessors(beanFactory)在525行执行，而容器中的Bean
 *                是通过543行的finishBeanFactoryInitialization(beanFactory)方法进行创建，可见BeanFactoryPostProcessor的执行
 *                要在Bean的创建之前。
 *
 * 二、BeanDefinitionRegistryPostProcessor
 *     1.概念
 *     它是BeanFactoryPostProcessor的子接口：public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor。
 *     该接口中定义了一个新的方法void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)；它的执行时机是在所有bean的
 *     定义信息将要被加载，但bean实例还未创建的时候执行。由于我们在上面已经说过BeanFactoryPostProcessor的postProcessBeanFactory()方法
 *     是在所有的bean定义已经保存并加载到beanFactor的时候执行，因此我们可以推断出postProcessBeanDefinitionRegistry()方法会先于
 *     BeanFactoryPostProcessor的postProcessBeanFactory()方法执行，可以利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件。
 *     示例见 cn.rain.character12.processor.MyBeanDefinitionRegistryPostProcessor
 *
 *     2.执行过程
 *    （1）创建IoC容器的对象，调用refresh()方法。
 *    （2）由于BeanDefinitionRegistryPostProcessor继承了BeanFactoryPostProcessor，因此和BeanFactoryPostProcessor一样，
 *         也是通过调用invokeBeanFactoryPostProcessors(beanFactory)方法执行BeanFactory  -->  AbstractApplicationContext 525 行
 *    （3）从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件。
 *             ① 依次执行每个BeanDefinitionRegistryPostProcessor组件的postProcessBeanDefinitionRegistry()方法。
 *             ② 再来执行每个BeanDefinitionRegistryPostProcessor组件的postProcessBeanFactory()方法，该方法是BeanFactoryPostProcessor中的方法。
 *    （4）最后再从容器中找到实现了BeanFactoryPostProcessor接口的组件，然后依次执行这些组件的postProcessBeanFactory()方法。
 *
 * 三、ApplicationListener
 *     1.概念
 *     监听容器中发布的事件，完成事件驱动模型开发。
 *     public interface ApplicationListener<E extends ApplicationEvent> extends EventListener；
 *     所有的监听器都要实现ApplicationListener接口，其中泛型E（即实现了ApplicationEvent的类及其子类）就是要监听的事件。
 *
 *     2.基于事件开发的步骤：
 *    （1）写一个监听器（ApplicationListener实现类）来监听某个事件（ApplicationEvent及其子类）。
 *    （2）把监听器加入到容器中。
 *    （3）只要容器中有相关事件的发布，我们就能监听到这个事件，例如：
 *         ·ContextRefreshedEvent：容器刷新完成（所有bean都完成创建）会发布这个事件。
 *         ·ContextClosedEvent：关闭容器会发布这个事件。
 *    （4）发布一个事件，通过调用容器的publishEvent()方法完成发布：applicationContext.publishEvent()。
 *         演示见cn.rain.character12.Test1。
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * @author 任伟
 * @date 2018/4/17 16:43
 */

@ComponentScan({"cn.rain.character12.processor", "cn.rain.character12.listener"})
@Configuration
public class BeansConfiguration12 {

    /**
     * 为了测试BeanFactoryPostProcessor是在Bean创建之前还是之后执行，我们再加入一个组件Blue。
     * 最后我们测试的结果发现，BeanFactoryPostProcessor确实是在Bean创建实例之前执行。
     */
    @Bean
    public Blue blue(){
        return new Blue();
    }
}

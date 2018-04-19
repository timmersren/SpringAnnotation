package cn.rain.character12.configuration;

import cn.rain.character1.beans.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * description: spring的扩展原理。
 *
 * 一、BeanFactoryPostProcessor
 *     1.概念
 *       我们之前学习过了BeanPostProcessor，它是bean的后置处理器，会在bean创建对象初始化前后进行拦截工作。
 *       BeanFactoryPostProcessor：beanFactory的后置处理器，它会在BeanFactory标准初始化之后调用，来定制和
 *       修改BeanFactory的内容。所有的bean定义已经保存加载到beanFactory，但是bean的实例还未创建。
 *       示例见 cn.rain.character12.processor.MyBeanFactoryPostProcessor
 *
 *     2.执行过程
 *      （1）创建IoC容器的对象，调用refresh()方法。
 *      （2）通过调用invokeBeanFactoryPostProcessors(beanFactory)方法执行BeanFactory  -->  AbstractApplicationContext 525 行
 *               如何找到所有的BeanFactoryPostProcessor并执行他们的方法？
 *               ① 直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
 *               ② 在初始化创建其他组件前面执行。因为invokeBeanFactoryPostProcessors(beanFactory)在525行执行，而容器中的Bean
 *                  是通过543行的finishBeanFactoryInitialization(beanFactory)方法进行创建，可见BeanFactoryPostProcessor的执行
 *                  要在Bean的创建之前。
 *
 * 二、BeanDefinitionRegistryPostProcessor
 *     1.概念
 *       它是BeanFactoryPostProcessor的子接口：public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor。
 *       该接口中定义了一个新的方法void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)；它的执行时机是在所有bean的
 *       定义信息将要被加载，但bean实例还未创建的时候执行。由于我们在上面已经说过BeanFactoryPostProcessor的postProcessBeanFactory()方法
 *       是在所有的bean定义已经保存并加载到beanFactor的时候执行，因此我们可以推断出postProcessBeanDefinitionRegistry()方法会先于
 *       BeanFactoryPostProcessor的postProcessBeanFactory()方法执行，可以利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件。
 *       示例见 cn.rain.character12.processor.MyBeanDefinitionRegistryPostProcessor
 *
 *     2.执行过程
 *      （1）创建IoC容器的对象，调用refresh()方法。
 *      （2）由于BeanDefinitionRegistryPostProcessor继承了BeanFactoryPostProcessor，因此和BeanFactoryPostProcessor一样，
 *           也是通过调用invokeBeanFactoryPostProcessors(beanFactory)方法执行BeanFactory  -->  AbstractApplicationContext 525 行
 *      （3）从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件。
 *               ① 依次执行每个BeanDefinitionRegistryPostProcessor组件的postProcessBeanDefinitionRegistry()方法。
 *               ② 再来执行每个BeanDefinitionRegistryPostProcessor组件的postProcessBeanFactory()方法，该方法是BeanFactoryPostProcessor中的方法。
 *      （4）最后再从容器中找到实现了BeanFactoryPostProcessor接口的组件，然后依次执行这些组件的postProcessBeanFactory()方法。
 *
 * 三、ApplicationListener
 *     1.概念
 *       监听容器中发布的事件，完成事件驱动模型开发。
 *       public interface ApplicationListener<E extends ApplicationEvent> extends EventListener；
 *       所有的监听器都要实现ApplicationListener接口，其中泛型E（即实现了ApplicationEvent的类及其子类）就是要监听的事件。
 *
 *     2.基于事件开发的步骤
 *      （1）写一个监听器（ApplicationListener实现类）来监听某个事件（ApplicationEvent及其子类）。
 *      （2）把监听器加入到容器中。
 *      （3）只要容器中有相关事件的发布，我们就能监听到这个事件，例如：
 *           ·ContextRefreshedEvent：容器刷新完成（所有bean都完成创建）会发布这个事件。
 *           ·ContextClosedEvent：关闭容器会发布这个事件。
 *      （4）发布一个事件，通过调用容器的publishEvent()方法完成发布：applicationContext.publishEvent()。
 *           演示见cn.rain.character12.Test1。
 *
 *     3.监听器监听事件发布的执行过程
 *      （1）创建IoC容器的对象，调用refresh()方法。
 *      （2）在refresh()方法中通过调用finishRefresh()方法完成容器刷新且在这个过程中会发布ContextRefreshedEvent事件。  -->   AbstractApplicationContext  546 行
 *      （3）在finishRefresh()方法中发布事件的过程通过publishEvent(new ContextRefreshedEvent(this))方法发布事件。   -->  AbstractApplicationContext   883 行
 *                publishEvent(new ContextRefreshedEvent(this))方法【发布事件的流程】：
 *                ① publishEvent()方法中通过getApplicationEventMulticaster()方法获取事件派发器（多播器）。  -->  AbstractApplicationContext  393 行
 *                ② 获取到派发器以后通过调用multicastEvent()方法派发事件：    -->  AbstractApplicationContext  393 行
 *                       通过getApplicationListeners(event, type)获取所有的监听器，然后for循环遍历每个监听器。   SimpleApplicationEventMulticaster  -->  128 行
 *                           1).先判断SimpleApplicationEventMulticaster的taskExecutor属性是否为null？            SimpleApplicationEventMulticaster  -->  130 行
 *                              ·不为null，说明可以支持Executor进行异步派发。那么便会开启一条新的线程，异步执行该监听器。 SimpleApplicationEventMulticaster  -->  131-136 行
 *                              ·为null，直接调用invokeListener(listener, event)方法执行该监听器的方法。     SimpleApplicationEventMulticaster  -->  128 行
 *                                      invokeListener(listener, event)方法中最终会拿到listener回调onApplicationEvent方法。  -->   SimpleApplicationEventMulticaster  -->  172 行
 *         ps：不管是容器发布的事件，还是我们自己发布的事件，流程都和上面【发布事件的流程】的步骤是一样的。
 *
 *     4.getApplicationEventMulticaster()获取事件派发器（多播器）的过程
 *      （1）创建IoC容器的对象，调用refresh()方法。
 *      （2）通过调用refresh()方法中的initApplicationEventMulticaster()方法对容器的事件派发器进行初始化。  -->  AbstractApplicationContext  534 行
 *               事件派发器进行初始化的逻辑：
 *               在ConfigurableListableBeanFactory（Bean工厂）中查询是否存在id为"applicationEventMulticaster"的Bean？  -->  AbstractApplicationContext  747 行
 *               ·如果有，说明事件派发器可以使用，故不做任何操作（源码中将它取出来记了下日志，我不将这个当做操作）。  -->  AbstractApplicationContext  747-753 行
 *               ·如果没有，那么会创建一个事件派发器并注册到Bean工厂中。      -->  AbstractApplicationContext  754-762 行
 *
 *     5.getApplicationListeners(event, type)获取所有监听器的过程
 *      （1）创建IoC容器的对象，调用refresh()方法。
 *      （2）通过调用refresh()方法中的registerListeners()方法注册监听器（晚于上面4中获取去事件派发器initApplicationEventMulticaster的方法执行）。    -->  AbstractApplicationContext  540 行
 *               registerListeners()注册监听器的流程：
 *               ① 先通过String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false)
 *                  拿到容器中所有实现了ApplicationListener接口的Bean的id（即拿到了所有监听器）。      -->   AbstractApplicationContext  815 行
 *               ② 由于整个注册监听器的执行实际晚于获取事件派发器，因此此时容器中已经有了事件派发器。于是通过for循环将
 *                  上一步取到的所有监听器的Bean注册到事件派发器中。所以事件发布的时候，事件派发器中所有的监听器就都能感知到了。       -->  AbstractApplicationContext  816-818 行
 *
 *      6.方法上使用@EventListener注解实现事件监听功能
 *        我们在上面2中说过了，如果我们想自己实现一个监听器，需要实现ApplicationListener<E>接口，并且在泛型E处指定要监听的事件Event。
 *        然后将该监听器加入到容器中，这个监听器就可以起作用了。其实这里有一种更为简单的方式可以实现监听器的功能，即在需要监听事件的
 *        方法上使用@EventListener注解，在其class属性中设置需要监听的事件的类型（可以有多个）,并且在该方法的参数列表中传入监听的事件。
 *        例如：@EventListener(class = {ApplicationEvent.class, xxxEvent.class}) ， 示例见cn.rain.character12.listener.ListenerUserService
 *
 *        原理：是通过EventListenerMethodProcessor处理器来解析方法上的@EventListener从而实现监听器的功能，而EventListenerMethodProcessor
 *              是SmartInitializingSingleton接口的实现类，实现了该接口中的afterSingletonsInstantiated()方法，此方法会在所有的单实例Bean创建
 *              完成后执行，下面我们就来探究一下SmartInitializingSingleton的afterSingletonsInstantiated()方法的执行过程。
 *
 *      7. SmartInitializingSingleton --> void afterSingletonsInstantiated()方法的执行过程
 *        （1）创建IoC容器的对象，调用refresh()方法。
 *        （2）通过调用refresh()方法中的finishBeanFactoryInitialization(beanFactory)方法创建剩余所有的(未延迟加载的)单实例Bean。   -->  AbstractApplicationContext  543 行
 *        （3）在finishBeanFactoryInitialization(beanFactory)方法中通过调用beanFactory.preInstantiateSingletons()方法完成剩余单实例Bean的创建。  -->  AbstractApplicationContext  867 行
 *                  beanFactory.preInstantiateSingletons()方法的逻辑：
 *                  ① 通过一个for循环完成所有单实例Bean的创建。   -->    DefaultListableBeanFactory  738-764 行
 *                  ② 再通过一个for循环找出这些Bean中有哪些实现了SmartInitializingSingleton接口。    -->  DefaultListableBeanFactory  769 行
 *                  ③ 实现了SmartInitializingSingleton接口的Bean会执行SmartInitializingSingleton中的afterSingletonsInstantiated()方法。   -->  DefaultListableBeanFactory  781 行
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

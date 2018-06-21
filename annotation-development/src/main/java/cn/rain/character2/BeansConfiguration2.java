package cn.rain.character2;

import cn.rain.character1.beans.Person;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

/**
 * description: 演示Bean的单例和多例。使用xml配置bean的是时候，可以通过scope属性配置该bean为单例还是多例。
 * 注解的配置方式可以通过@Scope来设置其value属性，如果不设置的默认是singleton（单例）的，可以设置的属性值如下：
 *
 * ConfigurableBeanFactory#SCOPE_PROTOTYPE：枚举相当于-->  "singleton" ，两种写法都可以
 * ConfigurableBeanFactory#SCOPE_SINGLETON：枚举相当于-->  "prototype" ，两种写法都可以
 * org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST ， 同一个request请求只创建一个实例，不常用。
 * org.springframework.web.context.WebApplicationContext#SCOPE_SESSION ， 相同的session域中只创建一个实例，不常用。
 *
 * 另外，在spring4项目中我们演示Bean的生命周期的时候已经讲解过，这里再强调一遍。
 * 使用单例模式的bean，会在加载IoC容器的时候就将该bean创建对象并加入到容器中，以后每次使用都从容器中直接拿这个对象；
 * 而多例的Bean在IoC启动的时候不会创建对象，而是在使用（获取）这个bean的时候才会去创建对象并加入到容器中，并且每次
 * 获取这个bean就会创建一次这个bean的对象。
 *
 * 懒加载：对于单实例的bean，如果我们不希望在IoC容器启动的时候就创建某些Bean的对象，而是在第一次用到的时候才创建，
 * 我们就可以使用@Lazy注解将该bean标注为懒加载，注意懒加载只针对单实例Bean，因为多实例Bean本身就具备懒加载的特性。
 *
 * @author 任伟
 * @date 2018/4/12 13:37
 */
@Configuration
public class BeansConfiguration2 {

    @Bean("person2-1")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Lazy
    public Person person(){
        System.out.println("创建person对象并加入到IoC容器中...");
        return new Person("张三", 21);
    }
}

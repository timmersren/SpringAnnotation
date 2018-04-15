package cn.rain.character8.configurantion;

import cn.rain.character8.dao.UserDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * description: Spring利用依赖注入（DI），完成对IOC容器中中各个组件的依赖关系赋值。
 * 1.使用@Autowired完成组件的自动装配：
 * 使用@Autowired注解自动为Bean的属性赋值，我们在UserService中自动注入UserDao。
 * 我们知道spring的自动注入是去容器中找和该属性类型一致的Bean然后为其注入，但是这里有一个问题，
 * 如果类型一致的Bean存在多个怎么办？来模拟一下，我们在UserDao中使用组件扫描为容器添加了一个UserDao，
 * 在此配置类中，我们使用@Bean再为容器中添加一个UserDao。但是要注意一点，如果通过这两种方式为容器中
 * 添加的Bean的默认id一样的话，该Bean只会在容器中存在一个，因此我们必须为其中一个改名。
 *
 * 关于@AutoWired有几点需要说明：
 * （1）默认先按照需要注入的属性的类型去容器中寻找与之类型一直的组件。
 * （2）如果找到多个相同类型的组件，在不进行外部干预的情况下会使用需要被注入的属性的属作为Bean的id，
 * 在这些相同类型的组件中通过这个id来寻找Bean，如果还找不到，抛出异常NoUniqueBeanDefinitionException。
 * （3）如果不想使用上述（2）中spring默认的自动注入规则，我们可以使用@Qualifier注解来指定要注入的Bean是哪个，可以
 * 在其value属性上指定要注入的Bean，这样当有多个类型相同的组件存在时，会按照@Qualifier指定的Bean进行注入，如果
 * 没有找到@Qualifier指定的那个Bean，会抛出异常NoSuchBeanDefinitionException。
 * （4）另外，如果我们使用@AutoWired注解为属性自动注入值，那么默认情况下是必须要到相应的Bean进行装配，如果找不到
 * 的话就会报错。但是我们可以通过将@AutoWired的required置为false（默认是true）来使属性变为不必须装配，即能找到
 * 相应的Bean就装配，找不到就不装配。
 * （5）最后，我们还可以通过使用@Primary注解，将该组件标记为首先的组件，意思是当需要注入的组件的类型存在多时，
 * 优先选择标注了@Primary注解的组件，但是如果使用了@Qualifier指定了Bean，那么还是会按照@Qualifier指定的装配。
 *
 * 2.Spring还支持使用JSR250规范的@Resource注解和JSR330规范的@Inject注解，这些都是java规范的注解而@Autowired是
 * spring规范的注解，因此使用@Autowired的时候同时支持使用@Qualifier、@Primary等注解（因为这些都是spring中的注解）。
 * 	（1）@Resource:可以和@Autowired一样实现自动装配功能，但是它默认是按照组件名称进行装配的，可以通过其name属性
 * 	指定要装配的组件的id。它没有能支持@Primary功能也没有支持@Autowired（required=false）;
 * 	（2）@Inject:需要导入javax.inject的jar包依赖，和Autowired的功能一样，只是没有required=false的功能。
 *
 * 3.@Autowired可以标注的位置：属性、构造器、参数、方法。不管标注在何处都是从IoC容器中获取组件。
 *
 * 4.自定义组件想要使用Spring容器底层的一些组件（如ApplicationContext，BeanFactory等等）：
 * 需要自定义的组件实现xxxAware，在创建自定义组件对象的时候，会调用接口规定的方法注入相关组件，
 * 即把Spring底层一些组件注入到自定义的Bean中。
 * xxxAware的功能都是通过xxxProcessor来处理的，例如ApplicationContextAware通过ApplicationContextAwareProcessor来处理的，
 * 且xxxProcessor都实现了BeanPostProcessor接口（后置处理器），因此我们自定义的Bean使用xxxAware的时候，它是先于该Bean的
 * 初始化方法执行的。详情见MyAwareBean
 *
 * @author 任伟
 * @date 2018/4/14 20:52
 */

@Configuration
@ComponentScan(value = {"cn.rain.character8.dao", "cn.rain.character8.service", "cn.rain.character8.aware"})
public class BeansConfiguration8 {

    @Bean("userDao2")
    @Primary
    public UserDao userDao(){
        UserDao userDao = new UserDao();
        userDao.setToken(2);
        return userDao;
    }
}

package cn.rain.character4.configuration;

import cn.rain.character1.beans.Color;
import cn.rain.character1.beans.Person;
import cn.rain.character4.register.MyImportBeanDefinitionRegistrar;
import cn.rain.character4.selector.MyImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * description: 首先总结一下前面学习的为容器添加组件的两种方式。
 * 1.使用包扫描注解@ComponentScan并且在组件上标注组件注解从而向容器中添加组件。但是
 * 这种方法导入组件有一定的局限性，要求这些组件必须是我们写的，如果是第三方库，我们
 * 无法为其添加组件注解。
 * 2.在配置类中使用@Bean注解，这种方式解决了添加第三方库到容器中的问题，但是这种方式
 * 写起来较麻烦。
 *
 * 这里我们介绍使用@Import注解快速简单的导入组件到容器中。
 * 注意:
 * 1.@Import注解只能使用在类上，并且要使用在标注了@Configuration（即配置类）上。
 * 2.使用@Import导入的组件，其id默认是该组件的全类名。
 * 3.@Import只导入其value属性中定义的组件，这些组件的子类和实现类不会被自动导进来，需要在value中配置才会导进来。
 *
 * 现在讲解@Import注解的第二种导入组件的方式：在@Import注解的value属性中加入实现了ImportSelector接口的类，该类
 * 实现的方法将返回一个String[]数组，这个数组中是一些类的全类名，@Import将会将这些全类名对应的类（组件）全部导入
 * 到容器中,并且这些组件默认的id就是其全类名。
 *
 * 最后@Import还有第三种导入组件的方式：在@Import注解的value属性中加入实现了ImportBeanDefinitionRegistrar接口的类，
 * 该类的实现的方法registerBeanDefinitions中是手动注册一个或多个bean的逻辑。这里要说明一下，并不是@Import注解帮我们
 * 实现了将Bean注册到注册中心的任务，而是在MyImportBeanDefinitionRegistrar.registerBeanDefinitions方法中完成的，
 * 如果我们不在@Import的value属性中加入MyImportBeanDefinitionRegistrar.class，那么手动注册Bean的业务是不会运行的。
 * 只有在value中添加了它，这个业务才会被执行，因此我们说@Import并没有帮我们完成注册Bean的任务，它仅仅是让注册Bean
 * 的业务得到了执行。
 *
 * @author 任伟
 * @date 2018/4/12 22:35
 */
@Configuration
@Import(value = {Color.class, Person.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class}) // 使用Import注解导入组件到容器中
public class BeansConfiguration4 {

}

package cn.rain.character6.configuration;

import cn.rain.character6.bean.Car;
import cn.rain.character6.bean.Cat;
import cn.rain.character6.bean.Dog;
import cn.rain.character6.processor.MyBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * description: 演示bean的生命周期，即bean创建---初始化----销毁的过程。
 * 【第一种】让spring管理bean的生命周期的方式，我们可以在bean中自定义初始化和销毁方法；容器在bean进行到当前
 * 生命周期的时候来调用我们自定义的初始化和销毁方法。
 *
 * 构造过程（即对象创建）：
 *      单实例：在容器启动的时候创建对象
 *      多实例：在每次获取的时候创建对象
 *
 * 初始化过程：
 *      如果我们在@Bean注解的initMethod属性中指定了初始化方法，那么对象创建完成并赋值好之后会调用该初始化方法。
 *
 * 销毁过程：
 *      单实例：如果我们在@Bean注解的destroyMethod属性中指定了销毁方法，那么在容器关闭的时候将会调用指定的销毁方法。
 *      多实例：容器不会管理多实例bean的销毁过程。如果该Bean是多实例的，即使我们在destroyMethod属性中指定了销毁方法，
 *      容器在关闭时也不会去调用，多实例的Bean的销毁过程交给我们自己管理。
 *
 * 除了使用上述方式来为Bean指定初始化和销毁方法外，这里讲解【第二种】方式：
 * 我们还可以通过让Bean实现spring提供的InitializingBean和DisposableBean接口，这两个接口都只有一个方法，分别对应
 * 初始化和销毁方法。只要是实现了这两个接口（或其中一个）的bean，spring就会在Bean的生命周期对应的阶段调用其初始化和
 * 销毁方法。
 * 注意，和第一种Bean的生命周期一样，使用这种方式，如果Bean是多实例的话，容器加载的时候该实例仍然不会创建，直到
 * 第一次获取的时候才会创建。另外，这种方式在容器销毁的时候，如果Bean是多实例，spring仍然不会调用bean的销毁方法进行销毁。
 *
 * 【第三种】方式来让spring管理Bean的初始化即销毁，使用JSR250规范标准（Java Specification Requests Java 规范提案），
 * 这种方式需要我们在Bean中定义的初始化和销毁方法上标注下面两个注解，spring运行后便会在这个Bean对应的生命周期阶段调用对应的
 * 初始化和销毁方法，JSR250标准中的两个注解：
 * --   @PostConstruct:在bean创建完成并且属性赋值完成；来执行初始化方法。
 * --   @PreDestroy：这是一个回调通知，它会在容器销毁bean之前通知我们进行清理工作，从而执行该注解标注的销毁方法。
 *
 *
 * 前边三种方式都是让spring管理bean生命中的初始化和销毁阶段，接下来我们要讲的这个是专门针对bean的初始化阶段，它可以在
 * bean的初始化方法执行之前和之后执行某些方法。
 * 【第四种】方式管理bean的生命周期，管理bean初始化之前和之后的阶段：
 *  要求我们定义一个实现了BeanPostProcessor接口的实现类，并实现该接口中的两个方法postProcessBeforeInitialization、
 *  postProcessAfterInitialization，这两个方法中的逻辑一个是在bean的初始化之前执行，另一个是在bean的初始化之后执行。
 *  注意，我们自定义的这个Bean后置处理器一旦加入到容器中，将会对容器中所有的Bean起作用。
 *
 * @author 任伟
 * @date 2018/4/13 11:33
 */
public class BeansConfiguration6 {

//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }

//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean
    public Cat cat(){
        return new Cat();
    }

//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean
    public Dog dog(){
        return new Dog();
    }

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor(){
        return new MyBeanPostProcessor();
    }
}

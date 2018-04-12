package cn.rain.character3.configuration;

import cn.rain.character1.beans.Person;
import cn.rain.character3.condition.LinuxCondition;
import cn.rain.character3.condition.WindowsCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * description: 演示@Conditional注解，按照条件注册bean。
 * 现在我们向IoC容器中注册了两个Person的bean，id分别是"bill"、"linus"。
 * 但是我们不希望两个bean都加入到IoC容器中，如果我们什么都不做，由于两个Person
 * 都标注了@Bean注解，因此都会加入到容器中。而现在我们希望判断当前JVM运行的操作
 * 系统，如果是windows那么我们将bill注册进容器，如果是Linux那么我们将linus进行注册。
 *
 * 想要完成上述按照条件注册bean，就需要使用到spring4.0推出的注解@Conditional，
 * 该注解中只有一个属性：Class<? extends Condition>[] value();即实现了Condition接口的
 * 实现类的class数组，因此我们先去实现Condition接口，具体请看condition包下的实现。
 *
 * 另外@Conditional注解不只能在方法上使用，还可以使用在类上。当它在方法上使用时，只有
 * 当spring扫描到该方法时才会对这个@Conditional定义的判断条件进行判断，也就是说它使用在
 * 某个方法上的时候该判断条件的作用范围仅限于此方法；当它定义在类上的时候，这个类中的所有
 * 方法都要对该条件进行判断，即定义在类上的时候其作用范围是类中的所有方法。
 *
 * @author 任伟
 * @date 2018/4/12 14:37
 */

@Configuration
public class BeansConfiguration3 {
    @Bean("bill")
    @Conditional(value = {WindowsCondition.class})
    public Person bill(){
        System.out.println("创建了Bill Gates的bean...");
        return new Person("Bill Gates", 62);
    }

    @Bean("linus")
    @Conditional(value = {LinuxCondition.class})
    public Person linus(){
        System.out.println("创建了linus torvalds的bean...");
        return new Person("linus torvalds", 59);
    }

}

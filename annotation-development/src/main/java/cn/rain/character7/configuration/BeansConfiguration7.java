package cn.rain.character7.configuration;

import cn.rain.character7.bean.Person7;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * description: 我们之前使用xml配置bean的时候，通过<property>标签来为属性赋值，
 * 对于注解形式，我们可以@Value注解，具体见Person7。在使用@Value属性为Bean的
 * 属性赋值时，我们可以使用一下几种形式：
 * （1）基本数字即字符串、int、浮点数、boolean类型的值
 * （2）可以使用SpEL，即spring的表达式#{}
 * （3）可以使用${ }取出properties配置文件中的值（由于配置文件中的配置最终都会被加载到
 * 运行环境environment中，其实这种形式的、就是取出环境变量中的值）。
 *
 * 这里说一下第（3）种使用${ }的形式，我们之前在xml配置形式中，想使用这种形式必须要使用
 * <context: property-placeholder location />获取到配置文件的位置，使用注解的方式也要获取
 * 配置文件的位置，在配置类上使用@PropertySource注解将配置文件中的K,V值加载到运行的环境变量中，
 * 这个注解的value属性允许传入一个String[]，这个数组里面就是配置文件的位置，从这里我们也可以
 * 知道通过该注解，可以一次加载多个配置文件。另外配置文件的位置，可以classpath:从类路径开始；
 * 也可以是file：从文件路径开始。
 * 另外，@PropertySource是一个可重复使用的注解，我们可以在其value属性中使用String[]传入多个配置文件
 * 的位置，也可以重复使用该注解传入多个配置文件的位置。此外我们还可以使用@PropertySources注解，它的value
 * 属性封装了一个PropertySource[]：PropertySource[] value()。我们也可以使用该注解传入多个@PropertySource
 * 注解，例如：@PropertySources(value = {@PropertySource("xxx.property"), @PropertySource("yyy.property")})
 * 最后说一下，我们前边多次提到spring运行后会将配置文件加载进运行的环境变量中，我们在character7.Test1
 * 中进行了测试，我们取出环境变量Environment的Property的person.nickName这个key，发现值就是配置文件中的值。
 *
 *
 * @author 任伟
 * @date 2018/4/14 15:48
 */
@PropertySource(value = {"classpath:character7/person.properties"})
@Configuration
public class BeansConfiguration7 {
    @Bean
    public Person7 person7(){
        // 这里我们创建实例的时候，没有为name和age属性赋值。
        return new Person7();
    }
}

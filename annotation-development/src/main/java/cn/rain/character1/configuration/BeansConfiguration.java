package cn.rain.character1.configuration;

import cn.rain.character1.beans.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: 用java注解配置类来代替xml文件的配置方式。
 * 1.在类头使用@Configuration注解标识此类是一个spring的配置类。
 * 2.通过配置类中的方法将Bean加入到IoC容器中，在方法上使用@Bean注解标识向spring容器中
 * 加入Bean，该Bean的类型（对应xml中的class属性）就是该方法的返回值类型；该Bean的id默
 * 认是该方法的方法名，也可以通过@Bean的value属性进行修改。
 *
 * @author 任伟
 * @date 2018/4/11 14:45
 */

@Configuration
public class BeansConfiguration {
    @Bean("person001")
    public Person personAutoInitAll(){
        return new Person("任伟001", 26);
    }

    @Bean("person002")
    public Person personAutoInitName(){
        return new Person("任伟002");
    }


    @Bean("person003")
    public Person personNoInit(){
        return new Person();
    }



}

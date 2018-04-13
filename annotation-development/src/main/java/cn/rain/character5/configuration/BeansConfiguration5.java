package cn.rain.character5.configuration;

import cn.rain.character5.factory.ColorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: 前边讲了3中向容器中添加bean的方式，这里来演示第4种方式--Spring的FactoryBean。
 * 在这里我们看似是为容器中添加了ColorFactory这个组件，实则不然，由于ColorFactory实现了FactoryBean<Color>接口，
 * 因此它已经成为了一个Bean工厂，它不会向容器中添加工厂本身，而是添加工厂中的组件，即泛型<Color>中的Color组件。
 * 我们不妨在测试类中测试一下通过bean的id "colorFactory"获取到的究竟是哪个组件？测试后便会发现容器中存在的组件
 * 确实是Color而不是ColorFactory。
 * 但是如果我就想获取工厂本身这个bean，而不是工厂中生产的组件，应该怎么获取呢？
 * 我们只需要在获取bean的时候，在工厂bean的id前加上&即可，例如context.getBean("&colorFactory")，这样获取的就是
 * 工厂本身了。
 *
 * @author 任伟
 * @date 2018/4/13 10:11
 */

@Configuration
public class BeansConfiguration5 {

    @Bean
    public ColorFactory colorFactory(){
        return new ColorFactory();
    }
}

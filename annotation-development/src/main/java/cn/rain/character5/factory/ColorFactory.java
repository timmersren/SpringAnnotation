package cn.rain.character5.factory;

import cn.rain.character1.beans.Color;
import org.springframework.beans.factory.FactoryBean;

/**
 * description: 实现了spring的FactoryBean<T>接口的类，会成为一个<T>组件工厂Bean，
 * 当我们在配置类中使用@Bean注解向容器中加入该工厂的bean的时，例如：
 * @Bean
 * public ColorFactory ColorFactory(){
 *     return new ColorFactory();
 * }
 * 按照之前学习过的内容我们看的话，这段代码会向IoC容器中加入一个ColorFactory.class的
 * bean，但是由于ColorFactory实现了FactoryBean<Color>，因此它现在是一个Color的工厂bean，
 * 它不会将工厂本身加入到容器中，而是将Color加入到容器中，单实例还是多实例是根据ColorFactory
 * 中的重写方法isSingleton()的返回值决定的。
 *
 *
 * @author 任伟
 * @date 2018/4/13 10:12
 */
public class ColorFactory implements FactoryBean<Color> {
    @Override
    public Color getObject() throws Exception {
        System.out.println("调用了ColorFactory的getObject创建了Color的bean....");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        // 是否为单例
        return true;
    }
}

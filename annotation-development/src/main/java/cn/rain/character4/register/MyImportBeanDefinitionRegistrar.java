package cn.rain.character4.register;

import cn.rain.character1.beans.Rainbow;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * description: 实现了ImportBeanDefinitionRegistrar的类要实现一个registerBeanDefinitions方法，在该方法中
 * 可以通过参数BeanDefinitionRegistry手动注册bean到注册中心，当我们在@Import的value属性中加入实现了
 * ImportBeanDefinitionRegistrar的类时，在该类中注册的Bean将被加入到容器中。
 *
 * @author 任伟
 * @date 2018/4/12 23:36
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * description: 这个方法里编写手动注册bean的逻辑，这里的逻辑是我们判断容器中是否包含Red和Blue组件，如果有那么将Rainbow组件注册进来。
     * @param importingClassMetadata 和ImportSelector中selectImports方法的参数一样，即当前标注@Import注解的类的所有注解信息。
     * @param registry Bean的注册中心。
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean containsRed = registry.containsBeanDefinition("cn.rain.character1.beans.Red");
        boolean containsBlue = registry.containsBeanDefinition("cn.rain.character1.beans.Blue");
        if (containsRed && containsBlue){
            // 定义要注册的那个组件的信息，即指定组件的类型。
            BeanDefinition rainbowDef = new RootBeanDefinition(Rainbow.class);
            // 使用BeanDefinitionRegistry registry对象注册这个组件，并指定该组件的id。
            registry.registerBeanDefinition("rainbow", rainbowDef);
        }
    }
}

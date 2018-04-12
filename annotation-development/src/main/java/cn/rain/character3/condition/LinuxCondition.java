package cn.rain.character3.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * description: 当我们在定义某个Bean的时候，或者在配置类上加了@Conditional注解，spring就会在
 * 启动容器时创建Bean之前进入@Conditional注解中给出的Condition接口的实现类（本类就是一个实现类）
 * 进行条件判断，如果满足条件（即返回true）这个bean才会被创建然后加入到IoC容器中，否则这个bean不会
 * 被创建从而IoC容器中就也没有这个bean。
 *
 * 这里讲一下BeanDefinitionRegistry注册中心，我们在第52行代码中获取bill是否在容器中注册了，结果返回true。
 * 这是由于在BeansConfiguration3中，我们先定义的bill的bean，因此当扫描到bill的bean然后进入其WindowsCondition
 * 进行条件判断，发现是Windows操作系统，于是成功注册到容器中。接下来扫描到linus的bean，然后进入其LinuxCondition
 * 进行判断，在这里（即本类）的52行判断了bill是否成功注册了，并打印输出语句。但是问题来了，运行测试后我们发现
 * 打印的输出语句的顺序先是"bill的bean是否在容器中注册了？true"，然后才是"创建了Bill Gates的bean..."。在我的
 * 理解中，当扫描到bill的bean时判断完它的WindowsCondition条件并且为true以后，它不就应该注册成功并且创建bill的
 * bean对象了吗？如果按照这个逻辑，那应该是先输出“创建了Bill Gates的bean...”即扫描到bill的时候就创建了bill的
 * 对象，然后扫描到linus然后进入到本类才会输出“bill的bean是否在容器中注册了？true”吗？
 * 其实这是理解是错误的，我们说当扫描完bill的bean并且条件判断成功之后，bill确实在注册中心中进行了注册，但并不是
 * 在注册中心注册的时候就创建了它的对象。
 * 正确的逻辑应该是这样，spring先扫描所有定义的bean，且在扫描每个bean的过程中根据判断条件、过滤器等筛选bean。
 * 筛选成功的bean将会注册到注册中心里，我们可以简单的理解注册中心为一个Map，其中key是bean的id，value是bean的全路径，
 * 注意这个步骤仅仅是将筛选后的bean加入到注册中心，但是还并没有创建该bean的对象，当所有的bean扫描完成以后，注册中心
 * 里也就包含了所有筛选过后的bean，这里的bean是所有需要IoC容器创建对象的bean，然后容器才会根据注册中心去一个个的创建
 * bean的对象。所以这里的输出语句是这样一个顺序，当程序进入到本类时，说明所有的bean肯定还没有扫描完成（因为本类时某一
 * 个bean的Condition判断条件），因此这时所有bean的对象都还没有创建，但是筛选成功的bean已经在注册中心里存在了，所以我们
 * 查看bill会看到它在注册中心中，并且创建bill对象的过程要在后边所有的bean扫描完成后。
 *
 * @author 任伟
 * @date 2018/4/12 15:01
 */
public class LinuxCondition implements Condition{
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //1、通过context能获取到ioc使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //2、通过context能获取到当前使用的类加载器
        ClassLoader classLoader = context.getClassLoader();
        //3、通过context能获取到当前JVM的运行环境
        Environment environment = context.getEnvironment();
        //4、通过context能获取到bean定义的注册类对象
        BeanDefinitionRegistry registry = context.getRegistry();

        // 通过BeanDefinitionRegistry对象可以判断容器中的某个bean注册情况（是否注册到了容器中）
        // 此外我们还可以通过BeanDefinitionRegistry对象给容器中注册bean。
        boolean isContain = registry.containsBeanDefinition("bill");
        System.out.println("bill的bean是否在容器中注册了？" + isContain);

        // 通过当前运行环境获取到所使用的操作系统
        String osName = environment.getProperty("os.name");
        if (osName.contains("Linux")){
            return true;
        }
        return false;
    }
}

# SpringAnnotation
这是一套关于spring注解开发的详细教程，讲解了我们使用spring开发时常用的注解。
教程参考自尚硅谷的公开课《spring注解驱动开发》，但是代码和笔记均由本人亲自整理。

## PartⅠ annotation-development
主要演示spring容器相关的注解及其原理

### character1

1. 演示使用spring注解配置Bean并加入到IoC容器，替代原有xml配置Bean的方式。
2. 演示使用包扫描注解替代xml配置形式的<context:component-scan base-package="xx.xxx"/>包扫描。
3. 详细演示了包扫描时的过滤规则，包括使用excludeFilters定义要排除的组件；使用includeFilters定义要包含的组件。
4. 演示了@ComponentScan.Filter中的五种过滤器类型，并且通过自定义过滤器演示了如果使用FilterType.CUSTOM（自定义类型的过滤器）来实现自定义过滤规则。

### character2

1. 演示Bean的作用域，可以通过@Scope注解设置Bean的作用域（单例或多例）
2. 演示使用@Lazy注解使单例的Bean实现懒加载（即容器启动时不会创建该Bean对象，而是第一次获取时才会创建）。

### character3
演示@Conditional注解，按照条件注册bean，只有符合条件的组件才能加入到容器中，此外自己实现Condition接口实现条件判断，在LinuxCondition中还讲解了spring的注册中心。

### character4
演示了使用@Import注解想容器中添加组件的三种方式：

1. @Import的value属性中直接加入组件的类型，即可将该组件加入到容器中。
2. value属性中加入实现了ImportSelector接口的类的类型，会将该类的返回值（返回值是String[]，每个字符串是一个类的全类名）中的所有组件加入到容器中。
3. value属性中加入实现了ImportBeanDefinitionRegistrar接口的类的类型，该实现类中是手动注册bean到注册中心的逻辑，@Import的value属性一旦加入了该类，该类中手动注册bean的逻辑将被执行于是一个或多个bean将被注册到注册中心。

### character5
演示了通过实现spring的FactoryBean<T>接口，通过在该工厂上使用@Bean注解，向容器中添加<T>组件。
至此，我们演示了向容器中添加组件的四种方式，这里给出总结：

1. 通过@ComponentScan注解开启包扫描，并且在组件上标注组件注解，让spring自动扫描组件并加入到容器中。这种方式仅适用于自己写的组件，因为我们无法在第三库的类上加入组件注解。另外我们在扫描某个包的时候还可以通过excludeFilters或者includeFilters定义过滤规则。
2. 通过在配置类中编写方法，并在方法上使用@Bean注解来向容器中添加组件，方法返回值即为组件的类型，组件的id默认是方法名（但组件id可通过@Bean注解的value属性进行更改）。
3. 使用@Import注解为容器中添加组件，其value属性是一个Class[]，里面可以传入普通的组件、实现了ImportSelector接口的实现类、实现了ImportBeanDefinitionRegistrar接口的实现类。
4. 和方式2类似，但是这里的方法的返回值类型是一个实现了FactoryBean<T>的实现类，这样为容器添加的组件不再是方法的返回值类型，而是这个工厂Bean中生产的组件，即泛型<T>中给出的类型。

### character6
演示了spring管理Bean的生命周期的四种方式：
1. 在bean中自定义该bean的初始化方法和销毁方法，在@Bean注解中通过init-method和destroy-method属性指定自定义的初始化和销毁方法。
2. 让bean实现InitializingBean接口并重写该接口的afterPropertiesSet方法，spring在该bean的初始化阶段便会执行afterPropertiesSet方法；同理，让bean实现DisposableBean接口并重写该接口的destroy方法，spring在该bean的销毁阶段便会执行destroy方法。
3. 使用JSR250标准规范，在bean中自定义初始化方法并使用@PostConstruct注解标注，spring在该bean的初始化阶段便会执行自定义的初始化方法；同理，在bean中自定义销毁方法并使用@PreDestroy注解标注，spring在该bean的销毁阶段便会执行自定义的销毁方法。
4. 自定义Bean的后置处理器，要求实现BeanPostProcessor接口，并实现该接口中的两个方法。和前三种方式有两点不同，第一点不同是Bean后置处理器管理的阶段不是bean的初始化和销毁，而是bean的初始化阶段前和初始化阶段后，对应的方法分别是postProcessBeforeInitialization和postProcessAfterInitialization；第二点不同是，前三种方式都是针对某个Bean进行生命周期的管理，而Bean的后置处理器，一旦将我们自定义的Bean后置处理器加入到容器中，那么该后置处理器将会对容器中所有的bean起作用。
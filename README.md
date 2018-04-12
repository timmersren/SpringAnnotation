# SpringAnnotation
spring注解的原理及示例。

## annotation-development
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
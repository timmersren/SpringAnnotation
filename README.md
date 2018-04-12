# SpringAnnotation
spring注解的原理及示例。

## annotation-development
主要演示spring容器相关的注解及其原理

### character1

1. 演示使用spring注解配置Bean并加入到IoC容器，替代原有xml配置Bean的方式。
2. 演示使用包扫描注解替代xml配置形式的<context:component-scan base-package="xx.xxx"/>包扫描。
3. 详细演示了包扫描时的过滤规则，包括使用excludeFilters定义要排除的组件；使用includeFilters定义要包含的组件。
4. 演示了@ComponentScan.Filter中的五种过滤器类型，并且通过自定义过滤器演示了如果使用FilterType.CUSTOM（自定义类型的过滤器）来实现自定义过滤规则。


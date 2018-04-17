package cn.rain.character11.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * description: 演示声明式事务。
 * 一、环境搭建
 * 1.导入相关依赖：数据源、数据库驱动、Spring-jdbc模块
 * 2.配置数据源、JdbcTemplate（Spring提供的简化数据库操作的工具）来操作数据
 * 3.给方法上标注 @Transactional 表示当前方法是一个事务方法。
 * 4.在配置类上标注@EnableTransactionManagement以开启基于注解的事务管理功能
 * 5.配置事务管理器来控制事务。
 *
 * 二、原理
 * 1.进入@EnableTransactionManagement注解我们发现，该注解上使用了@Import注解为容器中添加组件。
 *   且@Import要导入的组件是TransactionManagementConfigurationSelector.class，它的父类实现了ImportSelector接口。
 *   我们前边讲过，实现了ImportSelector接口的类会返回一个存放了某些组件全类名的String[]。 如果@Import导入的是
 *   ImportSelector接口的实现类，那么该实现类返回的String[]中的类名对应的组件将会全部加入容器中。
 *   接着，我们看源码在TransactionManagementConfigurationSelector.selectImports()方法中，它通过switch-case返回了
 *   组件全类名的String[]     -->  TransactionManagementConfigurationSelector  45-52 行
 *          它会根据adviceMode的类型来为容器中添加不同的组件：
 *          ·如果adviceMode类型为PROXY：添加AutoProxyRegistrar.class 和 ProxyTransactionManagementConfiguration.class这两个组件。
 *          ·如果adviceMode类型为ASPECTJ：添加org.springframework.transaction.aspectj.AspectJTransactionManagementConfiguration这个组件。
 *                  那么adviceMode是什么呢？
 *                  这是我们点回@EnableTransactionManagement注解中，发现它里边定义了一个model()属性，且默认值就是PROXY：
 *                  AdviceMode mode() default AdviceMode.PROXY;
 *
 *   好了，至此我们知道了，我们使用@EnableTransactionManagement注解如果不更改model属性，而是使用默认值的话，那么它会为容器中
 *   添加两个组件：AutoProxyRegistrar.class 和 ProxyTransactionManagementConfiguration.class，下面我们来分析这两个组件。
 *
 * 2.AutoProxyRegistrar
 *      （1）通过AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry)给容器中注册组件。    -->  AutoProxyRegistrar  72行
 *      （2）进入registerAutoProxyCreatorIfNecessary(registry)方法发现给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件。    -->  AopConfigUtils  74 行
 *           我们通过分析InfrastructureAdvisorAutoProxyCreator的继承树，发现它的父类中也是实现了SmartInstantiationAwareBeanPostProcessor这个后置处理。
 *           其实InfrastructureAdvisorAutoProxyCreator就是利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用。
 *
 * 3.ProxyTransactionManagementConfiguration做了什么？
 *      （1）给容器中注册事务增强器：
 *              ① 事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource解析事务注解。
 *              ② 事务拦截器：
 *                   1).TransactionInterceptor 保存了事务属性信息，事务管理器。
 *                   2).他是一个 MethodInterceptor。
 *                   3).在目标方法执行的时候:
 *                          1.执行拦截器链。
 *                          2.事务拦截器：
 *                              （1）先获取事务相关的属性
 *                              （2）再获取PlatformTransactionManager，如果事先没有添加指定任何transactionManager，
 *                                   那么最终会从容器中按照类型获取一个PlatformTransactionManager。
 *                              （3）执行目标方法：
 *                                   ·如果异常，获取到事务管理器，利用事务管理回滚操作。
 *                                   ·如果正常，利用事务管理器，提交事务。
 *
 * @author 任伟
 * @date 2018/4/17 15:05
 */
@EnableTransactionManagement
@ComponentScan({"cn.rain.character11.dao", "cn.rain.character11.service"})
@Configuration
public class BeansConfiguration11 {

    /** 在容器中配置数据源 */
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("289443");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    /** 在容器中配置JdbcTemplate */
    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception{
        // 这里的new JdbcTemplate(dataSource())需要说明一下，Spring对@Configuration配置类会特殊处理，
        // 使用@Bean标注的给容器中加组件的方法，多次调用都只是从容器中找组件，不会多次创建。
        return new JdbcTemplate(dataSource());
    }

    /** 在容器中配置事物管理器 */
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }
}

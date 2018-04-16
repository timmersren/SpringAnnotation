package cn.rain.character9.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;

/**
 * description: 演示spring的Profile功能，Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能。
 * 1.使用@Profile注解为Bean进行环境标注（标注在向容器中加入Bean的方法上）：
 * 当Bean使用@Profile进行标识后,默认情况(没激活任何环境)下不会被加载到容器中。只有当spring激活某种环境后,与该
 * 环境一致的@Profile标注的Bean才会被加载到容器中。例如说，下面我们使用@Profile标注了"test"、"dev"、"prod"环境，
 * 如果我们不激活环境，那么这三个标注了@Profile的Bean都不会加载到容器中，假如我们启动了test环境，那么标注了@Profile("test")
 * 的Bean将会被加载到容器中。
 * 也就是说，加了环境标识的Bean只有这个环境被激活的时候才能注册到容器中，除非标注为@Profile("default")，因为这种
 * 标注是默认环境，就相当于没使用@Profile注解。
 *
 * 2.此外@Profile注解还可以加在配置类上:
 * 运行时指定环境，将会加载与该环境一致的配置类，就相当于根据不同的运行环境指定特定的配置文件。
 * 这里需要注意的是，例如这个配置类，在类上加了@Profile("test")，在Bean的方法上又有test、dev、prod
 * 这些不同的环境标识，假如我们启动时使用test环境，那么只有标识了@Profile("test")的Bean才会被加入到
 * 容器中，dev、prod都不会加入容器，这说明了spring根据环境筛选的时候是按照：先筛选配置类-->再筛选@Bean方法
 * 来完成最终的筛选。如果@Bean没有标注任何环境标识，或者标注为@Profile("default"),那么这个Bean在任何环境下
 * 都是加载的（前提是这个Bean所在的配置类被加载了）。
 *
 * 3.spring启动时切换运行环境：
 * （1）运行时使用命令行动态参数: 在虚拟机参数位置加载 -Dspring.profiles.active=test
 * （2）使用代码方式激活环境，详见character9.Test1。
 *
 * @author 任伟
 * @date 2018/4/16 9:58
 */

//@Profile("test")
@PropertySource("classpath:character9/db.properties")
@Configuration
public class BeansConfiguration9 implements EmbeddedValueResolverAware {

    // 我们在这里借机演示一下之前学过的获取外部properties配置文件的几种方式。

    @Value("${jdbc.user}")
    private String user;

    /** 通过实现EmbeddedValueResolverAware接口获取到占位符解析器,通过占位符解析器解析jdbc.driverClass */
    private String driverClass;

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        driverClass = resolver.resolveStringValue("${jdbc.driverClass}");
    }

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${jdbc.password}") String password) throws Exception { // 通过@Value给参数赋值
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${jdbc.password}") String password) throws Exception { // 通过@Value给参数赋值
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/spring4");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${jdbc.password}") String password) throws Exception { // 通过@Value给参数赋值
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mybatis");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }
}

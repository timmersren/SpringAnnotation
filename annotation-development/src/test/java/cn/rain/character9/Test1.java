package cn.rain.character9;

import cn.rain.character9.configuration.BeansConfiguration9;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/16 10:44
 */
public class Test1 {

    @Test
    public void testDataSourceConnect() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfiguration9.class);
        // 获取容器中所有数据源的Bean的名称
        String[] names = context.getBeanNamesForType(DataSource.class);
        for (String name : names) {
            System.out.println(name);
        }
    }

    /**
     * 使用代码的方式激活不同的Profile环境：
     * 我们之前创建容器的方式都是使用new AnnotationConfigApplicationContext(BeansConfiguration9.class)，
     * 通过有参构造器在参数位置上传入我们的主配置类来获取IoC容器，其实我们可以看下这个有参构造器的源码：
     * 	public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
     * 		this(); // 调用无参构造器
     * 		register(annotatedClasses);  // 注册主配置类
     * 		refresh(); // 启动并刷新容器
     *  }
     * 我们若想通过代码方式更改运行环境肯定不能使用这个有参构造器了，我们考虑模仿它的步骤自己注册主配置类，
     * 并且在注册之前改变容器的运行环境，具体见下面代码。
     */
    @Test
    public void testProfile(){
        // 1.因为有参构造器第一步是调用无参构造器，所以我们直接创建一个无参构造器。
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 2.设置需要激活的Profile环境,这里可以设置同时激活一个或多个环境。
        context.getEnvironment().setActiveProfiles("dev", "prod");
        // 3.注册主配置类
        context.register(BeansConfiguration9.class);
        // 4.启动并刷新容器
        context.refresh();

        // 获取容器中所有的数据源，检查这些数据源与当前环境是否一致。
        String[] names = context.getBeanNamesForType(DataSource.class);
        for (String name : names) {
            System.out.println(name);
        }
    }
}



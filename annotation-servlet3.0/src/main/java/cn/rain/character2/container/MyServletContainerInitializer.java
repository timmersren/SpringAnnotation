package cn.rain.character2.container;

import cn.rain.character2.service.HelloService;
import cn.rain.character2.servlet.component.UserFilter;
import cn.rain.character2.servlet.component.UserListener;
import cn.rain.character2.servlet.component.UserServlet;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

/**
 * description: 演示runtimes pluggability（运行时插件能力）之ServletContainerInitializer。
 * 所有实现了ServletContainerInitializer接口的实现类，并将该实现类的全类名写入到了项目的jar包
 * 里面的META-INF/service/javax.servlet.ServletContainerInitializer文件中以后，便会被servlet
 * 容器在启动的时候扫描到。
 * 此外，可以在该实现类上标注@HandlesTypes()注解，并为其value属性传入想要处理的类型，那么传入的
 * 这个类型的所有子类、实现类、子接口等等都会传入到该实现类实现的方法onStartup(Set<Class<?>> set, ServletContext servletContext)
 * 中的set参数中去。
 * 注意：value属性中配置的那个类本身不会被传入。
 * 我这里一直没试出效果，怀疑是META-INF文件夹的路径不对。
 *
 * @author 任伟
 * @date 2018/4/20 15:22
 */
@HandlesTypes({HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    /**
     * @param set 接收该实现类上标注的@HandlesTypes({HelloService.class})注解中要处理的类型。
     *            会接收HelloService.class的所有后台，但是HelloService.class本身并不会被传入。
     * @param sc 当前Web应用的servlet上下文，一个web应用中只有一个servletContext。
     *           可以在项目启动的时候通过ServletContext给容器添加组件。
     *           注意：添加组件只能在容器启动初始化的阶段进行:
     *           （1）通过实现ServletContainerInitializer接口拿到ServletContext给容器添加组件。
     *           （2）在监听器监听到容器启动初始化时拿到ServletContext给容器添加组件（获取方式
     *                见UserListener.contextInitialized方法）。
     * @throws ServletException 异常。
     */
    public void onStartup(Set<Class<?>> set, ServletContext sc) throws ServletException {
        System.out.println("set中接收到的要处理的类型：");
        for (Class clazz : set) {
            System.out.println(clazz);
        }

        // 注册servlet组件,可以自己new UserServlet的对象，也可以直接将UserServlet.class传入。
        ServletRegistration.Dynamic servlet = sc.addServlet("userServlet", new UserServlet());
        // 配置servlet的映射信息
        servlet.addMapping("/user");

        // 注册监听器组件
        sc.addListener(UserListener.class);

        // 注册过滤器组件
        FilterRegistration.Dynamic filter = sc.addFilter("userFilter", UserFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }
}

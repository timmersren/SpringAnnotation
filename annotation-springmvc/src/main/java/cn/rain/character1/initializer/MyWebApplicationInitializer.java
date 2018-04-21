package cn.rain.character1.initializer;

import cn.rain.character1.configuration.RootConfig;
import cn.rain.character1.configuration.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * description: 由于我们继承的这个抽象类的最上层接口是WebApplicationInitializer，且该接口在SpringServletContainerInitializer
 * 中的@HandlesTypes(WebApplicationInitializer.class)注解中被设置成了要处理的类型，且SpringServletContainerInitializer
 * 这个类的全路径被配置在了org.springframework.spring-web-4.3.11.RELEASE.jar这个jar包下的META-INF.services文件中，
 * 因此当Web容器启动的时候便会加载WebApplicationInitializer接口下的所有子类并运行它们的方法，我们编写的这个类正是如此，
 * 因此我们这个类中的方法在容器启动的时候将被运行。
 *
 * @author 任伟
 * @date 2018/4/21 13:46
 */
public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 获取根（父）容器（即spring容器）的配置类。
     * 该方法的作用体现在以前web.xml中就是通过监听器来读取spring的配置文件（父容器）。
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * 获取web容器（即SpringMVC）的配置类。
     * 体现在以前web.xml中就是获取springMVC的配置文件（子容器）。
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * 获取DispatcherServlet的映射信息。
     * "/"：拦截所有请求（包括静态资源（xx.js,xx.png）），但是不包括*.jsp。
     * /*：拦截所有请求；连*.jsp页面都拦截；jsp页面是tomcat的jsp引擎解析的。
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

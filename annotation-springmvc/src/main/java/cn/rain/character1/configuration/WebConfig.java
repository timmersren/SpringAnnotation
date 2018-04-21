package cn.rain.character1.configuration;

import cn.rain.character1.interceptor.MyInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

/**
 * description:
 * 1.springMVC是一个子容器，为了保证父子容器的关系，SpringMVC只扫描Controller组件。
 * 2.
 *
 * @author 任伟
 * @date 2018/4/21 14:00
 */
@SuppressWarnings("all")
@ComponentScan(value = {"cn.rain.character1", "cn.rain.character2"},
        includeFilters = { @ComponentScan.Filter(
                type = FilterType.ANNOTATION, classes = {Controller.class}
        )},
        useDefaultFilters = false)
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
        // 定制视图解析器
        @Override
        public void configureViewResolvers(ViewResolverRegistry registry) {
                // 点进该方法的源码发现，默认所有的页面的路径是/WEB-INF/xxx .jsp
//                registry.jsp();
                // 我们也可以调用registry.jsp(String prefix, String suffix)自己指定路径和后缀。
                registry.jsp("/WEB-INF/views/", ".jsp");
        }

        // 定制拦截器功能
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
                // 1.注册拦截器
                InterceptorRegistration registration = registry.addInterceptor(new MyInterceptor());
                /**
                 * 2.设置拦截器要拦截的请求。
                 * 这里解释下设置的"/**"，在这之前要先解释拦截"/*"指的是一个层级的请求，例如/xx这种请求都可以被/*拦截。
                 * 但是诸如/aa/bb、/aa/bb/cc这种多层级的请求是不能被"/*"拦截的，而若是配置了"/**"则可以对多层级的请求拦截。
                 */
                registration.addPathPatterns("/**");
        }
}

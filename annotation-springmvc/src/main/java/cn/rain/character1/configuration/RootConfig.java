package cn.rain.character1.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * description: spring是一个父容器，为了保证父子容器的关系，
 * 故Spring配置类不扫描Controller组件，将Controller组件交给SpringMVC来扫描。
 *
 * @author 任伟
 * @date 2018/4/21 14:00
 */
@ComponentScan(value = "cn.rain.character1",
               excludeFilters = {@ComponentScan.Filter(
                       type = FilterType.ANNOTATION, classes = {Controller.class}
               )})
public class RootConfig {
}

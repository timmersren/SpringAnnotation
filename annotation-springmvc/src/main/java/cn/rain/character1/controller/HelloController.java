package cn.rain.character1.controller;

import cn.rain.character1.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/21 14:11
 */
@Controller
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        String value = "China....";
        return helloService.sayHello(value);
    }

    /**
     * 由于我们在SpringMVC的配置类中定制了视图解析器，并且配置了视图的路径和后缀。
     * 因此这个请求会自动为我们找寻该路径下的页面。
     */
    @RequestMapping("/suc")
    public String success(){
        return "success";
    }

}

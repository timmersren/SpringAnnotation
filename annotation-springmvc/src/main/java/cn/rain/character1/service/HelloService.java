package cn.rain.character1.service;

import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/21 14:11
 */
@Service
public class HelloService {
    public String sayHello(String value){
        return "Hello " + value;
    }
}

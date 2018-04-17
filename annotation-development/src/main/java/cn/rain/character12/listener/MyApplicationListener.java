package cn.rain.character12.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * description: 实现ApplicationListener接口，实现自己的监听器。所有实现了ApplicationEvent接口的类就是要监听的事件。
 * @author 任伟
 * @date 2018/4/17 23:42
 */
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("收到事件："+event);
    }
}

package cn.rain.character12.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * description: 在方法上使用@EventListener注解实现对事件的监听。
 * @author 任伟
 * @date 2018/4/18 15:40
 */
@Service
public class ListenerUserService {

    @EventListener(classes = {ApplicationEvent.class})
    public void listen(ApplicationEvent event){
        System.out.println("ListenerUserService监听到的ApplicationEvent事件：" + event.toString());
    }
}

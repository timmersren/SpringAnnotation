package cn.rain.character2.servlet.component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * description: servlet三大组件--Listener 监听器。
 * 监听项目的启动和停止。
 *
 * @author 任伟
 * @date 2018/4/20 16:26
 */
@SuppressWarnings("all")
public class UserListener implements ServletContextListener {

    // 监听Servlet容器的启动初始化
    public void contextInitialized(ServletContextEvent sce) {
        // 可以在监听到容器初始化的时候拿到ServletContext
//        ServletContext servletContext = sce.getServletContext();
        System.out.println("UserListener.contextInitialized");
    }

    // 监听Servlet容器的销毁
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("UserListener.contextDestroyed");
    }
}

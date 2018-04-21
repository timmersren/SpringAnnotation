package cn.rain.character1.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/21 15:17
 */
@SuppressWarnings("all")
public class MyInterceptor implements HandlerInterceptor {

    // 目标方法运行之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器的preHandle...拦截的请求是： " + request.getRequestURI());
        return true;
    }

    // 目标方法运行之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器的postHandle...");
    }

    // 页面响应之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截器的afterCompletion...");
    }
}

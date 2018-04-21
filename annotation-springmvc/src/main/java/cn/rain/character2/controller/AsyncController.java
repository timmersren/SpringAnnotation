package cn.rain.character2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

/**
 * description: 演示SpringMVC处理异步请求，通过控制器返回Callable<E>接口来完成。
 * springMVC处理异步请求的步骤：
 * 1.控制器返回Callable。
 * 2.Spring异步处理，将Callable 提交到 TaskExecutor 使用一个隔离的线程进行执行。
 * 3.DispatcherServlet和所有的Filter退出web容器的线程，但是response保持打开状态。
 * 4.Callable返回结果，SpringMVC将请求重新派发给容器，恢复之前的处理。
 * 5.根据Callable返回的结果，SpringMVC继续进行视图渲染流程等（从收请求--视图渲染）。
 *
 * 下面我们根据本类接收到请求后在控制台的输出结果来分析上面的5步：
 *
 * 拦截器的preHandle...拦截的请求是： /asyncmvc
 * 主线程开始...http-nio-8080-exec-5，开始时间 --> 1524323077751
 * 主线程结束...http-nio-8080-exec-5，结束时间 --> 1524323077751
 *
 * ==》至此，主线程执行结束，将会返回Callable，并且DispatcherServlet及所有的Filter退出线程。
 * ============================================================================================================
 *
 *                          ================等待Callable执行==========
 *
 * ============================================================================================================
 * 副线程开始...MvcAsync2，开始时间 --> 1524323077752
 * 副线程结束...MvcAsync2，结束时间 --> 1524323079752
 *
 * ==》至此，Callable执行完成。
 * ============================================================================================================
 *
 *        ================再次收到之前重发过来的请求，因此拦截器的preHandle会再次执行===============
 *
 * ============================================================================================================
 * 拦截器的preHandle....拦截的请求是： /asyncmvc
 * 拦截器的postHandle...（Callable的之前的返回值就是目标方法的返回值）
 * 拦截器的afterCompletion...
 *
 * 另外，我们发现我们通过实现HandlerInterceptor接口编写的非异步的拦截器无法拦截到真正的业务逻辑。
 * 因此我们应该使用异步的拦截器：
 * （1）原生API的拦截器（Servlet中的拦截器）：实现AsyncListener接口。
 * （2）SpringMVC中的拦截器：实现AsyncHandlerInterceptor接口。
 *
 *
 * @author 任伟
 * @date 2018/4/21 20:05
 */
@SuppressWarnings("all")
@Controller
public class AsyncController {

    @ResponseBody
    @RequestMapping(value = {"/asyncmvc"})
    public Callable<String> async(){
        System.out.println("主线程开始..." + Thread.currentThread().getName() + "，开始时间 --> " + System.currentTimeMillis());

        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                System.out.println("副线程开始..." + Thread.currentThread().getName() + "，开始时间 --> " + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("副线程结束..." + Thread.currentThread().getName() + "，结束时间 --> " + System.currentTimeMillis());
                return "springMVC callable..,";
            }
        };
        System.out.println("主线程结束..." + Thread.currentThread().getName() + "，结束时间 --> " + System.currentTimeMillis());
        return callable;
    }
}

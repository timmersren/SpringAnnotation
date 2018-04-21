package cn.rain.character3.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 演示Servlet 3.0以后支持的Servlet异步请求。
 * 让Servlet支持异步请求的步骤：
 * 1.将@WebServlet中的asyncSupported属性设置为true（默认是false）。
 * 2.通过调用request.startAsync()方法开启Servlet异步模式，会返回AsyncContext对象。
 * 3.创建一个Runnable接口的实现类，并实现其run()方法，将业务逻辑写进run()方法中。
 * 4.通过调用AsyncContext对象的start(Runnable run)方法，并将Runnable的实现类传入来对业务逻辑进行异步处理。
 * 4.当业务逻辑处理完成后调用AsyncContext对象的complete()方法进行通知。
 * 5.通过调用AsyncContext对象的getResponse()方法获取响应对象ServletResponse。
 * 6.通过ServletResponse对象完成响应。
 *
 * @author 任伟
 * @date 2018/4/21 19:14
 */
@WebServlet(value = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){

        System.out.println("主线程开始..." + Thread.currentThread().getName() + "开始时间为 --> " + System.currentTimeMillis());
        // 开启异步模式
        final AsyncContext asyncContext = request.startAsync();
        // 创建Runnable实现类并实现run()方法，编写业务逻辑，这里使用匿名内部类实现Runnable接口。
        asyncContext.start(new Runnable() {
            public void run() {
                try {
                    System.out.println("副线程开始..." + Thread.currentThread().getName() + "开始时间为 --> " + System.currentTimeMillis());
                    // 调用业务逻辑
                    sayHello();
                    // 业务逻辑完成后调用complete()方法进行通知。
                    asyncContext.complete();
                    ServletResponse asyncResponse = asyncContext.getResponse();
                    asyncResponse.getWriter().write("Hello Async...");
                    System.out.println("副线程结束..." + Thread.currentThread().getName() + "结束时间为 --> " + System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("主线程结束..." + Thread.currentThread().getName() + "结束时间为 --> " + System.currentTimeMillis());
    }

    // 模拟一个很耗时的业务逻辑
    @SuppressWarnings("all")
    private void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " 【业务正在处理中....】");
        Thread.sleep(3000);
    }
}

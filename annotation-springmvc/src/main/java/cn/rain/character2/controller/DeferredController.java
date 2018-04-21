package cn.rain.character2.controller;

import cn.rain.character2.queue.DeferredResultQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;

/**
 * description: 真实的业务场景中的异步请求，不会像AsyncController中那样简单。实际应用中，异步请求多和
 * 消息中间件同时使用（可以参考课件中的最后一张图）。即当请求被应用1接收后先将其放到消息队列中，
 * 然后由应用2的线程从队列中拿到再去处理，处理完成后调用方法通知结果，应用1监听到处理结果后会将结果取出
 * 并进行相应。
 * 下面我们就来简单模拟一下这个业务场景。
 *
 * @author 任伟
 * @date 2018/4/21 23:29
 */
@Controller
public class DeferredController {
    @ResponseBody
    @RequestMapping("/createOrder")
    public DeferredResult<Object> createOrder(){
        DeferredResult<Object> deferredResult = new DeferredResult<>((long)8000, "create fail...");

        DeferredResultQueue.save(deferredResult);

        return deferredResult;
    }


    @ResponseBody
    @RequestMapping("/create")
    public String create(){
        //创建订单
        String order = UUID.randomUUID().toString();
        DeferredResult<Object> deferredResult = DeferredResultQueue.get();
        deferredResult.setResult(order);
        return "success ==> "+order;
    }
}

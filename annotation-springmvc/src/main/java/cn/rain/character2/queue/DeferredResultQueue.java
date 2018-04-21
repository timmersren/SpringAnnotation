package cn.rain.character2.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.web.context.request.async.DeferredResult;

/**
 * description: 用一个队列简单模拟消息中间件。
 * @author 任伟
 * @date 2018/4/21 23:29
 */
public class DeferredResultQueue {
	
	private static Queue<DeferredResult<Object>> queue = new ConcurrentLinkedQueue<>();
	
	public static void save(DeferredResult<Object> deferredResult){
		queue.add(deferredResult);
	}
	
	public static DeferredResult<Object> get( ){
		return queue.poll();
	}

}

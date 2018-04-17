package cn.rain.character10.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * description: 定义日志的切面类LogAspect。
 * @author 任伟
 * @date 2018/4/16 13:37
 */

@Aspect
public class LogAspect {
    /**
     * 抽取公共切入点：
     * 1.本类引用：直接使用该切入点方法的方法名的字符串即可，即"pointCut()"。
     * 2.如果外部（相同包下）引用：使用类名.方法名的字符串即可，即"LogAspect.pointCut()"。
     * 3.如果外部（不同包下）引用：使用类的全限定名.方法名的字符串即可，即"cn.rain.character10.aspect.LogAspect.pointCut()"。
     */
    @Pointcut("execution(public int cn.rain.character10.calculator.MathCalculator.*(..))")
    public void pointCut(){
    }

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        System.out.println("【@Before前置通知】:方法" + joinPoint.getSignature().getName()
                + "运行...参数列表是：{" + Arrays.asList(joinPoint.getArgs()) + "}");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println("【@After后置通知】:方法" + joinPoint.getSignature().getName() + "结束...");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result){ //注意：如果方法中需要传入参数JoinPoint，它必须位于参数列表的第一个。
        System.out.println("【@AfterReturning返回通知】:方法" + joinPoint.getSignature().getName()
                + "正常返回...运行结果：{" + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception){
        System.out.println("【@AfterThrowing异常通知】:方法" + joinPoint.getSignature().getName()
                + "异常...异常信息：{" + exception + "}");
    }
}

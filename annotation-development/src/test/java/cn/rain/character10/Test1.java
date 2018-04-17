package cn.rain.character10;

import cn.rain.character10.calculator.MathCalculator;
import cn.rain.character10.configuration.BeansConfiguration10;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/16 14:07
 */
public class Test1 {
    private ApplicationContext context = null;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext(BeansConfiguration10.class);
    }

    @Test
    public void testAop() {
        MathCalculator calculator = (MathCalculator) context.getBean("mathCalculator");
        calculator.div(1, 1);
    }
}

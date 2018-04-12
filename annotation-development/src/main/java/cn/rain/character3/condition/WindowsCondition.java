package cn.rain.character3.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/12 15:03
 */
public class WindowsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        // 通过当前运行环境获取到所使用的操作系统
        String osName = environment.getProperty("os.name");
        if (osName.contains("Windows")) {
            return true;
        }
        return false;
    }
}

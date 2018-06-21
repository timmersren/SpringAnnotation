package cn.rain.character4.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Iterator;
import java.util.Set;

/**
 * description: @Import注解的value属性值可以是普通的组件，还可以是实现了ImportSelector接口的类。
 * 该类实现的方法selectImports将返回一个String[]数组，这个数组中是各个组件的全类名。
 * 如果@Import注解的value属性中是这样一个实现了ImportSelector接口的类，那么它将会把该类的
 * selectImports方法的返回值中的所有全类目对应的组件加入到容器中。
 *
 * @author 任伟
 * @date 2018/4/12 23:01
 */
public class MyImportSelector implements ImportSelector {
    /**
     * @param importingClassMetadata 当前标注@Import注解的类的所有注解信息。
     * @return 返回值就是要导入到容器中的组件全类名的String[]数组。
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Set<String> annotationTypes = importingClassMetadata.getAnnotationTypes();
        for (String annotationType : annotationTypes) {
            System.out.println("BeansConfiguration4配置类中包含了注解：" + annotationType);
        }
        // 注意返回值不能为null，否则会发生空指针异常。可以返回一个空数组 return new String[0]。
        return new String[]{"cn.rain.character1.beans.Blue", "cn.rain.character1.beans.Red"};
    }
}

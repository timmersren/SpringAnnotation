package cn.rain.character1.filter;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * description: 自定义@ComponentScan中的FilterType.CUSTOM，需要实现TypeFilter接口。
 * 我们知道，当我们开启@ComponentScan包扫描后，spring会扫描指定包中的所有组件，
 * 然后根据一定的过滤规则去将符合规则的组件加入到IoC容器中，如果我们不定义任何过滤规则，
 * 那么spring将使用默认的包扫描过滤规则将所有标注了组件注解的类加入到IoC容器中，
 * 但是当我们定义了过滤规则后，spring将按照定义的规则去过滤，例如可以在@ComponentScan
 * 注解中使用excludeFilters属性定义排除规则，亦可以使用includeFilters属性定义包含规则，
 * 不管是排除还是包含，我们在其中都要指定过滤器的类型，如FilterType.ANNOTATION、FilterType.ASSIGNABLE_TYPE等
 * 共五种类型，这里提到的这两种最常用。另外还有一种就是FilterType.CUSTOM自定义过滤器。
 * 当我们实现了TypeFilter接口之后，便可以自定义一个拦截器，我们可以在@ComponentScan注解中
 * excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM,value = {MyTypeFilter.class})}使用自定义拦截器。
 * 在@ComponentScan中定义的要扫描的包中的所有组件，在被spring扫描到之后都会被我们自定义的拦截器拦截下来运行一下
 * match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)方法，我刚才通过DEBUG试了一下，
 * component包下的所有类都会走一遍match方法，因此我们在match方法中就可以自定义一些拦截规则，其中metadataReader参数
 * 可以获取到当前被拦截下来的类的信息，具体看下面演示。
 *
 * @author 任伟
 * @date 2018/4/12 11:10
 */
public class MyTypeFilter implements TypeFilter {

    /**
     * @param metadataReader 读取到的当前正在扫描的类的信息
     * @param metadataReaderFactory 可以获取到其他任何类信息的
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取当前被扫描到的类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        System.out.println("自定义拦截器中获取到的被拦截类的【注解信息】：" + annotationMetadata);
        // 获取当前被扫描到的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        System.out.println("自定义拦截器中获取到的被拦截类的【类信息】：" + classMetadata);
        // 获取当前被扫描到的类资源（类的路径）
        Resource resource = metadataReader.getResource();
        System.out.println("自定义拦截器中获取到的被拦截类的【资源路径信息】：" + resource);
        // 获取当前类的类名
        String className = classMetadata.getClassName();
        // 拦截规则：只要类名中包含Dao就返回true
        if(className.contains("Dao")){
            return true;
        }
        return false;
    }
}

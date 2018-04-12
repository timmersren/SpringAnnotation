package cn.rain.character1.configuration;

import cn.rain.character1.beans.Person;
import cn.rain.character1.component.controller.BookController;
import cn.rain.character1.component.dao.BookDao;
import cn.rain.character1.component.service.BookService;
import cn.rain.character1.filter.MyTypeFilter;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * description: 用java注解配置类来代替xml文件的配置方式。
 * 1.在类头使用@Configuration注解标识此类是一个spring的配置类。
 * 2.通过配置类中的方法将Bean加入到IoC容器中，在方法上使用@Bean注解标识向spring容器中
 * 加入Bean，该Bean的类型（对应xml中的class属性）就是该方法的返回值类型；该Bean的id默
 * 认是该方法的方法名，也可以通过@Bean的value属性进行修改。
 *
 * 另外，除了单独配置Bean以外，注解配置形式也可以开启组件扫描，在配置类上使用@ComponentScan
 * 注解，它的功能和xml中开启组件扫描是一样的。
 *
 * 当我们使用@ComponentScan的value属性指定扫描的包后，但是对该包中的某些组件不想让它们加入到容器中，
 * 我们还可以使excludeFilters属性排除某些组件。excludeFilters属性的值是Filter注解的数组，而Filter注解
 * 中需要指定过滤的类型（FilterType）默认是FilterType.ANNOTATION，这里我们使用这个默认类型。此外
 * Filter注解的value属性中传入要过滤的组件的class类型的数组，我们可以点进去看一下，
 * 这个value属性有一个别名classes，因此这里我们使用value和classes都是在使用这个属性。
 *
 * includeFilters与excludeFilters用法完全一样，但是意思正好相反，includeFilters是说在ComponentScan注解的
 * value属性要扫描的那些包中，只将哪些组件加入到容器中。另外，我们发现includeFilters属性和@ComponentScan注解
 * 好像有一些冲突，@ComponentScan指定要扫描的包中的所有组件都要加到容器中，而includeFilters属性又说只允许某些
 * 组件加到容器中，在这种冲突的情况下应该听谁的呢？答案是@ComponentScan，默认的扫描方式扫描@ComponentScan的value
 * 属性配置的包中的所有组件，因此如果想让includeFilters起作用，我们必须要关闭默认的扫描方式，将@ComponentScan中的
 * useDefaultFilters属性置为false。
 *
 * 此外，我们知道@ComponentScan的value属性是指定要扫描的包，它是一个String[]数组，可以同时指定多个包，但是问题来了，
 * 如果我扫描的每个包都要指定一套过滤规则，这种方式显然是不适用的。这时我们可以使用@ComponentScans注解来代理@ComponentScan，
 * 在@ComponentScans注解中只有一个属性，就是ComponentScan[] value()，即@ComponentScans中的属性是一个@ComponentScan数组，
 * 在这里我们可以传入多个@ComponentScan，我们可以在每个@ComponentScan中定义要扫描的包和该包中的过滤规则。
 *
 * 注意：一开始我这样配的 excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = {BookDao.class})}
 * 然后一直报这样一个错：Caused by: java.lang.IllegalArgumentException: @ComponentScan ANNOTATION type filter requires an annotation type: class cn.rain.character1.component.dao.BookDao
 * 它的意思就是说FilterType.ANNOTATION这种过滤类型需要我们在value（也可使用别名classes）属性中配置要排除的注解的类型，
 * 但是我们却写了具体的组件类型BookDao.class，这样是不对的，我们应该写成Repository.class,这样所有标识了@Repository的组件
 * 就会全部被排除掉。其实要想只排除BookService.class也可以，只是就不能使用FilterType.ANNOTATION这种过滤类型了，而是使用
 * FilterType.ASSIGNABLE_TYPE类型来过滤。下面详细解释 FilterType中的这几种类型：
 *
 * （1）FilterType.ANNOTATION：需要传入spring注解的类型，即spring定义好的关于组件的注解（Component.class,Controller.class,
 *  Service.class, Repository.class）
 * （2）FilterType.ASSIGNABLE_TYPE：可以传入组件的具体类型，比如BookDao.class, BookService.class等我们自己定义的组件，
 *  注意这种过滤类型不管是排除还是包含受影响的不仅是该组件，还包括它们的子类和实现类。
 * （3）FilterType.ASPECTJ：可以使用aspectJ表达式来指定过滤规则，不常用。
 * （4）FilterType.REGEX：可以使用正则表达式来指定过滤规则，不常用。
 * （5）FilterType.CUSTOM：自定义过滤规则,详情参看cn.rain.character1.filter.MyTypeFilter。
 *
 * @author 任伟
 * @date 2018/4/11 14:45
 */

@Configuration
@ComponentScan(value = "cn.rain.character1.component"

//        ,excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM,
//                value = {MyTypeFilter.class})}

        ,includeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM,
        value = {MyTypeFilter.class})},

        useDefaultFilters = false


//       , excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
//                value = {Repository.class})}

//        ,includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
//                value = {Controller.class, Service.class})},
//
//        useDefaultFilters = false
                )
public class BeansConfiguration {
    @Bean("person001")
    public Person personAutoInitAll(){
        return new Person("任伟001", 26);
    }

    @Bean("person002")
    public Person personAutoInitName(){
        return new Person("任伟002");
    }


    @Bean("person003")
    public Person personNoInit(){
        return new Person();
    }
}

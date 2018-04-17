package cn.rain.character10.configuration;

import cn.rain.character10.aspect.LogAspect;
import cn.rain.character10.calculator.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * description:
 * 一、演示AOP，指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式。
 * 演示步骤：
 * （1）导入aop模块的jar包：spring-aspects
 * （2）定义一个业务逻辑类（MathCalculator）：在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常等）
 * （3）定义一个日志切面类（LogAspects）：切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行切面类的相应方法。
 * 		通知方法：
 * 			前置通知(@Before)：logStart，在目标方法(div)运行之前运行
 * 			后置通知(@After)：logEnd，在目标方法(div)运行结束之后运行（无论方法正常结束还是异常结束）
 * 			返回通知(@AfterReturning)：logReturn，在目标方法(div)正常返回之后运行
 * 			异常通知(@AfterThrowing)：logException，在目标方法(div)出现异常以后运行
 *  		环绕通知(@Around)：动态代理，手动推进目标方法运行（joinPoint.proceed()）
 *  （4）给切面类的目标方法标注何时何地运行（通知注解）。
 *  （5）将切面类和业务逻辑类（目标方法所在类）都加入到容器中。
 *  （6）必须告诉Spring哪个类是切面类(给切面类上标注@Aspect注解)
 *  （7）以前使用xml文件配置AOP，即使我们都是通过注解来对切面等进行标注，还是需要在xml配置文件中
 *  通过<aop:aspectj-autoproxy/>标签来开启自动注解为业务逻辑类自动创建代理对象，在注解驱动开发中，
 *  这个标签可以通过在配置类上标注@EnableAspectJAutoProxy注解达到相同的效果。
 *
 *  总结上面基于注解配置AOP，三个重要步骤：
 *  （1）将业务逻辑组件和切面类都加入到容器中，再切面类上使用@Aspect注解告诉Spring哪个是切面类。
 *  （2）在切面类上的每一个通知方法上标注通知注解并且为其value属性设置切入点表达式（可抽取出来），
 *  告诉Spring何时（通知注解）何地（切入点表达式）运行。
 *  （3）在配置类上标注@EnableAspectJAutoProxy，以开启基于注解的aop模式。
 *
 *  二、AOP原理
 *  分析方法：看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？这种分析方法不仅适用于
 *  我们现在研究的AOP原理，也适用于各种原理分析，下面我们通过这种方式来分析AOP的原理：
 *
 *  1.@EnableAspectJAutoProxy是什么？
 *  该注解内部通过@Import(AspectJAutoProxyRegistrar.class)：给容器中导入AspectJAutoProxyRegistrar，
 *  由于AspectJAutoProxyRegistrar实现了ImportBeanDefinitionRegistrar接口，所以可以在AspectJAutoProxyRegistrar
 *  中手动给容器注册组件，我们深入源码发现它最终是为容器注册了AnnotationAwareAspectJAutoProxyCreator.class这个Bean，
 *  并且为这个Bean的id命名为"internalAutoProxyCreator"。
 *
 *  2.AnnotationAwareAspectJAutoProxyCreator组件什么时候工作，能干什么？
 *  我们首先看一下它的继承关系：
 *      AnnotationAwareAspectJAutoProxyCreator
 *          extends-->   AspectJAwareAdvisorAutoProxyCreator
 *              extends-->   AbstractAdvisorAutoProxyCreator
 *                  extends-->  AbstractAutoProxyCreator implements 【SmartInstantiationAwareBeanPostProcessor】, 【BeanFactoryAware】
 *
 *  我们发现在AnnotationAwareAspectJAutoProxyCreator的父类AbstractAutoProxyCreator中实现了Bean的后置处理接口，我们知道
 *  它会在在bean初始化完成前后做事情；另外还实现了BeanFactoryAware这个xxxAware接口（前边讲过），它能自动装配BeanFactory。
 *
 *  下面我们在AbstractAutoProxyCreator类中和这两个接口有关的方法打断点：
 *  AbstractAutoProxyCreator.setBeanFactory(BeanFactory beanFactory) // 205行
 *  AbstractAutoProxyCreator.postProcessBeforeInstantiation(Class<?> beanClass, String beanName) // 242行
 *  AbstractAutoProxyCreator.postProcessAfterInitialization(Object bean, String beanName) // 295行
 *
 *  下面我们分析AbstractAutoProxyCreator的子类AbstractAdvisorAutoProxyCreator中和这两个接口有关的方法：
 *  AbstractAdvisorAutoProxyCreator.setBeanFactory(BeanFactory beanFactory) // 54行，该方法重写了父类的方法,并且该重写方法中会调用initBeanFactory((ConfigurableListableBeanFactory) beanFactory);
 *
 *  下面我们分析AbstractAdvisorAutoProxyCreator的子类AspectJAwareAdvisorAutoProxyCreator中和这两个接口有关的方法：
 *  该子类中灭有与这两个接口相关的方法。
 *
 *  最后，我们来看AspectJAwareAdvisorAutoProxyCreator的子类AnnotationAwareAspectJAutoProxyCreator中与这两个接口有关的方法：
 *  AnnotationAwareAspectJAutoProxyCreator.initBeanFactory(ConfigurableListableBeanFactory beanFactory) // 75行
 *
 *  接下来我们使用DEBUG运行character10.Test1.testAop()，并记录流程：
 *  （1）在Test01中通过有参构造器，创建IoC容器。--> Test1 21行
 *  （2）调用无参构造器、注册配置类、调用refresh()方法刷新容器。 --> AnnotationConfigApplicationContext 82-84行
 *  （3）refresh()方法就是对容器进行初始化，点进去我们注意这个方法registerBeanPostProcessors(beanFactory) --> AbstractApplicationContext 528行
 *   它就是注册Bean的后置处理器来拦截Bean的创建。再点进去我们发现它是通过PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this)方法
 *   进行后置处理器的创建，我们点进该方法看它的创建逻辑： --> PostProcessorRegistrationDelegate类的 186-252行
 *          ① String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false); --> 188行，先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
 *          ② beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount)); --> 194行，给容器中加入其他的BeanPostProcessor
 *
 *          注：196行的注释所说“Separate between BeanPostProcessors that implement PriorityOrdered,Ordered, and the rest.”即它会分离实现了PriorityOrdered或Ordered接口的BeanPostProcessors
 *          和原生的BeanPostProcessors，然后将它们装入不同的ArrayList。 --> 198-216 行
 *          ③ 优先注册实现了PriorityOrdered接口的BeanPostProcessor。 --> 219-220 行
 *          ④ 再给容器中注册实现了Ordered接口的BeanPostProcessor。 --> 223-232 行
 *          ⑤ 注册普通的(没实现优先级接口)的BeanPostProcessor。 -->  235-243 行
 *          ⑥ 注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象并保存在容器中，我们以创建创建id为"internalAutoProxyCreator"【Type为AnnotationAwareAspectJAutoProxyCreator.class】
 *             的BeanPostProcessor为例，说明创建步骤：
 *                   1)、创建Bean的实例。
 *                   2)、populateBean；给bean的各种属性赋值。
 *                   3)、initializeBean：初始化bean，下面说明初始化bean的流程：
 *                          1.invokeAwareMethods()：处理Aware接口的方法回调。
 *                          2.applyBeanPostProcessorsBeforeInitialization()：应用后置处理器的postProcessBeforeInitialization()。
 *                          3.invokeInitMethods()；执行自定义的初始化方法。
 *                          4.applyBeanPostProcessorsAfterInitialization()；执行后置处理器的postProcessAfterInitialization()。
 *                   4)、BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功。
 *          ⑦ 调用registerBeanPostProcessors()方法  --> 232 行。
 *             registerBeanPostProcessors()方法是通过beanFactory.addBeanPostProcessor(postProcessor)把BeanPostProcessor注册到BeanFactory中。 --> 294 行
 *
 * ========================以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程================================
 *
 *  这里要说明一点，AnnotationAwareAspectJAutoProxyCreator上层的抽象父类AbstractAutoProxyCreator，实现的是SmartInstantiationAwareBeanPostProcessor，
 *  我们来看看这个后置处理器的继承关系：
 *          SmartInstantiationAwareBeanPostProcessor
 *                  extends --> InstantiationAwareBeanPostProcessor
 *                      extends --> BeanPostProcessor
 *  这里我们要注意区分InstantiationAwareBeanPostProcessor接口中的方法postProcessBeforeInstantiation、postProcessAfterInstantiation和其父接口BeanPostProcessor中方法
 *  postProcessBeforeInitialization、postProcessAfterInitialization的区别：
 *  【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的】
 *  【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 *
 *  （4）finishBeanFactoryInitialization(beanFactory)完成BeanFactory初始化工作，即创建剩下的单实例bean --> AbstractApplicationContext 543行
 *       其创建步骤如下：
 *              ① 遍历获取容器中所有的Bean，通过getBean(beanName)方法依次创建对象;  -->  DefaultListableBeanFactory：738-764 行
 *                 且getBean的过程是  DefaultListableBeanFactory.getBean(beanName) --> 761 行   调用==》 AbstractBeanFactory.doGetBean() --> 197 行
 *                 调用==》 AbstractBeanFactory.getSingleton() --> 302 行
 *              ② 创建单实例Bean的过程：
 *                      1) 因为单实例Bean只创建一次，一旦创建成功就放入缓存中不会再创建。因此在创建Bean之前先试图从缓存中获取，
 *                         如果能获取到说明bean是之前被创建过的可以直接使用；否则再创建，只要创建好的Bean都会被缓存起来。
 *                         ps：spring正是通过上述1)机制来保证单实例Bean只被创建一次。
 *                      2) 若缓存中没获取到Bean，那么将会通过createBean()方法创建Bean --> AbstractAutowireCapableBeanFactory： 447-488 行
 *                         createBean()方法创建Bean的步骤为：
 *                              1. 在真正创建Bean之前还会做一件事情，那就是如果容器中有InstantiationAwareBeanPostProcessor类型的后置处理器，那么
 *                                 会尝试通过此处理器在创建Bean之前返回一个包装好的Bean的代理对象。
 *                                 resolveBeforeInstantiation(beanName, mbdToUse)方法解析BeforeInstantiation。
 *                                 (1)即希望后置处理器在此能返回一个代理对象，如果能返回代理对象就使用该代理对象：
 *                                      bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);   -->  AbstractAutowireCapableBeanFactory  1011 行
 *
 *                                      进入applyBeanPostProcessorsBeforeInstantiation(targetType, beanName)方法，看其逻辑表达的意思是，拿到所有的后置处理器，
 *                                      如果是InstantiationAwareBeanPostProcessor类型的后置处理器，就执行postProcessBeforeInstantiation(beanClass, beanName)方法 --> AbstractAutowireCapableBeanFactory： 1033-1044 行
 *                                      postProcessBeforeInstantiation(beanClass, beanName)方法会在Bean创建实例前返回一个此Bean的代理对象，如果返回的这个代理对象不能null，那么就相当于
 *                                      这个Bean已经存在了，不需要再创建了，然后会执行applyBeanPostProcessorsAfterInitialization(bean, beanName)方法进行Bean的初始化之后的一些操作 -->  AbstractAutowireCapableBeanFactory： 1013 行
 *                                      然后会再包装或者直接返回该Bean（反正这时返回的Bean肯定不为null），最后在1019 行return这个Bean，返回到调用该方法的473 行，由于返回的这个Bean不为null，
 *                                      故直接走475行return这个bean。
 *
 *                                      ps:这就是为什么我们说InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的。AnnotationAwareAspectJAutoProxyCreator在
 *                                      所有bean创建之前会有一个拦截，因为它的父类中实现了InstantiationAwareBeanPostProcessor这个类型的后置处理器，而这种后置处理器会调用postProcessBeforeInstantiation()  ！！！
 *
 *                                 (2)如果不能创建出代理对象，那么返回的这个bean=null，在474 行的if就不会进入，就继续下面第2步通过doCreateBean()方法创建Bean实例。    -->  AbstractAutowireCapableBeanFactory: 473 行
 *                              2. 调用doCreateBean(beanName, mbdToUse, args)方法真正的去创建一个bean实例；和3.6流程一样。 --> AbstractAutowireCapableBeanFactory: 483 行
 *
 *  ========================================================================================================================================================================================================================================
 *  AnnotationAwareAspectJAutoProxyCreator（它实现了InstantiationAwareBeanPostProcessor类型的后置处理器）的作用：
 *  1.每一个bean创建之前，调用postProcessBeforeInstantiation()方法对Bean进行循环创建   --> AbstractAutoProxyCreator： 241-269 行
 *    这里我们不关心其他Bean的创建，当遍历到创建MathCalculator和LogAspect的Bean时，我们这里分析一下：
 *          （1）判断当前bean是否在advisedBeans中（保存了所有需要增强bean）。  --> AbstractAutoProxyCreator：245 行
 *          （2）通过isInfrastructureClass(beanClass)方法判断当前bean是否是基础类型。  -->  AbstractAutoProxyCreator：248 行
 *
 *               判断基础类型的逻辑是，判断其是否实现了Advice、Pointcut、Advisor、AopInfrastructureBean接口 --> AbstractAutoProxyCreator 371-380 行
 *
 *               另外还通过this.aspectJAdvisorFactory.isAspect(beanClass)判断其是否是切面（即是否标注了@Aspect）  -->  AnnotationAwareAspectJAutoProxyCreator 104行
 *               this.aspectJAdvisorFactory.isAspect(beanClass)是通过AbstractAspectJAdvisorFactory.isAspect()方法判断它是不是切面。 -->  AbstractAspectJAdvisorFactory  76 行
 *
 *               而AbstractAspectJAdvisorFactory.isAspect()方法中通过判断(hasAspectAnnotation(clazz) && !compiledByAjc(clazz))来返回它是否是切面   --> AbstractAspectJAdvisorFactory  77 行
 *
 *               而hasAspectAnnotation(clazz)判断其实就是判断该Bean是否使用了@Aspect注解。 -->  AbstractAspectJAdvisorFactory  81 行
 *
 *               compiledByAjc(clazz)判断，大致意思是说这是一种代码风格，它是遍历判Bean中所有的属性名是否有以"ajc$"开头，只要有一个就返回true；
 *               否则返回false。如果返回true（即属性中有"ajc$"开头的），那么!compiledByAjc(clazz)就是false，那么这个Bean就不是切面。     --> AbstractAspectJAdvisorFactory  88-98 行
 *
 *          （3）通过shouldSkip(beanClass, beanName)判断是否需要跳过      --> AbstractAutoProxyCreator  248 行
 *               下面说明是否跳过的逻辑：
 *                      ① 获取候选的增强器List<Advisor> candidateAdvisors（即切面里面的通知方法）    -->  AspectJAwareAdvisorAutoProxyCreator  103 行
 *                         每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor类型，它是Advisor的实现类。
 *                      ② 判断每一个增强器是否是 AspectJPointcutAdvisor类型的，如果是返回true；否则调用父类super.shouldSkip(beanClass, beanName)；
 *                         该方法永远返回false，因此只要不是AspectJPointcutAdvisor类型的便会返回false。  --> AspectJAwareAdvisorAutoProxyCreator  105-111 行
 *
 *  2.创建Bean的实例。
 *  3.创建Bean的实例之后调用postProcessAfterInitialization()方法实现Bean创建后的一些包装工作。如果需要包装，那么会通过调用
 *    return wrapIfNecessary(bean, beanName, cacheKey)方法来完成包装工作  --> AbstractAutoProxyCreator 297-299 行
 *          包装的逻辑：
 *          （1）获取当前bean的所有增强器（即通知方法）：Object[] specificInterceptors  -->  AbstractAutoProxyCreator  346 行
 *                  获取增强器的逻辑：
 *                  ① 找到候选的所有的增强器（找哪些通知方法是需要切入当前bean方法的）  -->  AbstractAdvisorAutoProxyCreator  70 行
 *                  ② 获取到能在bean使用的增强器。
 *                  ③ 给增强器排序
 *          （2）保存当前bean在advisedBeans中。  -->  AbstractAutoProxyCreator 348 行
 *          （3）如果当前bean需要增强，创建当前bean的代理对象：   -->  AbstractAutoProxyCreator 349 行
 *                  ① 获取所有增强器（即通知方法）    -->  AbstractAutoProxyCreator 456 行
 *                  ② 保存到proxyFactory。   -->   AbstractAutoProxyCreator 457 行
 *                  ③ 创建代理对象，会有两种形式的代理对象，通过调用createAopProxy(AdvisedSupport config)方法spring根据判断条件决定创建哪种。  --> DefaultAopProxyFactory  50 行
 *                          ·JdkDynamicAopProxy(config)--jdk动态代理；  -->  DefaultAopProxyFactory  63 行
 *                          ·ObjenesisCglibAopProxy(config)--cglib的动态代理；  -->  DefaultAopProxyFactory  60 行
 *          （4）给容器中返回当前组件使用cglib增强了的代理对象。    -->  AbstractAutoProxyCreator   301 行
 *  ps:以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程。
 *  4.目标方法（即业务逻辑类MathCalculator中的方法）的执行过程：
 *    在分析执行过程前要说明，容器中保存的是组件的代理对象（cglib增强后的对象），这个对象里面保存了详细信息（比如增强器，目标对象等等）。
 *          （1）调用CglibAopProxy的intercept()方法拦截目标方法的执行。  -->  CglibAopProxy  642-687 行
 *          （2）根据ProxyFactory对象获取将要执行的目标方法拦截器链。  -->   CglibAopProxy  659 行
 *               List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *                   获取拦截器链的逻辑步骤：
 *                   ① 我们F5进入获取拦截器链的方法中便进入到了AdvisedSupport类中，其485-494行是获取拦截器链的逻辑，
 *                      在该逻辑中的第489行，我们F5进入getInterceptorsAndDynamicInterceptionAdvice()方法中，发现它
 *                      是通过List<Object> interceptorList = new ArrayList<Object>(config.getAdvisors().length);
 *                      保存所有的拦截器。并且这个ArrayList创建时就给出了长度，该长度就是所有增强器的个数，这里共有5个，
 *                      一个默认的ExposeInvocationInterceptor和4个（通知方法）增强器。   -->  DefaultAdvisorChainFactory   55 行
 *                   ② 遍历所有的增强器，通过调用registry.getInterceptors(advisor)方法将其转为MethodInterceptor[] Interceptor。   -->  DefaultAdvisorChainFactory  65、84、89 行
 *                          registry.getInterceptors(advisor)转换为MethodInterceptor[] Interceptor的逻辑：
 *                          1).如果是MethodInterceptor，直接加入到集合中。   -->  DefaultAdvisorAdapterRegistry    82-84 行
 *                          2).如果不是MethodInterceptor，使用AdvisorAdapter将增强器转为MethodInterceptor。   -->  DefaultAdvisorAdapterRegistry    85-89 行
 *                          3).转换完成返回MethodInterceptor数组。     -->  DefaultAdvisorAdapterRegistry  93 行
 *          （3）如果没有拦截器链，直接执行目标方法。    -->   CglibAopProxy  663-670 行
 *          （4）如果有拦截器链，把需要执行的目标对象、目标方法、拦截器链等信息传入并创建一个CglibMethodInvocation对象，
 *               并调用这个刚创建的CglibMethodInvocation对象的proceed()方法，返回Object retVal。   -->  CglibAopProxy  673 行
 *          （5）CglibMethodInvocation对象的proceed()方法的执行过程即拦截器链的触发过程：
 *                  ① 如果没有拦截器执行执行目标方法，或者拦截器的索引和拦截器数组-1大小一样（即执行到了最后一个拦截器）执行目标方法  --> ReflectiveMethodInvocation  154-181 行
 *                  ② 链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行。      -->  ReflectiveMethodInvocation  154-181 行
 *  				    ps：拦截器链的机制，保证通知方法与目标方法的执行顺序。
 *
 * 三、AOP原理的总结
 *      1.利用@EnableAspectJAutoProxy注解开启AOP功能。
 *      2.@EnableAspectJAutoProxy注解会给容器中注册一个组件AnnotationAwareAspectJAutoProxyCreator。
 *      3.AnnotationAwareAspectJAutoProxyCreator是一个【InstantiationAwareBeanPostProcessor】类型的后置处理器。
 *      4.容器的创建流程：
 *              （1）通过registerBeanPostProcessors()注册后置处理器，创建AnnotationAwareAspectJAutoProxyCreator对象。
 *              （2）通过finishBeanFactoryInitialization()初始化剩下的单实例bean：
 *                      ① 创建业务逻辑组件和切面组件。
 *                      ② AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程。
 *                      ③ 组件创建完之后，判断组件是否需要增强（包装）。如果需要增强，那么将切面的通知方法包装成增强器（Advisor），
 *                         然后给业务逻辑组件创建一个代理对象（默认用cglib；如果是实现了jdk的Proxy接口，会使用jdk动态代理）。
 *      5.执行目标方法：
 *              （1）代理对象执行目标方法
 *              （2）通过CglibAopProxy.intercept()方法进行拦截，拦截过程：
 *                      ① 得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）。
 *                      ② 利用拦截器的链式机制，依次进入每一个拦截器进行执行。
 *                      ③ 执行效果：
 *                         1).正常执行：前置通知-》目标方法-》后置通知-》返回通知
 *                         2).出现异常：前置通知-》目标方法-》后置通知-》异常通知
 *
 *
 * @author 任伟
 * @date 2018/4/16 13:40
 */
@EnableAspectJAutoProxy
@Configuration
public class BeansConfiguration10 {

    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspect logAspect(){
        return new LogAspect();
    }
}

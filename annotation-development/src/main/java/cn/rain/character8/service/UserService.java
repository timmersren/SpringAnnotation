package cn.rain.character8.service;

import cn.rain.character8.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;


/**
 * description:
 * （1）使用@Resource进行自动装配（不常用）：
 * 默认是根据属性的名称作为Bean的id进行查找组件并装配（@Autowired默认是优先按照类型），可以通过name属性
 * 来指定要装配的组件的id。它不支持使用@Primary标注优先装配的组件，也不支持@Qualifier指定要装配的组件。
 *
 * （2）使用@Inject进行自动装配（和@Autowired几乎一样，但使用还需导jar包且相比于@Autowired没有required属性，因此不选择使用它）：
 * 使用@Inject需要导入javax.inject的jar依赖。@Inject除了没有required属性外，其它和@Autowired一样，也支持@Qualifier和@Primary。
 *
 * @author 任伟
 * @date 2018/4/14 21:04
 */
@SuppressWarnings("all")
@Service
public class UserService {

    @Autowired(required = false) // 将required属性置为false（默认为true），如果找不到对应的Bean进行装配，那么就放弃装配。
    @Qualifier("userDao2222") // 使用@Qualifier要求必须注入id为userDao2222的UserDao
//    @Resource(name = "userDao2")
//    @Inject
    private UserDao userDao;

    @Override
    public String toString() {
        return "UserService{" +
                "userDao=" + userDao +
                '}';
    }
}

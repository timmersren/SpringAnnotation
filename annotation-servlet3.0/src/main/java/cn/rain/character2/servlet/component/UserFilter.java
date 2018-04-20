package cn.rain.character2.servlet.component;

import javax.servlet.*;
import java.io.IOException;

/**
 * description: servlet三大组件--Filter 过滤器。
 * @author 任伟
 * @date 2018/4/20 16:13
 */
public class UserFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 过滤请求
        System.out.println("UserFilter.doFilter....");
        // 放行
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}

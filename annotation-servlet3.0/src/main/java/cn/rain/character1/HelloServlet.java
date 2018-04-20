package cn.rain.character1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 以前我们编写Servlet后，最原始的方法需要在web.xml中对每个Servlet进行配置，以便拦截相应的url。
 * 而使用注解的形式开发servlet完全可以将web.xml删除出，例如这里的servlet，我们只需要在类上标注@WebServlet注解，
 * 并在其value属性中配置要拦截的url即可代替之前的那一大堆配置。
 *
 * 除了@WebServlet注解，以前能在web.xml中配置的属性在servlet3.0之后都可以用注解代替。
 *
 * @author 任伟
 * @date 2018/4/20 14:31
 */
@WebServlet({"/hello", "/hello2"})
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("hello world !");
    }
}

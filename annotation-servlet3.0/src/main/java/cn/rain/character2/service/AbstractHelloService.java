package cn.rain.character2.service;

/**
 * description:
 *
 * @author 任伟
 * @date 2018/4/20 15:30
 */
public abstract class AbstractHelloService implements HelloServiceExt {

    public abstract void abstractSay();

    public void sayHello() {
        System.out.println("say hello");
    }

    public void sayHi() {
        System.out.println("say hi");
    }
}

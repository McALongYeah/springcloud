package com.yc;

public class Test {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");

        HelloI target = new HelloImpl(); //目标类
        CustomInvocationHandler handler = new CustomInvocationHandler(target);
        //生成代理类
        Object proxy = handler.createProxy();
        System.out.println(proxy); //proxy0对象

        HelloI hi = (HelloI) proxy; //hi = Proxy0对象
        hi.sayHello();//0 syhello
        hi.showBye();

    }
}

package com.yc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

public class CustomInvocationHandler implements InvocationHandler {

    private Object target; //目标类

    public CustomInvocationHandler(Object target){
        this.target = target;
    }

    //生成代理,对象的方法
    public Object createProxy(){
        //jdk提供的Proxy类  有一个方法根据接口生成代理类对象的方法
        Object proxy = Proxy.newProxyInstance(CustomInvocationHandler.class.getClassLoader(),target.getClass().getInterfaces(),this);
        return proxy; //$Proxy sayHello() showBye()
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().indexOf("say")>=0){
            showTime();
        }
        //反射机制调用目标类的目标方法
        Object returnValue = method.invoke(target,args);  //HelloImpl.sayHello() 目标类的目标方法
        return returnValue;
    }

    public void showTime(){
        System.out.println("时间为: " + new Date());
    }
}

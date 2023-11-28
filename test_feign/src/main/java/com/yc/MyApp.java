package com.yc;

import feign.Feign;
import feign.gson.GsonDecoder;

import java.util.List;

public class MyApp {
    public static void main(String[] args) {
//        生成的代理类文件的保存路径
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        GitHub github = Feign.builder() //Feign客户端的构造器
                .decoder(new GsonDecoder()) //gson解码器将json数据转为java对象
                .target(GitHub.class, "https://api.github.com");//（接口,访问地址的前缀）

        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor);
        }
    }
}

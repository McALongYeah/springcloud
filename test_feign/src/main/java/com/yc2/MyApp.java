package com.yc2;

import feign.Feign;
import feign.gson.GsonDecoder;

import java.util.List;

public class MyApp {
    public static void main(String[] args) {
//        生成的代理类文件的保存路径
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        ResfoodApi resfoodApi = Feign.builder() //Feign客户端的构造器
                .decoder(new GsonDecoder()) //gson解码器将json数据转为java对象
                .target(ResfoodApi.class, "http://localhost:9001");//（接口,访问地址的前缀）

        jsonModel jm =  resfoodApi.findAll();
        System.out.println(jm);
    }
}

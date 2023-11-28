package com.yc.web.controller;


import com.yc.api.ResfoodApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("resorder")
@Slf4j
//@Api 注解用于标注一个Controller（Class）。
//tags="说明该类的作用，可以在前台界面上看到的注解"
//value="该参数无意义，在UI界面上看不到，不需要配置"

public class ResorderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ResfoodApi resfoodApi;

    @RequestMapping(value = "addCart" ,method = {RequestMethod.GET,RequestMethod.POST} )
    public Map<String,Object> addCart(@RequestParam Integer fid, @RequestParam Integer num, HttpSession session) {
        Map<String, Object> map = new HashMap<>();

        Map<String,Object> result = resfoodApi.findById(fid);

//        //方案二: 利用
//        String url = "http://resfood/resfood/findById/"+fid;
//        Map<String,Object> result = this.restTemplate.getForObject(url,Map.class);

//        //方案一直接使用ip:端口
//        Map<String,Object> result = this.restTemplate.getForObject("http://localhost:9000/resfood/findById?fid=" + fid,Map.class);
        log.info("发送请求后返回的商品信息:" + result);
        return map;
    }

}

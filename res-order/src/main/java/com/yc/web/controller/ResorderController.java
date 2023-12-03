package com.yc.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.api.ResfoodApi;
import com.yc.bean.Resfood;
import com.yc.biz.GoodsBiz;
import com.yc.web.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    @Autowired
    private GoodsBiz goodsBiz;

    @GetMapping("payAction")
    public Map<String,Object> payAction(   Integer flag    ) throws InterruptedException {
//        //TODO: 1. 测试慢请求
//        if( flag==null ){
//            Thread.sleep(1000);
//        }
        //2.异常数
        Random r = new Random();
        int a = r.nextInt(5);
        if (a==0||a==1){
            throw new RuntimeException("发生异常");
        }
        Map<String,Object> map=new HashMap<>( );
        //取出当前用户的订单金额,调用第三方接口，完成支付.
        map.put("code",1);
        return map;
    }


    @RequestMapping(value = "serviceA",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> serviceA(){
        Map<String,Object> map = new HashMap<>();
        goodsBiz.goodsInfo();
        map.put("code",1);
        return map;
    }

    @RequestMapping(value = "serviceB",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> serviceB(){
        Map<String,Object> map = new HashMap<>();
        goodsBiz.goodsInfo();
        map.put("code",1);
        return map;
    }

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
        if (!"1".equals(result.get("code").toString())){
            map.put("code","0");
            map.put("msg","查无此商品");
            return map;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Resfood food = objectMapper.convertValue(result.get("obj"),Resfood.class);

        //         * 2.从session 中取出Cart (map)
        Map<Integer, CartItem> cart = new HashMap<Integer, CartItem>();
        if (session.getAttribute("cart") != null){
            cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        }else {
            session.setAttribute("cart",cart);
        }

//         * 3.判断这个商品在Cart(map)中是否存在
        CartItem ci;
        //判断这个商品在cart（map）是否有
        if (cart.containsKey(fid)){
            ci = cart.get(fid);
            ci.setNum(ci.getNum() + num);
            cart.put(fid,ci);
        }else {
            //         * 4.没有 则创建一个CartItem 存到map 中
            ci = new CartItem();
            ci.setNum(num);
            ci.setFood(food);
            cart.put(fid,ci);
        }

//         * 5.有增加数量]

        //处理数量
        if (ci.getNum() <= 0){
            cart.remove(fid);
        }
        session.setAttribute("cart",cart);
        map.put("code",1);
        map.put("obj",cart.values());
        return map;

    }

    @RequestMapping(value = "getCartInfo" ,method = {RequestMethod.GET,RequestMethod.POST} )
    //ApiParam 用在请求参数前面,用于对参数进行描述或说明是否为必添项等说明
    public Map<String,Object> getCartInfo(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (session.getAttribute("cart")==null || ((Map<Integer, CartItem>) session.getAttribute("cart")).size() <= 0){
            map.put("code",0);
            return map;
        }
        Map<Integer,CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        map.put("code",1);
        map.put("obj",cart.values()); //cart.values 返回的是map 的值的set
        return map;
    }

    @RequestMapping(value = "clearAll" ,method = {RequestMethod.GET,RequestMethod.POST} )
    //ApiParam 用在请求参数前面,用于对参数进行描述或说明是否为必添项等说明
    public Map<String,Object> clearAll(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        session.removeAttribute("cart");
        session.removeAttribute("code");
        map.put("code",1);
        return map;
    }



}

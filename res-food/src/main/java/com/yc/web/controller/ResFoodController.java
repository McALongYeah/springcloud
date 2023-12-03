package com.yc.web.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.biz.ResFoodBiz;
import com.yc.config.RedisKeys;
import com.yc.web.model.MyPageBean;
//import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("resfood")
@Slf4j
//@Api 注解用于标注一个Controller（Class）。
//tags="说明该类的作用，可以在前台界面上看到的注解"
//value="该参数无意义，在UI界面上看不到，不需要配置"
//@Api(value = "ResFoodController", tags = "菜品的控制层")
//动态刷新配置
@RefreshScope  //只能在类和方法上标明,要求actuator暴露了endpoints端点,
public class ResFoodController {

    @Autowired
    private ResFoodBiz resFoodBiz;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${res.pattern.dateFormat}")
    private String dateFormatString; //利用di机制从属性文件读取配置

    public Set<Thread> set= new HashSet<>();
    @GetMapping("/test")
    public Object test() throws InterruptedException {
        Thread thread = Thread.currentThread();
        set.add(thread);
        log.info(    "线程数为:"+ set.size() +",当前线程编号为:"+ thread.getId() );
        return thread.toString();
    }//  然后访问服务，查看输出信息

    @RequestMapping(value = "timeService",method={RequestMethod.GET})
    public Map<String,Object> timeService(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dateFormatString);
        String dString = df.format(date);
        Map<String ,Object> map = new HashMap<>();
        map.put("code",1);
        map.put("obj",dString);
        return map;
    }

    //@ApiOperation 注解在用于对一个操作或HTTP方法进行描述
    //@ApiOperation("查看详情次数增加")
    @RequestMapping(value = "detailCountAdd",method = {RequestMethod.GET,RequestMethod.POST})
//    @ApiImplicitParams(
//            @ApiImplicitParam(
//                   name = "fid",
//                   value = "菜品号",
//                 required = true
//          )
//  )
    //ApiParam 用在请求参数前面,用于对参数进行描述或说明是否为必添项等说明
    public Map<String,Object> detailCountAdd(
//            @ApiParam(name = "fid",value = "菜品的id号")
            @RequestParam Integer fid){
        Map<String ,Object> map = new HashMap<>();
        Long count = 1L;
        if (redisTemplate.hasKey(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid) == false){
            redisTemplate.opsForValue().set(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid,1);
        }else{
            count = redisTemplate.opsForValue().increment(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid);
        }
        map.put("code",1);
        map.put("obj",count);  // obj存正常返回的结果
        return map;
    }

    //@ApiOperation 注解在用于对一个操作或HTTP方法进行描述
//    @ApiOperation("查询菜品")
//    @RequestMapping(value = "findById",method = {RequestMethod.GET,RequestMethod.POST})
//    @ApiImplicitParams(
//            @ApiImplicitParam(
//                    name = "fid",
//                    value = "菜品的id号2",
//                    required = true
//            )
//    )
    //ApiParam 用在请求参数前面,用于对参数进行描述或说明是否为必添项等说明
//    public Resfood findById(
////            @ApiParam(name = "fid",value = "菜品的id号")
//            @RequestParam Integer fid){
//        Resfood resfood = resFoodBiz.findById(fid);
//        return resfood;
//    }

//    @RequestMapping(value = "findById/{fid}",method = {RequestMethod.GET,RequestMethod.POST})
//
//    @ApiOperation(value="根据菜品查寻操作")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "fid",value = "菜品号",required = true)
//    })
    @GetMapping( "findById/{fid}")
    public Map<String,Object> findById(@PathVariable Integer fid){
        Map<String,Object> map=new HashMap<>();

        Resfood rf=null;
        try{
            rf=this.resFoodBiz.findById(fid);
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("code",0);
            map.put("msg","Error occurred while fetching data");
        }
        map.put("code",1);
        map.put("data",rf);
        return map;
    }

    @GetMapping("findAll")
//    @ApiOperation(value = "查询所有菜品")
    public Map<String,Object> findAll(){
        Map<String,Object> map=new HashMap<>();
        List<Resfood> list=null;
        try {
            list = this.resFoodBiz.findAll();
        }catch (Exception ex){
            map.put("code",0);
            map.put("msg",ex.getCause());
            ex.printStackTrace();
            return map;
        }
        map.put("code",1);
        map.put("obj",list);
        return map;
    }

    @RequestMapping("/findByPage")
    @SentinelResource("hotkey-page") //流控资源名
    public Map<String,Object> findByPage(@RequestParam int pageno,@RequestParam int pagesize,@RequestParam(required = false)  String sortby,@RequestParam (required = false) String sort){
        Map<String,Object> map = new HashMap<>(); //返回的json字符串
        //此处的Page 是dao层的组件,这种被称为PO对象（持久化对象-》与表结构相同），到controller层要进行转化 转化成vo对象(值对象->为了页面展示需要)
        Page<Resfood> page = null;

        try{
            page = this.resFoodBiz.findByPage(pageno,pagesize,sortby,sort);
        }catch (Exception ex){
            map.put("code",0);
            map.put("msg",ex.getCause());
            ex.printStackTrace();
            return map;
        }

        map.put("code",1);
        MyPageBean pageBeanVO = new MyPageBean();
        pageBeanVO.setPageno(pageno);
        pageBeanVO.setPagesize(pagesize);
        pageBeanVO.setSort(sort);
        pageBeanVO.setSortby(sortby);
        pageBeanVO.setTotal(page.getTotal());
        pageBeanVO.setDataset(page.getRecords());

        //计算总页数
        long totalPages=page.getTotal()%pageBeanVO.getPagesize()==0?
                page.getTotal()/pageBeanVO.getPagesize():page.getTotal()/pageBeanVO.getPagesize()+1;
        pageBeanVO.setTotalpages((int) totalPages);
        //上一页页号的计算
        if (pageBeanVO.getPageno()<=1){
            pageBeanVO.setPre(1);
        }else {
            pageBeanVO.setPre(pageBeanVO.getPageno()-1);
        }
        //下一页的计算
        if (pageBeanVO.getPageno()==totalPages){
            pageBeanVO.setNext((int) totalPages);
        }else {
            pageBeanVO.setNext(pageBeanVO.getPageno()+1);
        }
        map.put("data",pageBeanVO);
        //TODO
        return map;


    }

}

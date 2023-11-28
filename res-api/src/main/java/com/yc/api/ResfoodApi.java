package com.yc.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@FeignClient("resfood")
public interface ResfoodApi {
    @RequestMapping(value = "resfood/detailCountAdd")

    public Map<String,Object> detailCountAdd(@RequestParam Integer fid);


    @GetMapping( "resfood/findById/{fid}")
    public Map<String,Object> findById(@PathVariable Integer fid);


    @GetMapping("resfood/findAll")
//    @ApiOperation(value = "查询所有菜品")
    public Map<String,Object> findAll();


    @RequestMapping("resfood/findByPage")
    public Map<String,Object> findByPage(@RequestParam int pageno,@RequestParam int pagesize,@RequestParam(required = false)  String sortby,@RequestParam (required = false) String sort) ;

}

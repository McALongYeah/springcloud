package com.yc2;

import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

public interface ResfoodApi {
    // 传入的参数后可以拼接成url: https://api.github.com/repos/OpenFeign/feign/contributors
    @RequestLine("GET /resfood/findAll") //feign 提供的规范
//    @RequestMapping({value="/repos/{owner/{repo}/contributor,method={GET})"}
//    List<Contributor> contributors(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
    public jsonModel findAll();

}

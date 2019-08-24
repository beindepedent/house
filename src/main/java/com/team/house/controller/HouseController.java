package com.team.house.controller;

import com.github.pagehelper.PageInfo;
import com.team.house.entity.House;
import com.team.house.service.HouseService;
import com.team.house.util.HouseCondition;
import com.team.house.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller(value="HouseController2")//这里的名字可以随便写,与前端穿过来的页面的路径没有关系，主要是为了避免在spring框架中的组件配置类名重名的问题
@RequestMapping("/admin")
public class HouseController {
    @Autowired
    private HouseService houseService;
    //查询未审核出租房信息
    @RequestMapping("/getNoPassHouse")
    @ResponseBody
    public Map<String,Object> getNoPassHouse(Page page,House house){
        //state为0的时候就是表示未审核，若在方法中带入一个传入参数，则该方法可以用作查询审核通过的，和审核没通过
        PageInfo<House> pageInfo = houseService.getHouseByIsPass(page,0);
        Map<String,Object> map=new HashMap<>();
        //返回的以下数据就是因为easyui需要，
        map.put("total",pageInfo.getTotal());//这里的名字不能改
        map.put("rows",pageInfo.getList());
        return map;

    }
    //查询未审核出租房信息
    @RequestMapping("/getYesPassHouse")
    @ResponseBody
    public Map<String,Object> getYesPassHouse(Page page){
        //state为0的时候就是表示未审核，若在方法中带入一个传入参数，则该方法可以用作查询审核通过的，和审核没通过
        PageInfo<House> pageInfo = houseService.getHouseByIsPass(page,1);
        Map<String,Object> map=new HashMap<>();
        //返回的以下数据就是因为easyui需要，
        map.put("total",pageInfo.getTotal());//这里的名字不能改
        map.put("rows",pageInfo.getList());
        return map;

    }
    //修改审核出租房状态：通过审核
    @RequestMapping("/alterHouseState")
    @ResponseBody
    public Map<String,Object> alterHouseState(String id){
        //state为0的时候就是表示未审核，若在方法中带入一个传入参数，则该方法可以用作查询审核通过的，和审核没通过
        int temp = houseService.alterHouseState(id, 1);
        Map<String,Object> map=new HashMap<>();
        map.put("result",1);
        return map;

    }
    //修改审核出租房状态：通过审核
    @RequestMapping("/alterNoHouseState")
    @ResponseBody
    public Map<String,Object> alterNoHouseState(String id){
        //state为0的时候就是表示未审核，若在方法中带入一个传入参数，则该方法可以用作查询审核通过的，和审核没通过
        int temp = houseService.alterHouseState(id, 0);
        Map<String,Object> map=new HashMap<>();
        map.put("result",1);
        return map;
    }
//    @RequestMapping("getBroswerHouse")   //condition包含查询条件及分页的相关属性(page,rows)
//    public String  getBroswerHouse(HouseCondition condition, Model model)throws  Exception{
//        //调用业务层
//        PageInfo<House> pageInfo=houseService.getBorswerHouse(condition);
//        //填充数据，控制层的作用就是取调存转数据，因为返回的是页面，所以没有responseBody,如果返回的是客户端，则返回的是数据接口
//        model.addAttribute("pageInfo",pageInfo);
//        //回显查询条件
//        model.addAttribute("condition",condition);
//        return  "house";
//        }
    /**
     *显示浏览所有的出租房信息
     * 传页码
     * @return
     */
    @RequestMapping("getBroswerHouse")   //condition包含查询条件及分页的相关属性(page,rows)
    @ResponseBody
    public Map<String,Object>  getBroswerHouse(HouseCondition condition){
        System.out.println("===============测试："+condition.toString());
        //调用业务层
        PageInfo<House> pageInfo=houseService.getBorswerHouse(condition);
        Map<String,Object> map=new HashMap<>();
       /* //填充数据，控制层的作用就是取调存转数据，因为返回的是页面，所以没有responseBody,如果返回的是客户端，则返回的是数据接口
        model.addAttribute("pageInfo",pageInfo);
        //回显查询条件
        model.addAttribute("condition",condition);*/
        map.put("total",pageInfo.getTotal());//这里的名字不能改
        map.put("rows",pageInfo.getList());
        return  map;
    }
}

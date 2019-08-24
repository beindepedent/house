package com.team.house.controller;

import com.github.pagehelper.PageInfo;
import com.team.house.entity.District;
import com.team.house.service.DistrictService;
import com.team.house.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")//表示后所有的控制器请求都在/admin目录下
public class DistrictController {
    @Autowired
    private DistrictService service;
    @RequestMapping("/getDistrict")
    @ResponseBody//获取所有的区域参数
    public List<District> getDistrict(){
        List<District> list =service.getAllDistrict();
        return list;
    }
    @RequestMapping("/getDistrictByPage")
    @ResponseBody//获取分页的区域参数，并将分页的区域列表参数以map集合的形式返回页面
    public HashMap<String,Object> getDistrictByPage(int page,int rows){
        PageInfo<District> pageInfo=service.getDistrictByPage(page,rows);
        HashMap<String,Object> map=new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }
    @RequestMapping("/getDistrictByPageBean")
    @ResponseBody//用page
    public HashMap<String,Object> getDistrictByPageBean(Page page){
        PageInfo<District> pageInfo=service.getDistrictByPageBean(page);
        HashMap<String,Object> map=new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }
    @RequestMapping("/addDistrict")
    @ResponseBody
    public String addDistrict(District district){
        int temp=-1;
        try{
            //调用业务实现添加
            temp = service.addDistrict(district);
        }catch (Exception e){
            e.printStackTrace();//打印到堆栈，都会选择记录日志
        }
        //传统实现；跳转到视图
        //前后端分离，返回数据
        return "{\"result\":"+temp+"}";
    }
    @RequestMapping("/getOneDistrict")
    @ResponseBody
    public District getDistrict(Integer id){
        System.out.println("================="+id);
        return service.getDitrictById(id);
    }
    @RequestMapping("/updateDistrict")
    @ResponseBody
    public String updateDistrict(District district){
        System.out.println("=========修改区域数据"+district.toString());
        int temp=-1;
        try{
            //调用业务实现修改
            temp = service.updateDistrict(district);
        }catch (Exception e){
            e.printStackTrace();//打印到堆栈，都会选择记录日志
        }
        //传统实现；跳转到视图
        //前后端分离，返回数据
        System.out.println(temp);
        return "{\"result\":"+temp+"}";
    }

    @RequestMapping("/deleteDistrictById")
    @ResponseBody//将该方法的返回值以data的形式返回给返回函数的参数data
    public String deleteBySingleId(Integer id){
        //调用事务的业务的时候加try catch，调用者加try catch
        int temp=-1;
        try{
            //调用业务实现删除
            temp=service.deleteBySingleId(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("============"+"{\"result\":"+temp+"}"+"==========");
        return "{\"result\":"+temp+"}";
    }

    //批量删除区域
    //deleteMoreDistrict?id=1&id=2&id=3 public String delDistrict(Integer []id)
    @RequestMapping("/deleteMoreDistrict")//"1,2,3"
    @ResponseBody
    public String deleteMoreDistrict(String ids){
        //将字符串转化为数组
        String arys[]=ids.split(",");

        Integer [] is=new Integer[arys.length];
        for(int i=0;i<arys.length;i++){
            is[i]=new Integer(arys[i]);
        }
        //调用业务
        int temp=service.deleteMoreDistrict(is);
        return "{\"result\":"+temp+"}";
    }

}


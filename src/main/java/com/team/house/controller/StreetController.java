package com.team.house.controller;

import com.github.pagehelper.PageInfo;

import com.team.house.entity.Street;
import com.team.house.service.StreetService;
import com.team.house.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class StreetController {
    @Autowired
    private StreetService streetService;

    //打开对应区域显示区域中街道的对话框
    @RequestMapping("/getStreetPageByDid")
    @ResponseBody
    public Map<String,Object> getStreetByDid(Integer did, Page page){
        PageInfo<Street> pageInfo=streetService.getStreetByDistrict(did,page);
        //返回结果
        HashMap<String,Object> map=new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

    //区域中对话框异步更新街道更改数据
    @RequestMapping("/updateStreetInDistrict")
    @ResponseBody
    public String updateStreetInDistrict(Street street){
        int temp=-1;
        System.out.println(street.toString());
        temp=streetService.updateStreetSelective(street);
        System.out.println(temp);
        return "{\"result\":"+temp+"}";
    }
    /*================================================================*/
    //绑定数据并在街道管理页面显示
    @RequestMapping("/getStreetByPageBean")
    @ResponseBody//用page
    public HashMap<String,Object> getStreetByPageBean(Page page){
        PageInfo<Street> pageInfo=streetService.getStreetByPageBean(page);
        HashMap<String,Object> map=new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }
    @RequestMapping("/addStreet")
    @ResponseBody
    public String addStreet(Street street){
        System.out.println("==================="+street.toString());
        int temp=-1;
        try{
            //调用业务实现添加
            temp = streetService.addStreet(street);
        }catch (Exception e){
            e.printStackTrace();//打印到堆栈，都会选择记录日志
        }
        //传统实现；跳转到视图
        //前后端分离，返回数据
        return "{\"result\":"+temp+"}";
    }
    @RequestMapping("/getOneStreet")
    @ResponseBody
    public Street getStreet(Integer id){
        System.out.println("================="+id);
        return streetService.getStreetById(id);
    }
    @RequestMapping("/updateStreet")
    @ResponseBody
    public String updateStreet(Street street){
        int temp=-1;
        try{
            //调用业务实现修改
            temp = streetService.updateStreet(street);
        }catch (Exception e){
            e.printStackTrace();//打印到堆栈，都会选择记录日志
        }
        //传统实现；跳转到视图
        //前后端分离，返回数据
        System.out.println(temp);
        return "{\"result\":"+temp+"}";
    }

    @RequestMapping("/deleteStreetById")
    @ResponseBody//将该方法的返回值以data的形式返回给返回函数的参数data
    public String deleteBySingleId(Integer id){
        //调用事务的业务的时候加try catch，调用者加try catch
        int temp=-1;
        try{
            //调用业务实现删除
            temp=streetService.deleteBySingleId(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("============"+"{\"result\":"+temp+"}"+"==========");
        return "{\"result\":"+temp+"}";
    }

    //批量删除区域
    //deleteMoreDistrict?id=1&id=2&id=3 public String delDistrict(Integer []id)
    @RequestMapping("/deleteMoreStreet")//"1,2,3"
    @ResponseBody
    public String deleteMoreStreet(String ids){
        //将字符串转化为数组
        String arys[]=ids.split(",");

        Integer [] is=new Integer[arys.length];
        for(int i=0;i<arys.length;i++){
            is[i]=new Integer(arys[i]);
        }
        //调用业务
        int temp=streetService.deleteMoreStreet(is);
        return "{\"result\":"+temp+"}";
    }

}


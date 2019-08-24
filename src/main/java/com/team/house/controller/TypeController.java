package com.team.house.controller;

import com.github.pagehelper.PageInfo;
import com.team.house.entity.Type;
import com.team.house.service.TypeService;
import com.team.house.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")//表示后所有的控制器请求都在/admin目录下
public class TypeController {
    @Autowired
    private TypeService service;
    @RequestMapping("/getType")
    @ResponseBody
    public List<Type> getType(int page,int rows){
        List<Type> list =service.getAllType();
        return list;
    }
    @RequestMapping("/getTypeByPage")
    @ResponseBody
    public HashMap<String,Object> getTypeByPage(int page,int rows){
        PageInfo<Type> pageInfo=service.getTypeByPage(page,rows);
        HashMap<String,Object> map=new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }
    @RequestMapping("/getTypeByPageBean")
    @ResponseBody
    public HashMap<String,Object> getTypeByPageBean(Page page){
        PageInfo<Type> pageInfo=service.getTypeByPageBean(page);
        HashMap<String,Object> map=new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }
    @RequestMapping("/addType")
    @ResponseBody
    public String addType(Type Type){
        int temp=-1;
        try{
            //调用业务实现添加
            temp = service.addType(Type);
        }catch (Exception e){
            e.printStackTrace();//打印到堆栈，都会选择记录日志
        }
        //传统实现；跳转到视图
        //前后端分离，返回数据
        return "{\"result\":"+temp+"}";
    }
    @RequestMapping("/getOneType")
    @ResponseBody
    public Type getType(Integer id){
        return service.getDitrictById(id);
    }
    @RequestMapping("/updateType")
    @ResponseBody
    public String updateType(Type Type){
        int temp=-1;
        try{
            //调用业务实现修改
            temp = service.updateType(Type);
        }catch (Exception e){
            e.printStackTrace();//打印到堆栈，都会选择记录日志
        }
        //传统实现；跳转到视图
        //前后端分离，返回数据
        System.out.println(temp);
        return "{\"result\":"+temp+"}";
    }

    @RequestMapping("/deleteTypeById")
    @ResponseBody//将该方法的返回值以data的形式返回给返回函数的参数data
    public String deleteBySingleId(Integer id){//1删除成功，0含有出租房，-1，删除操作失败
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
    //deleteMoreType?id=1&id=2&id=3 public String delType(Integer []id)
    @RequestMapping("/deleteMoreType")//"1,2,3"
    @ResponseBody
    public String deleteMoreType(String ids){
        //将字符串转化为数组
        String arys[]=ids.split(",");

        Integer [] is=new Integer[arys.length];
        for(int i=0;i<arys.length;i++){
            is[i]=new Integer(arys[i]);
        }
        //调用业务
        int temp=service.deleteMoreType(is);
        return "{\"result\":"+temp+"}";
    }
}


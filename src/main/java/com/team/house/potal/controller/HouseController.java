package com.team.house.potal.controller;

import com.github.pagehelper.PageInfo;
import com.team.house.entity.House;
import com.team.house.entity.Users;
import com.team.house.service.HouseService;
import com.team.house.util.HouseCondition;
import com.team.house.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/page")
public class HouseController {
    @Autowired
    private HouseService houseService;

    @RequestMapping("/addHouse")
    private String addHouse(House house, @RequestParam(value="pfile",required = false) CommonsMultipartFile pfile, HttpSession session, Model model){
//  /*      一个CommonsMultipartFile类对象就代表一个表单文件域，一张图片,而RequestParam强制指定一个文件域pfile就与前端的name为pfile对应
//        *//*
//        * 上传图片的的步骤：
//        * 第一步在pom中导入文件上传的依赖commons-fileupload，
//        * 第二步;在springmvc中添加文件上传转化器支持CommonsMultipartResolver
//        * 第三步：实现文件上传的代码
//        * 为什么可以把上传的文件堆到CommonsMultipartFile，这是因为这背后的拦截器，只要表单中有文件上传，
//        * 首先第一个启动的是文件上传转换器，就会把上传的文件封装到自己写的控制器中的CommonsMultipartFile对象中
//        *注意：1）文件上传的表单必需以post方式进行提交，且在表单中添加enctype="multipart/form-data"属性，因为文件上传是字节流的形式传输
//        *      2）文件上传必须要保证图片的唯一性
//        */

      //获取上传文件的信息
//       /* System.out.println("文件名："+pfile.getOriginalFilename());
//        System.out.println("文件大小："+pfile.getSize());
//        System.out.println("文件类型："+pfile.getContentType());*/
//        System.out.println("文件："+pfile.getBytes());文件的所有的字节
        try {
            //第一步上传图片
            String path="d:\\images\\";//当加入文件服务器的时候，改变的是这里的位置，及服务器存放图片的位置
            //生成唯一文件名
            String oldName=pfile.getOriginalFilename();
            String expname=oldName.substring(oldName.lastIndexOf("."));
            String filename=System.currentTimeMillis()+expname;
            File file=new File(path+filename);
            pfile.transferTo(file);//上传，保存
            //第二步保存信息到数据库
            //设置主键
            house.setId(System.currentTimeMillis()+"");
            //设置发布出租房的用户
            Users users = (Users)session.getAttribute("userInfo");
            System.out.println("=========users测试类=========="+users.toString());
            house.setUserId(users.getId());
            //当项目分工进行的时候。别人前台登录的项目还没有做，可以通过替换法，即给一个默认的用户id实现
            //设置图片
            house.setPath(filename);
            houseService.addHouse(house);
            return "redirect:getHouse";//跳转到管理页
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("info","上传文件失败...");
        }
        return "redirect:getHouse";
    }

    //查询用户出租房，查询用户发布的出租房
    @RequestMapping("/getHouse")
    public String getHouse(Page page,HttpSession session,Model model){//page只为接受页码，用户id是通过作用域获取
        //获取用户id
        Users user = (Users)session.getAttribute("userInfo");
        //调用业务查询出对应用户带分页的出租房信息
        System.out.println("========修改前获取用户的测试：=========="+user.toString());
        PageInfo<House> pageInfo = houseService.getHouseByUser(page,user.getId());
        //查找本机内网的ip地址，并将ip地址返回给页面
//      /*  TestGetIP testGetIP = new TestGetIP();
//        String ip = testGetIP.getIP();
//        model.addAttribute("ip",ip);
//
//        for(House house:pageInfo.getList()){
//            String path;
//            path=house.getPath();
//            System.out.println("=======测试========path:"+path);
//        }*/

        //将结果填充至model
        model.addAttribute("pageInfo",pageInfo);
        return "guanli";
    }

    //显示单条出租房信息,修改租房信息前的重写数据查询
    @RequestMapping("/getSingleHouse")
    public String getSingleHouse(String id,Model model){//model的作用就是将控制器的数据传到页面
        //获取出租房信息
        House house = houseService.getHouse(id);
        model.addAttribute("h",house);
        return "alter";
    }
    //更新单条出租房信息
    @RequestMapping("/updateHouse")//传入的参数：要删除的图片名，房屋实体，上传图片的文件上传工具类
    public String updateHouse(String delimage,House house,@RequestParam("pfile") CommonsMultipartFile pfile){  //page只为接收页码
        try {
            //判断用户是否选择文件
//            System.out.println("==============测试房屋的信息：========="+house.toString()+"==============测试信息：========="+delimage);
            String oldName=pfile.getOriginalFilename();
//            System.out.println("==============测试房屋的信息：="+oldName+"========");
            if(oldName.equals(""))  //没有选择文件
            {
                houseService.updateHouse(house);
            }else
            {
                //上传
                //第一步上传图片
                String path="d:\\images\\";  //存放文件的位置
                //生成唯一文件名
                String expname=oldName.substring(oldName.lastIndexOf("."));
                String filename=System.currentTimeMillis()+expname;
                File file=new File(path+filename);
                pfile.transferTo(file);  //上传，保存
                //更新数据库
                //设置更新图片路径
                house.setPath(filename);
                System.out.println("==============测试房屋的信息：========="+house.toString()+"==============测试信息：========="+delimage);
                houseService.updateHouse(house);
                //删除旧图
                File delfile=new File(path+delimage);
                delfile.delete();
            }
            return "redirect:getHouse";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "error";
    }
    //删除出租房
    @RequestMapping("/delHouse")
    public String delHouse(String id,Model model){//model的作用就是将控制器的数据传到页面
        //state为isdel,为1的时候就是表示是删除，
        int temp = houseService.delHouseState(id,1);
        if(temp>0)
            return "redirect:getHouse";
        else
            return "error";
    }
    //查询未审核出租房信息
    @RequestMapping("/getNoPassHouse")
    @ResponseBody
    public Map<String,Object> getNoPassHouse(Page page){
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
    @RequestMapping("getBroswerHouse")   //condition包含查询条件及分页的相关属性(page,rows)
    public String  getBroswerHouse(HouseCondition condition, Model model)throws  Exception{
        //调用业务层
        PageInfo<House> pageInfo=houseService.getBorswerHouse(condition);
        //填充数据，控制层的作用就是取调存转数据，因为返回的是页面，所以没有responseBody,如果返回的是客户端，则返回的是数据接口
        model.addAttribute("pageInfo",pageInfo);
        //回显查询条件
        model.addAttribute("condition",condition);
        return  "list";
    }
    //显示单条出租房详细信息
    @RequestMapping("/getSingleHouse2")
    public String getSingleHouse2(String id,Model model){//model的作用就是将控制器的数据传到页面
        //获取出租房信息
        House house = houseService.getHouse(id);
        model.addAttribute("h",house);
        return "details";
    }
}

package com.team.house.potal.controller;

import com.team.house.entity.Type;
import com.team.house.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller(value = "typeController2")//类名相同产生的bean对象在spring定义的组件名称是相同的，故有两个相同的类名，在控制器中的controller(valuue="")
@RequestMapping("/page")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping("/getAllType")
    @ResponseBody
    public List<Type> getAllType(){//用作用域跳页面，前端页面就用jstl和el表达式，如果返回的异步数据，前端页面就用js
        return typeService.getAllType();
    }
}

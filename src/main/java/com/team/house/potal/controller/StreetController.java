package com.team.house.potal.controller;

import com.github.pagehelper.PageInfo;
import com.team.house.entity.Street;
import com.team.house.service.StreetService;
import com.team.house.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller(value = "streetController2")
@RequestMapping("/page")
public class StreetController {
    @Autowired
    private StreetService streetService;

    //打开对应区域显示区域中街道的对话框
    @RequestMapping("/getStreetPageByDid")
    @ResponseBody
    public List<Street> getStreetByDid(Integer did){
        return streetService.getStreetByDid(did);
    }


}


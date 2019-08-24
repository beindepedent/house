package com.team.house.service.impl;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.team.house.entity.House;
import com.team.house.mapper.HouseMapper;
import com.team.house.service.HouseService;
import com.team.house.util.HouseCondition;
import com.team.house.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseMapper houseMapper;
    @Override
    public int addHouse(House house) {
        return houseMapper.insertSelective(house);
    }

    @Override
    public House getHouse(String id) {//修改前查询单单条数据回写
//        return houseMapper.selectByPrimaryKey(id);
        return houseMapper.getHouseById(id);//报错，还未解决！
    }

    @Override
    public int updateHouse(House house) {
        return houseMapper.updateByPrimaryKeySelective(house);
    }

    @Override
    public int delHouseState(String id, Integer state) {
        House house=new House();
        //设置id
        house.setId(id);
        //设置状态
        house.setIsdel(state);
        return houseMapper.updateByPrimaryKeySelective(house);
    }

    @Override
    public PageInfo<House> getHouseByIsPass(Page page, Integer state) {
        //开启分页
        PageHelper.startPage(page.getPage(),page.getRows());
        //查询所有
        List<House> list = houseMapper.getHouseByIsPass(state);
        return new PageInfo<House>(list) ;
    }

    @Override
    public int alterHouseState(String id, Integer state) {
        House house=new House();
        //设置id
        house.setId(id);
        //设置审核状态
        house.setIspass(state);
        return houseMapper.updateByPrimaryKeySelective(house);
    }

    @Override//房屋管理中的条件分页查询
    public PageInfo<House> getBorswerHouse(HouseCondition condition) {
        //启动分页支持
        PageHelper.startPage(condition.getPage(),condition.getRows());
        List<House> list =houseMapper.getBorswerHouse(condition);
        PageInfo<House> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    @Override//查询某用户的发布的出租房，并将出租房分页
    public PageInfo<House> getHouseByUser(Page page, Integer userid) {
        //给page的rows属性设置默认值：后期可能不传页大小
        PageHelper.startPage(page.getPage(),page.getRows());
        List<House> list = houseMapper.getHouseByUser(userid);
        return new PageInfo<House>(list);
    }



}

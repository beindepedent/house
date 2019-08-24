package com.team.house.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.team.house.entity.Street;
import com.team.house.entity.StreetExample;
import com.team.house.mapper.HouseMapper;
import com.team.house.mapper.StreetMapper;
import com.team.house.service.StreetService;
import com.team.house.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class StreetServiceImpl implements StreetService {

    @Autowired
    private StreetMapper streetMapper;
    @Autowired
    private HouseMapper houseMapper;
    @Override
    public PageInfo<Street> getStreetByDistrict(Integer did, Page page) {
        PageHelper.startPage(page.getPage(),page.getRows());
        //创建查询条件
        StreetExample streetExample=new StreetExample();
        StreetExample.Criteria criteria = streetExample.createCriteria();
        criteria.andDistrictIdEqualTo(did);//封装查询
        List<Street> list = streetMapper.selectByExample(streetExample);
        return new PageInfo<Street>(list);
    }

    @Override//区域中更新街道信息
    public int updateStreetSelective(Street street) {
        return streetMapper.updateByPrimaryKeySelective(street);
    }

    @Override//查询分页街道信息
    public PageInfo<Street> getStreetByPageBean(Page page) {
        //1、开启分页
        PageHelper.startPage(page.getPage(),page.getRows());
        //2、查询所有 封装条件实体类
        StreetExample example = new StreetExample();
        List<Street> list = streetMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int addStreet(Street street) {
        return streetMapper.insertSelective(street);
    }

    @Override
    public Street getStreetById(Integer id) {
        return streetMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateStreet(Street street) {
        return streetMapper.updateByPrimaryKeySelective(street);
    }

    @Override//删除单条街道，需要连带街道内的房屋一并删除
    @Transactional
    public int deleteBySingleId(Integer id) {
        houseMapper.deleteHouseByStreetId(id);
        streetMapper.deleteByPrimaryKey(id);
        return 1;
    }

    @Override
    public int deleteMoreStreet(Integer[] is) {
        return streetMapper.deleteMoreStreet(is);
    }

    @Override
    public List<Street> getStreetByDid(Integer did) {
        //查询所有
        StreetExample example = new StreetExample();
        StreetExample.Criteria criteria = example.createCriteria();
        criteria.andDistrictIdEqualTo(did);//封装查询
        List<Street> list = streetMapper.selectByExample(example);
        return list;
    }


}

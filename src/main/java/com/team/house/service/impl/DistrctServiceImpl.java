package com.team.house.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.team.house.entity.District;
import com.team.house.entity.DistrictExample;
import com.team.house.mapper.DistrictMapper;
import com.team.house.mapper.StreetMapper;
import com.team.house.service.DistrictService;
import com.team.house.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//事务通常不用在查询业务，常用于增删改对数据库有改动的业务，查询业务不需要设置事务的蜀绣那个，设置为挂起状态，
public class DistrctServiceImpl implements DistrictService {
    @Autowired
    private DistrictMapper districtMapper;
    @Autowired
    private StreetMapper streetMapper;
    @Override//查询所有区域
    @Transactional(propagation = Propagation.SUPPORTS)//事务的传播行为：挂起事务，不基于事务执行
    public List<District> getAllDistrict() {
        //封装条件实体类
        DistrictExample districtExample=new DistrictExample();
        List<District> list = districtMapper.selectByExample(districtExample);
        return list;
    }

    @Override//查询带分页的所有区域
    @Transactional(propagation = Propagation.SUPPORTS)//事务的传播行为：挂起事务，不基于事务执行
    public PageInfo<District> getDistrictByPage(int page, int pagesize) {
        //1、开启分页
        PageHelper.startPage(page,pagesize);//传页码，页大小
        //2、查询所有信息 只是当前页的结果
        DistrictExample districtExample=new DistrictExample();
        List<District> list=districtMapper.selectByExample(districtExample);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override//查询带分页的所有区域,传入的参数为分页的工具类
    @Transactional(propagation = Propagation.SUPPORTS)//事务的传播行为：挂起事务，不基于事务执行
    public PageInfo<District> getDistrictByPageBean(Page page) {
        //1、开启分页
        PageHelper.startPage(page.getPage(),page.getRows());
        //2、查询所有 封装条件实体类
        DistrictExample example = new DistrictExample();
        List<District> list = districtMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override//添加业务
    public int addDistrict(District district) {
        return districtMapper.insertSelective(district);
    }

    @Override//查询单条区域业务
    @Transactional(propagation = Propagation.SUPPORTS)//事务的传播行为：挂起事务，不基于事务执行
    public District getDitrictById(Integer id) {
        return districtMapper.selectByPrimaryKey(id);
    }

    @Override//修改单条区域业务
    public int updateDistrict(District district) {
        return districtMapper.updateByPrimaryKeySelective(district);
    }

    @Override//删除单条区域的业务,删除主外键的时候利用事务的特性，测试事务的特性通常操作是将两个操作中增加一个异常
    @Transactional
    public int deleteBySingleId(Integer id) {
        //这里面的try catch不需要写，因为业务方法上添加事务支持的同时
        //在实现业务代码上添加了try catch捕获异常的时候，事务的特性就会失效
//        try{
            //要求删除区域的同时，删除街道，具有主外键关系的删除操作步骤：先删外键，再删主键
            //1、删除区域的下的街道  DELETE FROM street WHERE district_id=id
            streetMapper.deleteStreetByDistrictId(id);
            /*
            模拟异常中断删除的事务
            int i=1;int j=0;
            i=i/j;*/
            //2、删除对应区域
            districtMapper.deleteByPrimaryKey(id);
//        } catch (Exception e){
//            return -1;
//        }
        return 1;
    }

    @Override
    public int deleteMoreDistrict(Integer[] is) {
        return districtMapper.deleteMoreDistrict(is);
    }
}

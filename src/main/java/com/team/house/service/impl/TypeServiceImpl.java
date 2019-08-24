package com.team.house.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.team.house.entity.Type;
import com.team.house.entity.TypeExample;
import com.team.house.mapper.HouseMapper;
import com.team.house.mapper.TypeMapper;
import com.team.house.service.TypeService;
import com.team.house.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//事务通常不用在查询业务，常用于增删改对数据库有改动的业务，查询业务不需要设置事务的蜀绣那个，设置为挂起状态，
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private HouseMapper houseMapper;
    @Override//查询所有类型
    @Transactional(propagation = Propagation.SUPPORTS)//事务的传播行为：挂起事务，不基于事务执行
    public List<Type> getAllType() {
        //封装条件实体类
        TypeExample TypeExample=new TypeExample();
        List<Type> list = typeMapper.selectByExample(TypeExample);
        return list;
    }

    @Override//查询带分页的所有类型
    @Transactional(propagation = Propagation.SUPPORTS)//事务的传播行为：挂起事务，不基于事务执行
    public PageInfo<Type> getTypeByPage(int page, int pagesize) {
        //1、开启分页
        PageHelper.startPage(page,pagesize);//传页码，页大小
        //2、查询所有信息 只是当前页的结果
        TypeExample TypeExample=new TypeExample();
        List<Type> list=typeMapper.selectByExample(TypeExample);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override//查询带分页的所有类型,传入的参数为分页的工具类
    @Transactional(propagation = Propagation.SUPPORTS)//事务的传播行为：挂起事务，不基于事务执行
    public PageInfo<Type> getTypeByPageBean(Page page) {
        //1、开启分页
        PageHelper.startPage(page.getPage(),page.getRows());
        //2、查询所有 封装条件实体类
        TypeExample example = new TypeExample();
        List<Type> list = typeMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override//添加业务
    public int addType(Type Type) {
        return typeMapper.insertSelective(Type);
    }

    @Override//查询单条类型业务
    @Transactional(propagation = Propagation.SUPPORTS)//事务的传播行为：挂起事务，不基于事务执行
    public Type getDitrictById(Integer id) {
        return typeMapper.selectByPrimaryKey(id);
    }

    @Override//修改单条类型业务
    public int updateType(Type Type) {
        return typeMapper.updateByPrimaryKeySelective(Type);
    }

    @Override//删除单条类型的业务,删除主外键的时候利用事务的特性，测试事务的特性通常操作是将两个操作中增加一个异常
    @Transactional
    public int deleteBySingleId(Integer id) {//1删除成功，0含有出租房，-1，删除操作失败
        //？判断该类型下有没有房屋，如果有房屋，则删除失败，不予删除，否则删除
        //select count(*) from house where type=id
        int n=houseMapper.selectHouseByTypeId(id);
        System.out.println("================="+n);
        if(n!=0)
            return 0;
        else
            return typeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteMoreType(Integer[] is) {
        return typeMapper.deleteMoreType(is);
    }
}

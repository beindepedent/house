package com.team.house.service;

import com.github.pagehelper.PageInfo;
import com.team.house.entity.District;
import com.team.house.entity.Street;
import com.team.house.util.Page;

import java.util.List;

public interface StreetService {
    //通过区域查询对应的街道
    PageInfo<Street> getStreetByDistrict(Integer did, Page page);

    //区域中异步动态修改街道信息
    int updateStreetSelective(Street street);

    /**
     * 查询带分页的所有区域,传入的参数为分页的工具类
     * @param page
     * @return 街道实体集合
     */
    PageInfo<Street> getStreetByPageBean(Page page);

    int addStreet(Street street);
    /**
     * 添加业务
     * @param id
     * @return 区域实体对象
     */
    Street getStreetById(Integer id);

    /**
     * 修改业务
     * @param street
     * @return 受影响的行数
     */
    int updateStreet(Street street);

    /**
     * 删除业务
     * @param id
     * @return 受影响的行数
     */
    int deleteBySingleId(Integer id);

    /**
     * 批量删除业务
     * @param is
     * @return 受影响的行数
     */
    int deleteMoreStreet(Integer[] is);

    /**
     * 通过区域传对应街道
     * @param did 区域编号
     * @return 受影响的行数
     */
    public List<Street> getStreetByDid(Integer did);
}

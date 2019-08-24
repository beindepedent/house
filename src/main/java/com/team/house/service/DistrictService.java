package com.team.house.service;

import com.github.pagehelper.PageInfo;
import com.team.house.entity.District;
import com.team.house.util.Page;

import java.util.List;

public interface DistrictService {
    /**
     * 查询所有区域
     * @return 区域实体集合
     */
    List<District> getAllDistrict();

    /**
     * 查询带分页的所有区域
     * @return 区域实体集合
     */
    PageInfo<District> getDistrictByPage(int page, int rows);

    /**
     * 查询带分页的所有区域,传入的参数为分页的工具类
     * @param page
     * @return 区域实体集合
     */
    PageInfo<District> getDistrictByPageBean(Page page);
    /**
     * 添加业务
     * @param district
     * @return 受影响的行数
     */
    int addDistrict(District district);
    /**
     * 添加业务
     * @param id
     * @return 区域实体对象
     */
    District getDitrictById(Integer id);

    /**
     * 修改业务
     * @param district
     * @return 受影响的行数
     */
    int updateDistrict(District district);

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
    int deleteMoreDistrict(Integer[] is);
}

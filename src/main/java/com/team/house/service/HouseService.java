package com.team.house.service;

import com.github.pagehelper.PageInfo;
import com.team.house.entity.House;
import com.team.house.util.HouseCondition;
import com.team.house.util.Page;

public interface HouseService {
    //发布出租房
    public int addHouse(House house);

    /**
     * 查询出租房的单条信息
     * @param page 分页
     * @param userid 用户编号
     * @return house
     */
    PageInfo<House> getHouseByUser(Page page, Integer userid);


    /**
     * 查询出租房的单条信息
     * @param id 用户编号
     * @return house
     */
    House getHouse(String id);

    int updateHouse(House house);

    /**
     * 修改出租房的状态
     * 删除出租房的状态 传1
     * 恢复出租房的状态 传0
     * @Param id 出租房编号
     * @Param state 状态信息
     * @return 影响行数
     */
    int delHouseState(String id,Integer state);
    /**
     * 通过审核状态查询出租房信息
     * 状态 传1 表示已审核
     * 状态 传0 表示未审核
     * @Param page 分页数据及相关信息
     * @Param state  状态信息
     * @return 影响行数
     */
   PageInfo<House> getHouseByIsPass(Page page,Integer state);
    /**
     * 审核出租房的状态
     * 删除出租房的状态 传1
     * 恢复出租房的状态 传0
     * @Param id 出租房编号
     * @Param state 状态信息
     * @return 影响行数
     */
    int alterHouseState(String id,Integer state);
    /**
     * 查询所有的出租房
     * @Param condition
     * @return house
     */
    PageInfo<House> getBorswerHouse(HouseCondition condition);
}

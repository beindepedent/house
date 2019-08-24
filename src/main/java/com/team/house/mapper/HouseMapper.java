package com.team.house.mapper;

import com.team.house.entity.House;
import com.team.house.entity.HouseExample;
import com.team.house.entity.Users;
import com.team.house.util.HouseCondition;

import java.util.List;

public interface HouseMapper {
    int deleteByPrimaryKey(String id);

    int insert(House record);

    int insertSelective(House record);

    List<House> selectByExample(HouseExample example);

    House selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);

    int deleteHouseByStreetId(Integer id);

    int selectHouseByTypeId(Integer id);
    //查询用户发布的出租房
    List<House> getHouseByUser(Integer userId);
    //查询出租房信息
    House getHouseById(String id);

    //查询所有未审核出租房信息,为修改出租房的状态信息做准备
    //审核状态为0表示未审核，审核状态为1，表示已审核
    /**
     * @return ispass
     * */
    List<House> getHouseByIsPass(Integer ispass);
    /*
    * 该方法关联的sql语句是SELECT
	h.id,title,d.name AS dname,s.name AS sname,t.name AS tname,
	floorage,contact,price,path
 FROM house AS h
	LEFT JOIN TYPE AS t ON h.type_id=t.id
	LEFT JOIN street AS s ON h.street_id=s.id
	LEFT JOIN district AS d ON s.district_id=d.id
      WHERE ispass=0 AND isdel=0
    * */

    /**
     * @param condition
     * @Return House
     */
    //注意：这里返回的房屋信息需要在前端页面进行分页，但是返回的list不能是pageInfo，
    // 因为用pageHelper进行分页，pageHelper作用的是业务层，分页是在业务里面实现的，
    //dao层永远返回的是查所有的集合
    List<House> getBorswerHouse(HouseCondition condition);
}
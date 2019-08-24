package com.team.house.mapper;

import com.team.house.entity.Type;
import com.team.house.entity.TypeExample;
import java.util.List;

public interface TypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    List<Type> selectByExample(TypeExample example);

    Type selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);
    /**
     * 批量删除类型
     * @param ids：1，2，3
     * @return 影响行数
     */
    int deleteMoreType(Integer [] ids);
}
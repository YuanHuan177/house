package com.babyjuan.house.repository;

import com.babyjuan.house.repository.entity.Community;
import com.babyjuan.house.repository.entity.CommunityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommunityMapper {
    int countByExample(CommunityExample example);

    int deleteByExample(CommunityExample example);

    int deleteByPrimaryKey(Long infoId);

    int insert(Community record);

    int insertSelective(Community record);

    List<Community> selectByExample(CommunityExample example);

    Community selectByPrimaryKey(Long infoId);

    int updateByExampleSelective(@Param("record") Community record, @Param("example") CommunityExample example);

    int updateByExample(@Param("record") Community record, @Param("example") CommunityExample example);

    int updateByPrimaryKeySelective(Community record);

    int updateByPrimaryKey(Community record);

    int updateStatus();
}
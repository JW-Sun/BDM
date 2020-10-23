package com.jw.bigwhalemonitor.mapper;

import com.jw.bigwhalemonitor.entity.ClusterUser;
import com.jw.bigwhalemonitor.entity.ClusterUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClusterUserMapper {
    int countByExample(ClusterUserExample example);

    int deleteByExample(ClusterUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(ClusterUser record);

    int insertSelective(ClusterUser record);

    List<ClusterUser> selectByExample(ClusterUserExample example);

    ClusterUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ClusterUser record, @Param("example") ClusterUserExample example);

    int updateByExample(@Param("record") ClusterUser record, @Param("example") ClusterUserExample example);

    int updateByPrimaryKeySelective(ClusterUser record);

    int updateByPrimaryKey(ClusterUser record);
}
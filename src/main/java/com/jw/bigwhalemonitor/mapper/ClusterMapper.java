package com.jw.bigwhalemonitor.mapper;

import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.ClusterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClusterMapper {
    int countByExample(ClusterExample example);

    int deleteByExample(ClusterExample example);

    int deleteByPrimaryKey(String id);

    int insert(Cluster record);

    int insertSelective(Cluster record);

    List<Cluster> selectByExample(ClusterExample example);

    Cluster selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Cluster record, @Param("example") ClusterExample example);

    int updateByExample(@Param("record") Cluster record, @Param("example") ClusterExample example);

    int updateByPrimaryKeySelective(Cluster record);

    int updateByPrimaryKey(Cluster record);
}
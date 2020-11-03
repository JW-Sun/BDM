package com.jw.bigwhalemonitor.mapper;

import com.jw.bigwhalemonitor.entity.ComputeFramework;
import com.jw.bigwhalemonitor.entity.ComputeFrameworkExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ComputeFrameworkMapper {
    int countByExample(ComputeFrameworkExample example);

    int deleteByExample(ComputeFrameworkExample example);

    int deleteByPrimaryKey(String id);

    int insert(ComputeFramework record);

    int insertSelective(ComputeFramework record);

    List<ComputeFramework> selectByExample(ComputeFrameworkExample example);

    ComputeFramework selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ComputeFramework record, @Param("example") ComputeFrameworkExample example);

    int updateByExample(@Param("record") ComputeFramework record, @Param("example") ComputeFrameworkExample example);

    int updateByPrimaryKeySelective(ComputeFramework record);

    int updateByPrimaryKey(ComputeFramework record);
}
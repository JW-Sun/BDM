package com.jw.bigwhalemonitor.mapper;

import com.jw.bigwhalemonitor.entity.YarnApp;
import com.jw.bigwhalemonitor.entity.YarnAppExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface YarnAppMapper {
    int countByExample(YarnAppExample example);

    int deleteByExample(YarnAppExample example);

    int deleteByPrimaryKey(String id);

    int insert(YarnApp record);

    int insertSelective(YarnApp record);

    List<YarnApp> selectByExample(YarnAppExample example);

    YarnApp selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") YarnApp record, @Param("example") YarnAppExample example);

    int updateByExample(@Param("record") YarnApp record, @Param("example") YarnAppExample example);

    int updateByPrimaryKeySelective(YarnApp record);

    int updateByPrimaryKey(YarnApp record);
}
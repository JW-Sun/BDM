package com.jw.bigwhalemonitor.mapper;

import com.jw.bigwhalemonitor.entity.AuthResource;
import com.jw.bigwhalemonitor.entity.AuthResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthResourceMapper {
    int countByExample(AuthResourceExample example);

    int deleteByExample(AuthResourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(AuthResource record);

    int insertSelective(AuthResource record);

    List<AuthResource> selectByExample(AuthResourceExample example);

    AuthResource selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AuthResource record, @Param("example") AuthResourceExample example);

    int updateByExample(@Param("record") AuthResource record, @Param("example") AuthResourceExample example);

    int updateByPrimaryKeySelective(AuthResource record);

    int updateByPrimaryKey(AuthResource record);
}
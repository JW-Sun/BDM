package com.jw.bigwhalemonitor.mapper;

import com.jw.bigwhalemonitor.entity.AuthRoleResource;
import com.jw.bigwhalemonitor.entity.AuthRoleResourceExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthRoleResourceMapper {
    int countByExample(AuthRoleResourceExample example);

    int deleteByExample(AuthRoleResourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(AuthRoleResource record);

    int insertSelective(AuthRoleResource record);

    List<AuthRoleResource> selectByExample(AuthRoleResourceExample example);

    AuthRoleResource selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AuthRoleResource record, @Param("example") AuthRoleResourceExample example);

    int updateByExample(@Param("record") AuthRoleResource record, @Param("example") AuthRoleResourceExample example);

    int updateByPrimaryKeySelective(AuthRoleResource record);

    int updateByPrimaryKey(AuthRoleResource record);
}
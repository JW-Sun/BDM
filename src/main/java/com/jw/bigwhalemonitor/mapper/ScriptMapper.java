package com.jw.bigwhalemonitor.mapper;

import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.entity.ScriptExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScriptMapper {
    int countByExample(ScriptExample example);

    int deleteByExample(ScriptExample example);

    int deleteByPrimaryKey(String id);

    int insert(Script record);

    int insertSelective(Script record);

    List<Script> selectByExampleWithBLOBs(ScriptExample example);

    List<Script> selectByExample(ScriptExample example);

    Script selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Script record, @Param("example") ScriptExample example);

    int updateByExampleWithBLOBs(@Param("record") Script record, @Param("example") ScriptExample example);

    int updateByExample(@Param("record") Script record, @Param("example") ScriptExample example);

    int updateByPrimaryKeySelective(Script record);

    int updateByPrimaryKeyWithBLOBs(Script record);

    int updateByPrimaryKey(Script record);
}
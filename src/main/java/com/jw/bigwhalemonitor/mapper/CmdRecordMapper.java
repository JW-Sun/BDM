package com.jw.bigwhalemonitor.mapper;

import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecordExample;
import com.jw.bigwhalemonitor.entity.CmdRecordWithBLOBs;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CmdRecordMapper {
    int countByExample(CmdRecordExample example);

    int deleteByExample(CmdRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmdRecordWithBLOBs record);

    int insertSelective(CmdRecordWithBLOBs record);

    List<CmdRecordWithBLOBs> selectByExampleWithBLOBs(CmdRecordExample example);

    List<CmdRecord> selectByExample(CmdRecordExample example);

    CmdRecordWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmdRecordWithBLOBs record, @Param("example") CmdRecordExample example);

    int updateByExampleWithBLOBs(@Param("record") CmdRecordWithBLOBs record, @Param("example") CmdRecordExample example);

    int updateByExample(@Param("record") CmdRecord record, @Param("example") CmdRecordExample example);

    int updateByPrimaryKeySelective(CmdRecordWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(CmdRecordWithBLOBs record);

    int updateByPrimaryKey(CmdRecord record);
}
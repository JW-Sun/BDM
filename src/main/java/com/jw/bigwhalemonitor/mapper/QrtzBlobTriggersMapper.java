package com.jw.bigwhalemonitor.mapper;

import com.jw.bigwhalemonitor.entity.QrtzBlobTriggers;
import com.jw.bigwhalemonitor.entity.QrtzBlobTriggersExample;
import com.jw.bigwhalemonitor.entity.QrtzBlobTriggersKey;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface QrtzBlobTriggersMapper {
    int countByExample(QrtzBlobTriggersExample example);

    int deleteByExample(QrtzBlobTriggersExample example);

    int deleteByPrimaryKey(QrtzBlobTriggersKey key);

    int insert(QrtzBlobTriggers record);

    int insertSelective(QrtzBlobTriggers record);

    List<QrtzBlobTriggers> selectByExampleWithBLOBs(QrtzBlobTriggersExample example);

    List<QrtzBlobTriggers> selectByExample(QrtzBlobTriggersExample example);

    QrtzBlobTriggers selectByPrimaryKey(QrtzBlobTriggersKey key);

    int updateByExampleSelective(@Param("record") QrtzBlobTriggers record, @Param("example") QrtzBlobTriggersExample example);

    int updateByExampleWithBLOBs(@Param("record") QrtzBlobTriggers record, @Param("example") QrtzBlobTriggersExample example);

    int updateByExample(@Param("record") QrtzBlobTriggers record, @Param("example") QrtzBlobTriggersExample example);

    int updateByPrimaryKeySelective(QrtzBlobTriggers record);

    int updateByPrimaryKeyWithBLOBs(QrtzBlobTriggers record);
}
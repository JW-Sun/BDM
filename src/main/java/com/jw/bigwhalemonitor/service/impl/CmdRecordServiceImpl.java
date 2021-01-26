package com.jw.bigwhalemonitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.dto.DtoCmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecordExample;
import com.jw.bigwhalemonitor.entity.CmdRecordWithBLOBs;
import com.jw.bigwhalemonitor.mapper.CmdRecordMapper;
import com.jw.bigwhalemonitor.service.CmdRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.swing.StringUIClientPropertyKey;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CmdRecordServiceImpl implements CmdRecordService {

    @Autowired
    private CmdRecordMapper cmdRecordMapper;

    @Override
    public PageInfo<CmdRecord> getByQuery(DtoCmdRecord req) {
        CmdRecordExample example = new CmdRecordExample();
        CmdRecordExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(req.getUid())) {
            criteria.andUidEqualTo(req.getUid());
        }
        if (StringUtils.isNotBlank(req.getAgentId())) {
            criteria.andAgentIdEqualTo(req.getAgentId());
        }
        if (StringUtils.isNotBlank(req.getClusterId())) {
            criteria.andClusterIdEqualTo(req.getClusterId());
        }
        if (req.getStatus() != null) {
            criteria.andStatusEqualTo(req.getStatus());
        }
        // 开始和结束时间
        if (req.getStartTime() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(req.getStartTime());
        }
        if (req.getEnd() != null) {
            criteria.andCreateTimeLessThanOrEqualTo(req.getEnd());
        }
        if (req.getScriptId() != null) {
            criteria.andScriptIdEqualTo(req.getScriptId());
        }
        if (req.getSchedulingId() != null) {
            criteria.andSchedulingIdEqualTo(req.getSchedulingId());
        }
        if (req.getId() != null) {
            criteria.andIdEqualTo(req.getId());
        }
        PageHelper.startPage(req.pageNo - 1, req.pageSize);
        List<CmdRecord> cmdRecords = cmdRecordMapper.selectByExample(example);
        PageInfo<CmdRecord> pageInfo = new PageInfo(cmdRecords);
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CmdRecordWithBLOBs save(CmdRecordWithBLOBs buildCmdRecord) {
        CmdRecordWithBLOBs cmdRecordWithBLOBs = (CmdRecordWithBLOBs) buildCmdRecord;
        if (cmdRecordWithBLOBs.getId() == null) {
            cmdRecordWithBLOBs.setId(UUID.randomUUID().toString());
            cmdRecordMapper.insert(cmdRecordWithBLOBs);
        } else {
            cmdRecordMapper.updateByPrimaryKeyWithBLOBs(cmdRecordWithBLOBs);
        }
        return cmdRecordWithBLOBs;
    }

    @Override
    public CmdRecordWithBLOBs getById(String cmdRecordId) {
        return cmdRecordMapper.selectByPrimaryKey(cmdRecordId);
    }

    @Override
    public List<CmdRecordWithBLOBs> getByQuery2(String id, String format) {
        CmdRecordExample example = new CmdRecordExample();
        CmdRecordExample.Criteria criteria = example.createCriteria();
        criteria.andSchedulingIdEqualTo(id);
        criteria.andSchedulingInstanceIdEqualTo(format);
        example.setOrderByClause("`createTime` ASC");
        List<CmdRecordWithBLOBs> cmdRecordWithBLOBs = cmdRecordMapper.selectByExampleWithBLOBs(example);
        return cmdRecordWithBLOBs;
    }

    @Override
    public Collection<CmdRecordWithBLOBs> getByQuery3() {
        // 获取状态是未开始或者正在运行
        CmdRecordExample example = new CmdRecordExample();
        CmdRecordExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(Constant.EXEC_STATUS_UNSTART);

        CmdRecordExample.Criteria c2 = example.createCriteria();
        c2.andStatusEqualTo(Constant.EXEC_STATUS_DOING);

        example.or(criteria);
        example.or(c2);
        return cmdRecordMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public Collection<CmdRecordWithBLOBs> getByJobFinalStatus(String s) {
        CmdRecordExample example = new CmdRecordExample();
        CmdRecordExample.Criteria c = example.createCriteria();
        c.andJobFinalStatusEqualTo(s);
        return cmdRecordMapper.selectByExampleWithBLOBs(example);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByCreateTime(Date format) {
        CmdRecordExample example = new CmdRecordExample();
        CmdRecordExample.Criteria c1 = example.createCriteria();
        c1.andCreateTimeLessThan(format);
        cmdRecordMapper.deleteByExample(example);
    }

    @Override
    public CmdRecordWithBLOBs getByQuery4(String scriptIds, int execStatusUnstart, int execStatusDoing) {
        CmdRecordExample example = new CmdRecordExample();
        CmdRecordExample.Criteria c1 = example.createCriteria();
        c1.andScriptIdEqualTo(scriptIds);
        c1.andStatusIn(Arrays.asList(execStatusUnstart, execStatusDoing));
        List<CmdRecordWithBLOBs> cmdRecordWithBLOBs = cmdRecordMapper.selectByExampleWithBLOBs(example);
        if (cmdRecordWithBLOBs == null || cmdRecordWithBLOBs.size() == 0) {
            return null;
        }
        return cmdRecordWithBLOBs.get(0);
    }
}

package com.jw.bigwhalemonitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoCmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecordExample;
import com.jw.bigwhalemonitor.mapper.CmdRecordMapper;
import com.jw.bigwhalemonitor.service.CmdRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.swing.StringUIClientPropertyKey;

import java.util.List;

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
}

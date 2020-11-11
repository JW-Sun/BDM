package com.jw.bigwhalemonitor.service;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoCmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecord;

import java.util.List;

public interface CmdRecordService {
    PageInfo<CmdRecord> getByQuery(DtoCmdRecord req);
}

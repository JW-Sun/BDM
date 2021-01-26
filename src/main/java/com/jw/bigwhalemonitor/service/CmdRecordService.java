package com.jw.bigwhalemonitor.service;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoCmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecordWithBLOBs;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface CmdRecordService {
    PageInfo<CmdRecord> getByQuery(DtoCmdRecord req);

    CmdRecordWithBLOBs save(CmdRecordWithBLOBs buildCmdRecord);


    CmdRecordWithBLOBs getById(String cmdRecordId);

    List<CmdRecordWithBLOBs> getByQuery2(String id, String format);

	Collection<CmdRecordWithBLOBs> getByQuery3();

	Collection<CmdRecordWithBLOBs> getByJobFinalStatus(String s);

	void deleteByCreateTime(Date format);

	CmdRecordWithBLOBs getByQuery4(String scriptIds, int execStatusUnstart, int execStatusDoing);
}

package com.jw.bigwhalemonitor.controller.script;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoCmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.security.LoginUser;
import com.jw.bigwhalemonitor.service.CmdRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 *  Cmd命令的操作
 */
@RestController
@RequestMapping("/script/cmd_record")
public class CmdRecordController extends BaseController {

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    // private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CmdRecordService cmdRecordService;

    /***
     *  查询的就是关于Cmd命令Record的page页
     * @param req
     * @return
     */
    @RequestMapping(value = "/getpage.api", method = RequestMethod.POST)
    public Msg getPage(@RequestBody DtoCmdRecord req) {
        DateFormat dateFormat = threadLocal.get();
        LoginUser user = getCurrentUser();
        if (!user.isRoot()) {
            req.setUid(user.getId());
        }
        PageInfo<CmdRecord> cmdRecordByQuery = cmdRecordService.getByQuery(req);
        List<CmdRecord> list = cmdRecordByQuery.getList();
        List<DtoCmdRecord> content = new ArrayList<>();
        for (CmdRecord cmdRecord : list) {
            DtoCmdRecord dtoCmdRecord = new DtoCmdRecord();
            BeanUtils.copyProperties(cmdRecord, dtoCmdRecord);
            content.add(dtoCmdRecord);
        }
        PageInfo page = new PageInfo<DtoCmdRecord>(content);
        return success(page);
    }
}

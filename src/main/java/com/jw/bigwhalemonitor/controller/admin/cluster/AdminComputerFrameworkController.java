package com.jw.bigwhalemonitor.controller.admin.cluster;

import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoComputeFramework;
import com.jw.bigwhalemonitor.entity.ComputeFramework;
import com.jw.bigwhalemonitor.service.cluster.ComputerFrameworkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.MacSpi;
import java.util.List;

@RestController
@RequestMapping("/admin/cluster/compute_framework")
public class AdminComputerFrameworkController extends BaseController {

    @Autowired
    private ComputerFrameworkService computerFrameworkService;

    // 增加更新
    @PostMapping("/save.api")
    public Msg save(@RequestBody DtoComputeFramework dtoComputeFramework) {
        // 类型、版本、命令、排都不能为空
        if (dtoComputeFramework.validate() != null) {
            return failed(dtoComputeFramework.validate());
        }
        // 处理新增
        if (dtoComputeFramework.getId() == null) {
            List<ComputeFramework> cf = computerFrameworkService.getByTypeAndVersion(dtoComputeFramework.getType(), dtoComputeFramework.getVersion());
            if (cf.size() > 0) {
                return failed("版本发生冲突");
            }
        }
        ComputeFramework computeFramework = new ComputeFramework();
        BeanUtils.copyProperties(dtoComputeFramework, computeFramework);
        computerFrameworkService.save(computeFramework);
        return success("版本新增成功。");
    }

    // 删除
    @PostMapping("/delete.api")
    public Msg delete(@RequestParam String id) {
        computerFrameworkService.deleteById(id);
        return success("计算框架删除success");
    }

    @PostMapping("/getpage.api")
    public Msg getPage(@RequestBody DtoComputeFramework dtoComputeFramework) {
        List<ComputeFramework> all = computerFrameworkService.getAll(dtoComputeFramework);
        return success(new PageImpl<ComputeFramework>(all));
    }

}

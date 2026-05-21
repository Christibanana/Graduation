package com.ruoyi.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.JdLicenseWarningQuery;
import com.ruoyi.system.domain.vo.JdAppraiserWarningVo;
import com.ruoyi.system.domain.vo.JdInstitutionWarningVo;
import com.ruoyi.system.mapper.JdAppraiserMapper;
import com.ruoyi.system.mapper.JdInstitutionMapper;

/**
 * 司法鉴定证照到期预警
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/judicial/warning")
public class JdLicenseWarningController extends BaseController
{
    @Autowired
    private JdInstitutionMapper jdInstitutionMapper;

    @Autowired
    private JdAppraiserMapper jdAppraiserMapper;

    /**
     * 查询司法鉴定机构证照到期预警列表
     */
    @PreAuthorize("@ss.hasPermi('judicial:warning:institution:list')")
    @GetMapping("/institution/list")
    public TableDataInfo institutionList(JdLicenseWarningQuery query)
    {
        startPage();
        List<JdInstitutionWarningVo> list = jdInstitutionMapper.selectJdInstitutionWarningList(query);
        return getDataTable(list);
    }

    /**
     * 查询司法鉴定人证照到期预警列表
     */
    @PreAuthorize("@ss.hasPermi('judicial:warning:appraiser:list')")
    @GetMapping("/appraiser/list")
    public TableDataInfo appraiserList(JdLicenseWarningQuery query)
    {
        startPage();
        List<JdAppraiserWarningVo> list = jdAppraiserMapper.selectJdAppraiserWarningList(query);
        return getDataTable(list);
    }
}

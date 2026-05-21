package com.ruoyi.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.JdInstitution;
import com.ruoyi.system.domain.bo.PublicJdAppraiserQuery;
import com.ruoyi.system.domain.vo.PublicJdAppraiserVo;
import com.ruoyi.system.domain.vo.PublicJdInstitutionVo;
import com.ruoyi.system.mapper.JdAppraiserMapper;
import com.ruoyi.system.mapper.JdInstitutionMapper;

/**
 * 公开司法鉴定名册查询
 *
 * @author ruoyi
 */
@Anonymous
@RestController
@RequestMapping("/public/judicial")
public class PublicJudicialRosterController extends BaseController
{
    @Autowired
    private JdInstitutionMapper jdInstitutionMapper;

    @Autowired
    private JdAppraiserMapper jdAppraiserMapper;

    /**
     * 查询司法鉴定机构公开名册
     */
    @GetMapping("/institution/list")
    public TableDataInfo institutionList(JdInstitution jdInstitution)
    {
        startPage();
        List<PublicJdInstitutionVo> list = jdInstitutionMapper.selectPublicJdInstitutionList(jdInstitution);
        return getDataTable(list);
    }

    /**
     * 查询司法鉴定人公开名册
     */
    @GetMapping("/appraiser/list")
    public TableDataInfo appraiserList(PublicJdAppraiserQuery query)
    {
        startPage();
        List<PublicJdAppraiserVo> list = jdAppraiserMapper.selectPublicJdAppraiserList(query);
        return getDataTable(list);
    }
}

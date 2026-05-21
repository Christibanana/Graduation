package com.ruoyi.system.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.JdInstitution;
import com.ruoyi.system.service.IJdInstitutionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 司法鉴定机构Controller
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
@RestController
@RequestMapping("/system/institution")
public class JdInstitutionController extends BaseController
{
    @Autowired
    private IJdInstitutionService jdInstitutionService;

    /**
     * 查询司法鉴定机构列表
     */
    @PreAuthorize("@ss.hasPermi('system:institution:list')")
    @GetMapping("/list")
    public TableDataInfo list(JdInstitution jdInstitution)
    {
        startPage();
        List<JdInstitution> list = jdInstitutionService.selectJdInstitutionList(jdInstitution);
        return getDataTable(list);
    }

    /**
     * 导出司法鉴定机构列表
     */
    @PreAuthorize("@ss.hasPermi('system:institution:export')")
    @Log(title = "司法鉴定机构", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JdInstitution jdInstitution)
    {
        List<JdInstitution> list = jdInstitutionService.selectJdInstitutionList(jdInstitution);
        ExcelUtil<JdInstitution> util = new ExcelUtil<JdInstitution>(JdInstitution.class);
        util.exportExcel(response, list, "司法鉴定机构数据");
    }

    /**
     * 获取司法鉴定机构详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:institution:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jdInstitutionService.selectJdInstitutionById(id));
    }

    /**
     * 新增司法鉴定机构
     */
    @PreAuthorize("@ss.hasPermi('system:institution:add')")
    @Log(title = "司法鉴定机构", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JdInstitution jdInstitution)
    {
        return toAjax(jdInstitutionService.insertJdInstitution(jdInstitution));
    }

    /**
     * 修改司法鉴定机构
     */
    @PreAuthorize("@ss.hasPermi('system:institution:edit')")
    @Log(title = "司法鉴定机构", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JdInstitution jdInstitution)
    {
        return toAjax(jdInstitutionService.updateJdInstitution(jdInstitution));
    }

    /**
     * 删除司法鉴定机构
     */
    @PreAuthorize("@ss.hasPermi('system:institution:remove')")
    @Log(title = "司法鉴定机构", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jdInstitutionService.deleteJdInstitutionByIds(ids));
    }
}

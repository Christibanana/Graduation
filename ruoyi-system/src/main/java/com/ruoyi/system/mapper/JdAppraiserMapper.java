package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.bo.JdLicenseWarningQuery;
import com.ruoyi.system.domain.bo.PublicJdAppraiserQuery;
import com.ruoyi.system.domain.JdAppraiser;
import com.ruoyi.system.domain.vo.JdAppraiserWarningVo;
import com.ruoyi.system.domain.vo.PublicJdAppraiserVo;

/**
 * 司法鉴定人Mapper接口
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
public interface JdAppraiserMapper 
{
    /**
     * 查询司法鉴定人
     * 
     * @param id 司法鉴定人主键
     * @return 司法鉴定人
     */
    public JdAppraiser selectJdAppraiserById(Long id);

    /**
     * 查询司法鉴定人列表
     * 
     * @param jdAppraiser 司法鉴定人
     * @return 司法鉴定人集合
     */
    public List<JdAppraiser> selectJdAppraiserList(JdAppraiser jdAppraiser);

    /**
     * 查询司法鉴定人公开名册列表
     *
     * @param query 查询条件
     * @return 司法鉴定人公开名册集合
     */
    public List<PublicJdAppraiserVo> selectPublicJdAppraiserList(PublicJdAppraiserQuery query);

    public List<JdAppraiserWarningVo> selectJdAppraiserWarningList(JdLicenseWarningQuery query);

    /**
     * 新增司法鉴定人
     * 
     * @param jdAppraiser 司法鉴定人
     * @return 结果
     */
    public int insertJdAppraiser(JdAppraiser jdAppraiser);

    /**
     * 修改司法鉴定人
     * 
     * @param jdAppraiser 司法鉴定人
     * @return 结果
     */
    public int updateJdAppraiser(JdAppraiser jdAppraiser);

    /**
     * 删除司法鉴定人
     * 
     * @param id 司法鉴定人主键
     * @return 结果
     */
    public int deleteJdAppraiserById(Long id);

    /**
     * 批量删除司法鉴定人
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJdAppraiserByIds(Long[] ids);
}

package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JdInstitutionMapper;
import com.ruoyi.system.domain.JdInstitution;
import com.ruoyi.system.service.IJdInstitutionService;

/**
 * 司法鉴定机构Service业务层处理
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
@Service
public class JdInstitutionServiceImpl implements IJdInstitutionService 
{
    @Autowired
    private JdInstitutionMapper jdInstitutionMapper;

    /**
     * 查询司法鉴定机构
     * 
     * @param id 司法鉴定机构主键
     * @return 司法鉴定机构
     */
    @Override
    public JdInstitution selectJdInstitutionById(Long id)
    {
        return jdInstitutionMapper.selectJdInstitutionById(id);
    }

    /**
     * 查询司法鉴定机构列表
     * 
     * @param jdInstitution 司法鉴定机构
     * @return 司法鉴定机构
     */
    @Override
    public List<JdInstitution> selectJdInstitutionList(JdInstitution jdInstitution)
    {
        return jdInstitutionMapper.selectJdInstitutionList(jdInstitution);
    }

    /**
     * 新增司法鉴定机构
     * 
     * @param jdInstitution 司法鉴定机构
     * @return 结果
     */
    @Override
    public int insertJdInstitution(JdInstitution jdInstitution)
    {
        jdInstitution.setCreateTime(DateUtils.getNowDate());
        return jdInstitutionMapper.insertJdInstitution(jdInstitution);
    }

    /**
     * 修改司法鉴定机构
     * 
     * @param jdInstitution 司法鉴定机构
     * @return 结果
     */
    @Override
    public int updateJdInstitution(JdInstitution jdInstitution)
    {
        jdInstitution.setUpdateTime(DateUtils.getNowDate());
        return jdInstitutionMapper.updateJdInstitution(jdInstitution);
    }

    /**
     * 批量删除司法鉴定机构
     * 
     * @param ids 需要删除的司法鉴定机构主键
     * @return 结果
     */
    @Override
    public int deleteJdInstitutionByIds(Long[] ids)
    {
        return jdInstitutionMapper.deleteJdInstitutionByIds(ids);
    }

    /**
     * 删除司法鉴定机构信息
     * 
     * @param id 司法鉴定机构主键
     * @return 结果
     */
    @Override
    public int deleteJdInstitutionById(Long id)
    {
        return jdInstitutionMapper.deleteJdInstitutionById(id);
    }
}

package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JdInstitution;

/**
 * 司法鉴定机构Mapper接口
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
public interface JdInstitutionMapper 
{
    /**
     * 查询司法鉴定机构
     * 
     * @param id 司法鉴定机构主键
     * @return 司法鉴定机构
     */
    public JdInstitution selectJdInstitutionById(Long id);

    /**
     * 查询司法鉴定机构列表
     * 
     * @param jdInstitution 司法鉴定机构
     * @return 司法鉴定机构集合
     */
    public List<JdInstitution> selectJdInstitutionList(JdInstitution jdInstitution);

    /**
     * 新增司法鉴定机构
     * 
     * @param jdInstitution 司法鉴定机构
     * @return 结果
     */
    public int insertJdInstitution(JdInstitution jdInstitution);

    /**
     * 修改司法鉴定机构
     * 
     * @param jdInstitution 司法鉴定机构
     * @return 结果
     */
    public int updateJdInstitution(JdInstitution jdInstitution);

    /**
     * 删除司法鉴定机构
     * 
     * @param id 司法鉴定机构主键
     * @return 结果
     */
    public int deleteJdInstitutionById(Long id);

    /**
     * 批量删除司法鉴定机构
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJdInstitutionByIds(Long[] ids);
}

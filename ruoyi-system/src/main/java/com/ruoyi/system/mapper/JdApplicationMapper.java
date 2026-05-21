package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JdApplication;

/**
 * 申请审批Mapper接口
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
public interface JdApplicationMapper 
{
    /**
     * 查询申请审批
     * 
     * @param id 申请审批主键
     * @return 申请审批
     */
    public JdApplication selectJdApplicationById(Long id);

    /**
     * 查询申请审批列表
     * 
     * @param jdApplication 申请审批
     * @return 申请审批集合
     */
    public List<JdApplication> selectJdApplicationList(JdApplication jdApplication);

    /**
     * 新增申请审批
     * 
     * @param jdApplication 申请审批
     * @return 结果
     */
    public int insertJdApplication(JdApplication jdApplication);

    /**
     * 修改申请审批
     * 
     * @param jdApplication 申请审批
     * @return 结果
     */
    public int updateJdApplication(JdApplication jdApplication);

    /**
     * 删除申请审批
     * 
     * @param id 申请审批主键
     * @return 结果
     */
    public int deleteJdApplicationById(Long id);

    /**
     * 批量删除申请审批
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJdApplicationByIds(Long[] ids);
}

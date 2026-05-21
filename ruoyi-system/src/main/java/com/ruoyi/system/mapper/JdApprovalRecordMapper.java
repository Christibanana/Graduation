package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.JdApprovalRecord;

/**
 * 审批记录Mapper接口
 *
 * @author ruoyi
 */
public interface JdApprovalRecordMapper
{
    /**
     * 新增审批记录
     *
     * @param jdApprovalRecord 审批记录
     * @return 结果
     */
    public int insertJdApprovalRecord(JdApprovalRecord jdApprovalRecord);
}

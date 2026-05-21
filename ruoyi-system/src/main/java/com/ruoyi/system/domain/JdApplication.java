package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 申请审批对象 jd_application
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
public class JdApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 申请ID */
    private Long id;

    /** 申请编号 */
    @Excel(name = "申请编号")
    private String applyNo;

    /** 申请类型 */
    @Excel(name = "申请类型")
    private String applyType;

    /** 申请对象类型 */
    @Excel(name = "申请对象类型")
    private String objectType;

    /** 目标对象ID */
    @Excel(name = "目标对象ID")
    private Long targetId;

    /** 申请人ID */
    private Long applicantId;

    /** 所属机构ID */
    @Excel(name = "所属机构ID")
    private Long institutionId;

    /** 变更前数据JSON */
    private String beforeData;

    /** 变更后数据JSON */
    private String afterData;

    /** 申请材料说明 */
    private String materialDesc;

    /** 申请状态 */
    @Excel(name = "申请状态")
    private String status;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /** 审核人ID */
    @Excel(name = "审核人ID")
    private Long reviewerId;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewTime;

    /** 审核意见 */
    @Excel(name = "审核意见")
    private String reviewOpinion;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setApplyNo(String applyNo) 
    {
        this.applyNo = applyNo;
    }

    public String getApplyNo() 
    {
        return applyNo;
    }

    public void setApplyType(String applyType) 
    {
        this.applyType = applyType;
    }

    public String getApplyType() 
    {
        return applyType;
    }

    public void setObjectType(String objectType) 
    {
        this.objectType = objectType;
    }

    public String getObjectType() 
    {
        return objectType;
    }

    public void setTargetId(Long targetId) 
    {
        this.targetId = targetId;
    }

    public Long getTargetId() 
    {
        return targetId;
    }

    public void setApplicantId(Long applicantId) 
    {
        this.applicantId = applicantId;
    }

    public Long getApplicantId() 
    {
        return applicantId;
    }

    public void setInstitutionId(Long institutionId) 
    {
        this.institutionId = institutionId;
    }

    public Long getInstitutionId() 
    {
        return institutionId;
    }

    public void setBeforeData(String beforeData) 
    {
        this.beforeData = beforeData;
    }

    public String getBeforeData() 
    {
        return beforeData;
    }

    public void setAfterData(String afterData) 
    {
        this.afterData = afterData;
    }

    public String getAfterData() 
    {
        return afterData;
    }

    public void setMaterialDesc(String materialDesc) 
    {
        this.materialDesc = materialDesc;
    }

    public String getMaterialDesc() 
    {
        return materialDesc;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setSubmitTime(Date submitTime) 
    {
        this.submitTime = submitTime;
    }

    public Date getSubmitTime() 
    {
        return submitTime;
    }

    public void setReviewerId(Long reviewerId) 
    {
        this.reviewerId = reviewerId;
    }

    public Long getReviewerId() 
    {
        return reviewerId;
    }

    public void setReviewTime(Date reviewTime) 
    {
        this.reviewTime = reviewTime;
    }

    public Date getReviewTime() 
    {
        return reviewTime;
    }

    public void setReviewOpinion(String reviewOpinion) 
    {
        this.reviewOpinion = reviewOpinion;
    }

    public String getReviewOpinion() 
    {
        return reviewOpinion;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("applyNo", getApplyNo())
            .append("applyType", getApplyType())
            .append("objectType", getObjectType())
            .append("targetId", getTargetId())
            .append("applicantId", getApplicantId())
            .append("institutionId", getInstitutionId())
            .append("beforeData", getBeforeData())
            .append("afterData", getAfterData())
            .append("materialDesc", getMaterialDesc())
            .append("status", getStatus())
            .append("submitTime", getSubmitTime())
            .append("reviewerId", getReviewerId())
            .append("reviewTime", getReviewTime())
            .append("reviewOpinion", getReviewOpinion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}

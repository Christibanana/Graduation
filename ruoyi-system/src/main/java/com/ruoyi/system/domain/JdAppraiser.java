package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 司法鉴定人对象 jd_appraiser
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
public class JdAppraiser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 鉴定人ID */
    private Long id;

    /** 所属机构ID */
    @Excel(name = "所属机构ID")
    private Long institutionId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 身份证号 */
    private String idCard;

    /** 学历 */
    private String education;

    /** 专业技术职称 */
    @Excel(name = "专业技术职称")
    private String professionalTitle;

    /** 执业证号 */
    @Excel(name = "执业证号")
    private String practiceCertNo;

    /** 执业类别 */
    @Excel(name = "执业类别")
    private String practiceCategory;

    /** 联系电话 */
    private String phone;

    /** 执业证有效期起 */
    private Date certValidFrom;

    /** 执业证有效期止 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "执业证有效期止", width = 30, dateFormat = "yyyy-MM-dd")
    private Date certValidTo;

    /** 鉴定人状态 */
    @Excel(name = "鉴定人状态")
    private String status;

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

    public void setInstitutionId(Long institutionId) 
    {
        this.institutionId = institutionId;
    }

    public Long getInstitutionId() 
    {
        return institutionId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }

    public void setIdCard(String idCard) 
    {
        this.idCard = idCard;
    }

    public String getIdCard() 
    {
        return idCard;
    }

    public void setEducation(String education) 
    {
        this.education = education;
    }

    public String getEducation() 
    {
        return education;
    }

    public void setProfessionalTitle(String professionalTitle) 
    {
        this.professionalTitle = professionalTitle;
    }

    public String getProfessionalTitle() 
    {
        return professionalTitle;
    }

    public void setPracticeCertNo(String practiceCertNo) 
    {
        this.practiceCertNo = practiceCertNo;
    }

    public String getPracticeCertNo() 
    {
        return practiceCertNo;
    }

    public void setPracticeCategory(String practiceCategory) 
    {
        this.practiceCategory = practiceCategory;
    }

    public String getPracticeCategory() 
    {
        return practiceCategory;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setCertValidFrom(Date certValidFrom) 
    {
        this.certValidFrom = certValidFrom;
    }

    public Date getCertValidFrom() 
    {
        return certValidFrom;
    }

    public void setCertValidTo(Date certValidTo) 
    {
        this.certValidTo = certValidTo;
    }

    public Date getCertValidTo() 
    {
        return certValidTo;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("institutionId", getInstitutionId())
            .append("name", getName())
            .append("gender", getGender())
            .append("idCard", getIdCard())
            .append("education", getEducation())
            .append("professionalTitle", getProfessionalTitle())
            .append("practiceCertNo", getPracticeCertNo())
            .append("practiceCategory", getPracticeCategory())
            .append("phone", getPhone())
            .append("certValidFrom", getCertValidFrom())
            .append("certValidTo", getCertValidTo())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}

package com.ruoyi.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 司法鉴定人证照预警返回对象
 *
 * @author ruoyi
 */
public class JdAppraiserWarningVo
{
    private Long id;

    private String name;

    private String practiceCertNo;

    private Long institutionId;

    private String institutionName;

    private String practiceCategory;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date certValidTo;

    private String status;

    private String warningType;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPracticeCertNo()
    {
        return practiceCertNo;
    }

    public void setPracticeCertNo(String practiceCertNo)
    {
        this.practiceCertNo = practiceCertNo;
    }

    public Long getInstitutionId()
    {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId)
    {
        this.institutionId = institutionId;
    }

    public String getInstitutionName()
    {
        return institutionName;
    }

    public void setInstitutionName(String institutionName)
    {
        this.institutionName = institutionName;
    }

    public String getPracticeCategory()
    {
        return practiceCategory;
    }

    public void setPracticeCategory(String practiceCategory)
    {
        this.practiceCategory = practiceCategory;
    }

    public Date getCertValidTo()
    {
        return certValidTo;
    }

    public void setCertValidTo(Date certValidTo)
    {
        this.certValidTo = certValidTo;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getWarningType()
    {
        return warningType;
    }

    public void setWarningType(String warningType)
    {
        this.warningType = warningType;
    }
}

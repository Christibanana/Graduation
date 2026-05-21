package com.ruoyi.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 公开司法鉴定人名册视图对象
 *
 * @author ruoyi
 */
public class PublicJdAppraiserVo
{
    private Long id;

    private String name;

    private String practiceCertNo;

    private String institutionName;

    private String practiceCategory;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date certValidTo;

    private String status;

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
}

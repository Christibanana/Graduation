package com.ruoyi.system.domain.bo;

/**
 * 公开司法鉴定人名册查询对象
 *
 * @author ruoyi
 */
public class PublicJdAppraiserQuery
{
    private String name;

    private String practiceCertNo;

    private String institutionName;

    private String practiceCategory;

    private String status;

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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}

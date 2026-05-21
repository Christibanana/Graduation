package com.ruoyi.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 司法鉴定机构证照预警返回对象
 *
 * @author ruoyi
 */
public class JdInstitutionWarningVo
{
    private Long id;

    private String name;

    private String licenseNo;

    private String region;

    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date licenseValidTo;

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

    public String getLicenseNo()
    {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo)
    {
        this.licenseNo = licenseNo;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Date getLicenseValidTo()
    {
        return licenseValidTo;
    }

    public void setLicenseValidTo(Date licenseValidTo)
    {
        this.licenseValidTo = licenseValidTo;
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

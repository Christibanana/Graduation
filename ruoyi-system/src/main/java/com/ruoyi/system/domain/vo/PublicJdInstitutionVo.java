package com.ruoyi.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 公开司法鉴定机构名册视图对象
 *
 * @author ruoyi
 */
public class PublicJdInstitutionVo
{
    private Long id;

    private String name;

    private String licenseNo;

    private String region;

    private String address;

    private String phone;

    private String businessScope;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date licenseValidTo;

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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getBusinessScope()
    {
        return businessScope;
    }

    public void setBusinessScope(String businessScope)
    {
        this.businessScope = businessScope;
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
}

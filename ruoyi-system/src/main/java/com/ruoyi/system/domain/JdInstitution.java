package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 司法鉴定机构对象 jd_institution
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
public class JdInstitution extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 机构ID */
    @Excel(name = "机构ID")
    private Long id;

    /** 机构名称 */
    @Excel(name = "机构名称")
    private String name;

    /** 许可证号 */
    @Excel(name = "许可证号")
    private String licenseNo;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String creditCode;

    /** 法定代表人 */
    @Excel(name = "法定代表人")
    private String legalRepresentative;

    /** 机构负责人 */
    @Excel(name = "机构负责人")
    private String principal;

    /** 所属地区 */
    @Excel(name = "所属地区")
    private String region;

    /** 住所 */
    private String address;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 业务范围 */
    @Excel(name = "业务范围")
    private String businessScope;

    /** 许可证有效期起 */
    private Date licenseValidFrom;

    /** 许可证有效期止 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "许可证有效期止", width = 30, dateFormat = "yyyy-MM-dd")
    private Date licenseValidTo;

    /** 机构状态 */
    @Excel(name = "机构状态")
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

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setLicenseNo(String licenseNo) 
    {
        this.licenseNo = licenseNo;
    }

    public String getLicenseNo() 
    {
        return licenseNo;
    }

    public void setCreditCode(String creditCode) 
    {
        this.creditCode = creditCode;
    }

    public String getCreditCode() 
    {
        return creditCode;
    }

    public void setLegalRepresentative(String legalRepresentative) 
    {
        this.legalRepresentative = legalRepresentative;
    }

    public String getLegalRepresentative() 
    {
        return legalRepresentative;
    }

    public void setPrincipal(String principal) 
    {
        this.principal = principal;
    }

    public String getPrincipal() 
    {
        return principal;
    }

    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setBusinessScope(String businessScope) 
    {
        this.businessScope = businessScope;
    }

    public String getBusinessScope() 
    {
        return businessScope;
    }

    public void setLicenseValidFrom(Date licenseValidFrom) 
    {
        this.licenseValidFrom = licenseValidFrom;
    }

    public Date getLicenseValidFrom() 
    {
        return licenseValidFrom;
    }

    public void setLicenseValidTo(Date licenseValidTo) 
    {
        this.licenseValidTo = licenseValidTo;
    }

    public Date getLicenseValidTo() 
    {
        return licenseValidTo;
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
            .append("name", getName())
            .append("licenseNo", getLicenseNo())
            .append("creditCode", getCreditCode())
            .append("legalRepresentative", getLegalRepresentative())
            .append("principal", getPrincipal())
            .append("region", getRegion())
            .append("address", getAddress())
            .append("phone", getPhone())
            .append("businessScope", getBusinessScope())
            .append("licenseValidFrom", getLicenseValidFrom())
            .append("licenseValidTo", getLicenseValidTo())
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

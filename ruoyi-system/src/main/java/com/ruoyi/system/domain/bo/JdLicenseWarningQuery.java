package com.ruoyi.system.domain.bo;

/**
 * 司法鉴定证照预警查询对象
 *
 * @author ruoyi
 */
public class JdLicenseWarningQuery
{
    /** 预警类型：EXPIRING 90天内即将到期，EXPIRED 已经过期 */
    private String warningType;

    public String getWarningType()
    {
        return warningType;
    }

    public void setWarningType(String warningType)
    {
        this.warningType = warningType;
    }
}

package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.JdAppraiser;

/**
 * 司法鉴定人Service接口
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
public interface IJdAppraiserService 
{
    /**
     * 查询司法鉴定人
     * 
     * @param id 司法鉴定人主键
     * @return 司法鉴定人
     */
    public JdAppraiser selectJdAppraiserById(Long id);

    /**
     * 查询司法鉴定人列表
     * 
     * @param jdAppraiser 司法鉴定人
     * @return 司法鉴定人集合
     */
    public List<JdAppraiser> selectJdAppraiserList(JdAppraiser jdAppraiser);

    /**
     * 新增司法鉴定人
     * 
     * @param jdAppraiser 司法鉴定人
     * @return 结果
     */
    public int insertJdAppraiser(JdAppraiser jdAppraiser);

    /**
     * 修改司法鉴定人
     * 
     * @param jdAppraiser 司法鉴定人
     * @return 结果
     */
    public int updateJdAppraiser(JdAppraiser jdAppraiser);

    /**
     * 批量删除司法鉴定人
     * 
     * @param ids 需要删除的司法鉴定人主键集合
     * @return 结果
     */
    public int deleteJdAppraiserByIds(Long[] ids);

    /**
     * 删除司法鉴定人信息
     * 
     * @param id 司法鉴定人主键
     * @return 结果
     */
    public int deleteJdAppraiserById(Long id);
}

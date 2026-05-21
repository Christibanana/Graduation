package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JdAppraiserMapper;
import com.ruoyi.system.domain.JdAppraiser;
import com.ruoyi.system.service.IJdAppraiserService;

/**
 * 司法鉴定人Service业务层处理
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
@Service
public class JdAppraiserServiceImpl implements IJdAppraiserService 
{
    @Autowired
    private JdAppraiserMapper jdAppraiserMapper;

    /**
     * 查询司法鉴定人
     * 
     * @param id 司法鉴定人主键
     * @return 司法鉴定人
     */
    @Override
    public JdAppraiser selectJdAppraiserById(Long id)
    {
        return jdAppraiserMapper.selectJdAppraiserById(id);
    }

    /**
     * 查询司法鉴定人列表
     * 
     * @param jdAppraiser 司法鉴定人
     * @return 司法鉴定人
     */
    @Override
    public List<JdAppraiser> selectJdAppraiserList(JdAppraiser jdAppraiser)
    {
        return jdAppraiserMapper.selectJdAppraiserList(jdAppraiser);
    }

    /**
     * 新增司法鉴定人
     * 
     * @param jdAppraiser 司法鉴定人
     * @return 结果
     */
    @Override
    public int insertJdAppraiser(JdAppraiser jdAppraiser)
    {
        jdAppraiser.setCreateTime(DateUtils.getNowDate());
        return jdAppraiserMapper.insertJdAppraiser(jdAppraiser);
    }

    /**
     * 修改司法鉴定人
     * 
     * @param jdAppraiser 司法鉴定人
     * @return 结果
     */
    @Override
    public int updateJdAppraiser(JdAppraiser jdAppraiser)
    {
        jdAppraiser.setUpdateTime(DateUtils.getNowDate());
        return jdAppraiserMapper.updateJdAppraiser(jdAppraiser);
    }

    /**
     * 批量删除司法鉴定人
     * 
     * @param ids 需要删除的司法鉴定人主键
     * @return 结果
     */
    @Override
    public int deleteJdAppraiserByIds(Long[] ids)
    {
        return jdAppraiserMapper.deleteJdAppraiserByIds(ids);
    }

    /**
     * 删除司法鉴定人信息
     * 
     * @param id 司法鉴定人主键
     * @return 结果
     */
    @Override
    public int deleteJdAppraiserById(Long id)
    {
        return jdAppraiserMapper.deleteJdAppraiserById(id);
    }
}

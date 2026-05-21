package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.JdApplicationMapper;
import com.ruoyi.system.mapper.JdApprovalRecordMapper;
import com.ruoyi.system.domain.JdApplication;
import com.ruoyi.system.domain.JdApprovalRecord;
import com.ruoyi.system.domain.JdAppraiser;
import com.ruoyi.system.domain.JdInstitution;
import com.ruoyi.system.service.IJdAppraiserService;
import com.ruoyi.system.service.IJdApplicationService;
import com.ruoyi.system.service.IJdInstitutionService;

/**
 * 申请审批Service业务层处理
 * 
 * @author Wu Wangmin
 * @date 2026-05-22
 */
@Service
public class JdApplicationServiceImpl implements IJdApplicationService 
{
    private static final String STATUS_DRAFT = "DRAFT";
    private static final String STATUS_SUBMITTED = "SUBMITTED";
    private static final String STATUS_APPROVED = "APPROVED";
    private static final String STATUS_REJECTED = "REJECTED";
    private static final String STATUS_CANCELLED = "CANCELLED";

    private static final String ACTION_SUBMIT = "SUBMIT";
    private static final String ACTION_APPROVE = "APPROVE";
    private static final String ACTION_REJECT = "REJECT";

    private static final String TYPE_INSTITUTION_REGISTER = "INSTITUTION_REGISTER";
    private static final String TYPE_INSTITUTION_CHANGE = "INSTITUTION_CHANGE";
    private static final String TYPE_INSTITUTION_RENEWAL = "INSTITUTION_RENEWAL";
    private static final String TYPE_INSTITUTION_CANCEL = "INSTITUTION_CANCEL";
    private static final String TYPE_APPRAISER_REGISTER = "APPRAISER_REGISTER";
    private static final String TYPE_APPRAISER_CHANGE = "APPRAISER_CHANGE";
    private static final String TYPE_APPRAISER_RENEWAL = "APPRAISER_RENEWAL";
    private static final String TYPE_APPRAISER_CANCEL = "APPRAISER_CANCEL";

    @Autowired
    private JdApplicationMapper jdApplicationMapper;

    @Autowired
    private JdApprovalRecordMapper jdApprovalRecordMapper;

    @Autowired
    private IJdInstitutionService jdInstitutionService;

    @Autowired
    private IJdAppraiserService jdAppraiserService;

    /**
     * 查询申请审批
     * 
     * @param id 申请审批主键
     * @return 申请审批
     */
    @Override
    public JdApplication selectJdApplicationById(Long id)
    {
        return jdApplicationMapper.selectJdApplicationById(id);
    }

    /**
     * 查询申请审批列表
     * 
     * @param jdApplication 申请审批
     * @return 申请审批
     */
    @Override
    public List<JdApplication> selectJdApplicationList(JdApplication jdApplication)
    {
        return jdApplicationMapper.selectJdApplicationList(jdApplication);
    }

    /**
     * 新增申请审批
     * 
     * @param jdApplication 申请审批
     * @return 结果
     */
    @Override
    public int insertJdApplication(JdApplication jdApplication)
    {
        jdApplication.setCreateTime(DateUtils.getNowDate());
        return jdApplicationMapper.insertJdApplication(jdApplication);
    }

    /**
     * 修改申请审批
     * 
     * @param jdApplication 申请审批
     * @return 结果
     */
    @Override
    public int updateJdApplication(JdApplication jdApplication)
    {
        jdApplication.setUpdateTime(DateUtils.getNowDate());
        return jdApplicationMapper.updateJdApplication(jdApplication);
    }

    /**
     * 批量删除申请审批
     * 
     * @param ids 需要删除的申请审批主键
     * @return 结果
     */
    @Override
    public int deleteJdApplicationByIds(Long[] ids)
    {
        return jdApplicationMapper.deleteJdApplicationByIds(ids);
    }

    /**
     * 删除申请审批信息
     * 
     * @param id 申请审批主键
     * @return 结果
     */
    @Override
    public int deleteJdApplicationById(Long id)
    {
        return jdApplicationMapper.deleteJdApplicationById(id);
    }

    /**
     * 提交申请
     *
     * @param id 申请ID
     * @return 结果
     */
    @Override
    @Transactional
    public int submitJdApplication(Long id)
    {
        JdApplication application = getRequiredApplication(id);
        if (!STATUS_DRAFT.equals(application.getStatus()) && !STATUS_REJECTED.equals(application.getStatus()))
        {
            throw new ServiceException("只有草稿或审核驳回状态的申请可以提交");
        }

        JdApplication update = new JdApplication();
        update.setId(id);
        update.setStatus(STATUS_SUBMITTED);
        update.setSubmitTime(DateUtils.getNowDate());
        update.setUpdateBy(SecurityUtils.getUsername());
        update.setUpdateTime(DateUtils.getNowDate());
        int rows = jdApplicationMapper.updateJdApplication(update);
        insertApprovalRecord(id, ACTION_SUBMIT, "提交申请");
        return rows;
    }

    /**
     * 审核通过申请
     *
     * @param id 申请ID
     * @param reviewOpinion 审核意见
     * @return 结果
     */
    @Override
    @Transactional
    public int approveJdApplication(Long id, String reviewOpinion)
    {
        JdApplication application = getRequiredApplication(id);
        if (!STATUS_SUBMITTED.equals(application.getStatus()))
        {
            throw new ServiceException("只有待审核状态的申请可以审核通过");
        }

        applyFormalArchive(application);

        JdApplication update = new JdApplication();
        update.setId(id);
        update.setStatus(STATUS_APPROVED);
        update.setReviewerId(SecurityUtils.getUserId());
        update.setReviewTime(DateUtils.getNowDate());
        update.setReviewOpinion(reviewOpinion);
        update.setUpdateBy(SecurityUtils.getUsername());
        update.setUpdateTime(DateUtils.getNowDate());
        int rows = jdApplicationMapper.updateJdApplication(update);
        insertApprovalRecord(id, ACTION_APPROVE, reviewOpinion);
        return rows;
    }

    /**
     * 审核驳回申请
     *
     * @param id 申请ID
     * @param reviewOpinion 审核意见
     * @return 结果
     */
    @Override
    @Transactional
    public int rejectJdApplication(Long id, String reviewOpinion)
    {
        JdApplication application = getRequiredApplication(id);
        if (!STATUS_SUBMITTED.equals(application.getStatus()))
        {
            throw new ServiceException("只有待审核状态的申请可以驳回");
        }

        JdApplication update = new JdApplication();
        update.setId(id);
        update.setStatus(STATUS_REJECTED);
        update.setReviewerId(SecurityUtils.getUserId());
        update.setReviewTime(DateUtils.getNowDate());
        update.setReviewOpinion(reviewOpinion);
        update.setUpdateBy(SecurityUtils.getUsername());
        update.setUpdateTime(DateUtils.getNowDate());
        int rows = jdApplicationMapper.updateJdApplication(update);
        insertApprovalRecord(id, ACTION_REJECT, reviewOpinion);
        return rows;
    }

    private JdApplication getRequiredApplication(Long id)
    {
        JdApplication application = jdApplicationMapper.selectJdApplicationById(id);
        if (application == null)
        {
            throw new ServiceException("申请不存在");
        }
        return application;
    }

    private void insertApprovalRecord(Long applicationId, String action, String opinion)
    {
        JdApprovalRecord record = new JdApprovalRecord();
        record.setApplicationId(applicationId);
        record.setAction(action);
        record.setOperatorId(SecurityUtils.getUserId());
        record.setOpinion(opinion);
        record.setOperateTime(DateUtils.getNowDate());
        record.setCreateBy(SecurityUtils.getUsername());
        record.setCreateTime(DateUtils.getNowDate());
        record.setDelFlag("0");
        jdApprovalRecordMapper.insertJdApprovalRecord(record);
    }

    private void applyFormalArchive(JdApplication application)
    {
        String applyType = application.getApplyType();
        if (TYPE_INSTITUTION_REGISTER.equals(applyType))
        {
            JdInstitution institution = parseAfterData(application, JdInstitution.class);
            institution.setCreateBy(SecurityUtils.getUsername());
            jdInstitutionService.insertJdInstitution(institution);
        }
        else if (TYPE_INSTITUTION_CHANGE.equals(applyType))
        {
            JdInstitution institution = parseAfterData(application, JdInstitution.class);
            institution.setId(requiredTargetId(application));
            institution.setUpdateBy(SecurityUtils.getUsername());
            ensureUpdated(jdInstitutionService.updateJdInstitution(institution), "机构档案不存在或更新失败");
        }
        else if (TYPE_INSTITUTION_RENEWAL.equals(applyType))
        {
            JdInstitution afterData = parseAfterData(application, JdInstitution.class);
            JdInstitution institution = new JdInstitution();
            institution.setId(requiredTargetId(application));
            institution.setLicenseValidFrom(afterData.getLicenseValidFrom());
            institution.setLicenseValidTo(afterData.getLicenseValidTo());
            institution.setStatus(afterData.getStatus());
            institution.setUpdateBy(SecurityUtils.getUsername());
            ensureUpdated(jdInstitutionService.updateJdInstitution(institution), "机构档案不存在或延续失败");
        }
        else if (TYPE_INSTITUTION_CANCEL.equals(applyType))
        {
            JdInstitution institution = new JdInstitution();
            institution.setId(requiredTargetId(application));
            institution.setStatus(STATUS_CANCELLED);
            institution.setUpdateBy(SecurityUtils.getUsername());
            ensureUpdated(jdInstitutionService.updateJdInstitution(institution), "机构档案不存在或注销失败");
        }
        else if (TYPE_APPRAISER_REGISTER.equals(applyType))
        {
            JdAppraiser appraiser = parseAfterData(application, JdAppraiser.class);
            appraiser.setCreateBy(SecurityUtils.getUsername());
            jdAppraiserService.insertJdAppraiser(appraiser);
        }
        else if (TYPE_APPRAISER_CHANGE.equals(applyType))
        {
            JdAppraiser appraiser = parseAfterData(application, JdAppraiser.class);
            appraiser.setId(requiredTargetId(application));
            appraiser.setUpdateBy(SecurityUtils.getUsername());
            ensureUpdated(jdAppraiserService.updateJdAppraiser(appraiser), "鉴定人档案不存在或更新失败");
        }
        else if (TYPE_APPRAISER_RENEWAL.equals(applyType))
        {
            JdAppraiser afterData = parseAfterData(application, JdAppraiser.class);
            JdAppraiser appraiser = new JdAppraiser();
            appraiser.setId(requiredTargetId(application));
            appraiser.setCertValidFrom(afterData.getCertValidFrom());
            appraiser.setCertValidTo(afterData.getCertValidTo());
            appraiser.setStatus(afterData.getStatus());
            appraiser.setUpdateBy(SecurityUtils.getUsername());
            ensureUpdated(jdAppraiserService.updateJdAppraiser(appraiser), "鉴定人档案不存在或延续失败");
        }
        else if (TYPE_APPRAISER_CANCEL.equals(applyType))
        {
            JdAppraiser appraiser = new JdAppraiser();
            appraiser.setId(requiredTargetId(application));
            appraiser.setStatus(STATUS_CANCELLED);
            appraiser.setUpdateBy(SecurityUtils.getUsername());
            ensureUpdated(jdAppraiserService.updateJdAppraiser(appraiser), "鉴定人档案不存在或注销失败");
        }
        else
        {
            throw new ServiceException("不支持的申请类型：" + applyType);
        }
    }

    private Long requiredTargetId(JdApplication application)
    {
        if (application.getTargetId() == null)
        {
            throw new ServiceException("当前申请缺少目标对象ID");
        }
        return application.getTargetId();
    }

    private <T> T parseAfterData(JdApplication application, Class<T> clazz)
    {
        if (application.getAfterData() == null || application.getAfterData().trim().isEmpty())
        {
            throw new ServiceException("当前申请缺少变更后数据");
        }
        try
        {
            return JSON.parseObject(application.getAfterData(), clazz);
        }
        catch (Exception e)
        {
            throw new ServiceException("变更后数据JSON解析失败");
        }
    }

    private void ensureUpdated(int rows, String message)
    {
        if (rows <= 0)
        {
            throw new ServiceException(message);
        }
    }
}

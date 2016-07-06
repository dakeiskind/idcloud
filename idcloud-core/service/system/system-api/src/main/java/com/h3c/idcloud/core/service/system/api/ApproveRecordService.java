package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.ApproveRecord;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface ApproveRecordService {
    int countByParams(Criteria example);

    ApproveRecord selectByPrimaryKey(Long approveRecordSid);

    List<ApproveRecord> selectByParams(Criteria example);

    List<ApproveRecord> selectByParams2(Criteria example);

    int deleteByPrimaryKey(Long approveRecordSid);

    int updateByPrimaryKeySelective(ApproveRecord record);

    int updateByPrimaryKey(ApproveRecord record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(ApproveRecord record, Criteria example);

    int updateByParams(ApproveRecord record, Criteria example);

    int insert(ApproveRecord record);

    int insertSelective(ApproveRecord record);
    
    List<ApproveRecord> selectOrderOpenByParams(Criteria example);
    
    List<ApproveRecord> selectProcessInstanceIdAndType(Criteria example);
   
    List<ApproveRecord> selectOperateApproveRecord(Criteria example);
    
    String selectApproveRecord(HttpServletRequest request) throws Exception;

    boolean tenantAdminApprove(String checkStatus, String checkcomments, String processInstanceId, String approvorAction, String processType);
    
    boolean operateAdminApprove1(String checkStatus, String checkcomments, String processInstanceId, String approvorAction, String processType);
    //ChengQi start
    boolean createAdminApprove(Map<String, Object> map, String checkStatus, String checkcomments, String processInstanceId, String approvorAction, String processType);
    
    boolean executeRequestResource(Map<String, Object> map, String processType);
    
    public boolean executeRequestResource(String processObjectId, String processType);
    
    public ResInstResult vmResCheck(Map<String, Object> map);
    
    //ChengQi end
    boolean createApprove(String checkStatus, String checkcomments, String processInstanceId, String approvorAction, String processType);
    
    boolean initApprove(String instanceSid, Long changeLogSid);
    
    List<ApproveRecord> selectApproveRecordInst(Criteria example, String type);
    
    List<ApproveRecord> findApproveHistoryRecords(String processInstanceId);

    List<ApproveRecord> selectApprovedRecord(Criteria example);
    
    Map getInstanceResSet(List<Map<String, Object>> resSetList, Long instanceSid);
    
    Integer countApproveRecord(String type);
    
    Boolean isResourceMgtRole(User user);
    
    void initCancelApproveRecord(String instanceSid, Long changeLogSid);
    
    /**
     * 项目切换项目经理和虚机切换项目的审核
     * @param sid
     * @param changeLogSid
     * @return
     */
    boolean initChangeApprove(String sid, Long changeLogSid, String processType);
}
package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.system.MailInfo;
import com.h3c.idcloud.core.pojo.dto.system.MailTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface MailTemplateMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long mailTemplateSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MailTemplate record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MailTemplate record);

    /**
     * 根据条件查询记录集
     */
    List<MailTemplate> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    MailTemplate selectByPrimaryKey(Long mailTemplateSid);
    
    /**
     * 根据Id查询记录
     */
    MailTemplate selectById(String mailTemplateId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") MailTemplate record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") MailTemplate record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MailTemplate record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MailTemplate record);
    
    /**
     * 根据服务实例sid查询VM邮件信息
     */
    MailInfo  selectVmMailInfo(Criteria example);
    
    /**
     * 根据服务实例sid查询VM邮件信息
     */
    MailInfo  selectStorageMailInfo(Criteria example);
    
    /**
     * 根据服务实例sid查询VM邮件信息
     */
    MailInfo  selectExMailInfo(Criteria example);
    
    /**
     * 根据服务实例sid查询VM邮件信息
     */
    MailInfo  selectSpMailInfo(Criteria example);
    
    /**
     * 根据服务实例sid查询VM邮件信息
     */
    MailInfo  selectDeploymentMailInfo(Criteria example);
    
    /**
     * 根据闲置资源sid查询owner用户邮件信息
     */
    MailInfo  selectFreeResMailInfo(Criteria example);
    
}
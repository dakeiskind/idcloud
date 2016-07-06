package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.dto.system.Attachments;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface AttachmentService {
    int countByParams(Criteria example);

    Attachments selectByPrimaryKey(String attachmentSid);

    List<Attachments> selectByParams(Criteria example);

    int deleteByPrimaryKey(String attachmentSid);

    int updateByPrimaryKeySelective(Attachments record);

    int updateByPrimaryKey(Attachments record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Attachments record, Criteria example);

    int updateByParams(Attachments record, Criteria example);

    int insert(Attachments record);

    int insertSelective(Attachments record);
}
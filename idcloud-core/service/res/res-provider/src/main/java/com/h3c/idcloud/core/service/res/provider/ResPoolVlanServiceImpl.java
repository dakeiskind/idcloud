package com.h3c.idcloud.core.service.res.provider;


import com.h3c.idcloud.core.persist.res.dao.ResPoolVlanMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVlanMapper;
import com.h3c.idcloud.core.service.res.api.ResPoolVlanService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResPoolVlanServiceImpl implements ResPoolVlanService {
    private static final Logger logger = LoggerFactory.getLogger(ResPoolVlanServiceImpl.class);
    @Autowired
    private ResPoolVlanMapper resPoolVlanMapper;
    @Autowired
    private ResVlanMapper resVlanMapper;

    public int deleteByPrimaryKey(String resPoolSid) {
        // 先删除vlan
        Criteria example = new Criteria();
        example.put("deleteResPoolSid", resPoolSid);
        this.resVlanMapper.deleteByParams(example);
        return this.resPoolVlanMapper.deleteByPrimaryKey(resPoolSid);
    }

}
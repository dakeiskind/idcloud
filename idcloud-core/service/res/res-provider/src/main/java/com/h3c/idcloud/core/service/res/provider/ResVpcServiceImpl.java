package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResVpcMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResVpc;
import com.h3c.idcloud.core.service.res.api.ResVpcService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Res vpc service 类.
 *
 * @author jpg
 */
@Service(version = "1.0.0")
@Component
public class ResVpcServiceImpl implements ResVpcService {

    /**
     * 静态变量 logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ResVpcServiceImpl.class);

    @Autowired
    private ResVpcMapper resVpcMapper;


    /**
     * 查询vpc列表
     *
     * @param criteria
     * @return
     */
    @Override
    public List<ResVpc> selectByParams(Criteria criteria) {
        return this.resVpcMapper.selectByParams(criteria);
    }

    /**
     * 更新vpc数据
     *
     * @param vpc
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(ResVpc vpc) {
        return this.resVpcMapper.updateByPrimaryKeySelective(vpc);
    }
}
package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResVpcPortsMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcPorts;
import com.h3c.idcloud.core.service.res.api.ResVpcPortsService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Res host service 类.
 *
 * @author Chaohong.Mao
 */
@Service(version = "1.0.0")
@Component
public class ResVpcPortsServiceImpl implements ResVpcPortsService {
    /**
     * 静态变量 logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ResVpcPortsServiceImpl.class);
    @Autowired
    private ResVpcPortsMapper resVpcPortsMapper;


    /**
     * 查询路由器列表
     *
     * @param criteria
     * @return
     */
    @Override
    public List<ResVpcPorts> selectByParams(Criteria criteria) {
        return this.resVpcPortsMapper.selectByParams(criteria);
    }

    /**
     * 更新路由器信息
     *
     * @param port
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(ResVpcPorts port) {
        return this.resVpcPortsMapper.updateByPrimaryKeySelective(port);
    }
}
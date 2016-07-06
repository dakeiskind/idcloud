package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResStatisticsMapper;
import com.h3c.idcloud.core.service.res.api.ResStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 资源统计服务实现
 * Created by swq on 4/13/2016.
 */
@Service(version = "1.0.0")
@Component
public class ResStatisticsServiceImpl implements ResStatisticsService {

    @Autowired
    private ResStatisticsMapper resStatisticsMapper;

    @Override
    public Map<String,Object> selectStatisticsVmInfo(String ownerId) {
        return resStatisticsMapper.selectStatisticsVmInfo(ownerId);
    }

    @Override
    public Map<String,Object> selectStatisticsFloatingIpInfo(Long mgtObjSid) {
        return resStatisticsMapper.selectStatisticsFloatingIpInfo(mgtObjSid);
    }
}

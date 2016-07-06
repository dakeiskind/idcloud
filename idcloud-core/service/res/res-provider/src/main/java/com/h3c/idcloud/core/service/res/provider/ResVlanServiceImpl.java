package com.h3c.idcloud.core.service.res.provider;


import com.h3c.idcloud.core.persist.res.dao.ResVlanMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResVlan;
import com.h3c.idcloud.core.service.res.api.ResVlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResVlanServiceImpl implements ResVlanService {
    private static final Logger logger = LoggerFactory.getLogger(ResVlanServiceImpl.class);
    @Autowired
    private ResVlanMapper resVlanMapper;

    @Override
    public int insertSelective(ResVlan record) {
        int fresult = 0;
        // 根据起始Vlan_ID和结束Vlan_ID插入VLan
        int startID = record.getStartValnId();
        int endID = record.getEndVlanId();
        for (int i = startID; i < (endID + 1); i++) {
            ResVlan vlan = new ResVlan();
            vlan.setVlanId("" + i + "");
            vlan.setResPoolSid(record.getResPoolSid());
            fresult += this.resVlanMapper.insertSelective(vlan);
        }

        if ((endID - startID + 1) == fresult) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public ResVlan statisticsVlanInPn(String resTopologySid) {
        return this.resVlanMapper.statisticsVlanInPn(resTopologySid);
    }


}
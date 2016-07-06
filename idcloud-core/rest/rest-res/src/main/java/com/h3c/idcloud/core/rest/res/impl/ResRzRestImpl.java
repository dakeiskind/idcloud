package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.rest.res.ResRzRest;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jpg on 2016/3/2.
 */
@Component
public class ResRzRestImpl implements ResRzRest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /** 资源拓扑Service */
    @Reference(version = "1.0.0")
    ResVeService resVeService;


}

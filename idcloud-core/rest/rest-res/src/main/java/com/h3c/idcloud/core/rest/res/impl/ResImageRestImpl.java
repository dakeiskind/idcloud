package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResImage;
import com.h3c.idcloud.core.rest.res.ResImageRest;
import com.h3c.idcloud.core.rest.res.ResRzRest;
import com.h3c.idcloud.core.service.res.api.ResImageService;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jpg on 2016/3/2.
 */
@Component
public class ResImageRestImpl implements ResImageRest {

    Logger log = Logger.getLogger(this.getClass());

    /** 资源镜像Service */
    @Reference(version = "1.0.0")
    ResImageService resImageService;


    /**
     * 查询资源镜像列表
     *
     * @param image
     * @return
     */
    @Override
    public Response findImages(ResImage image) {
        Criteria example = new Criteria();
        if (image != null) {
            if (!StringUtil.isNullOrEmpty(image.getStatus())) {
                example.put("status", image.getStatus());
            }
            if (!StringUtil.isNullOrEmpty(image.getOsType())) {
                example.put("osType", image.getOsType());
            }

        }
        System.out.println("----------------"+JsonUtil.toJson(image));
        List<ResImage> list = this.resImageService.selectByParams(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }
}

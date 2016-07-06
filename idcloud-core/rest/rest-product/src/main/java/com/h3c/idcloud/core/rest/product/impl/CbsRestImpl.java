package com.h3c.idcloud.core.rest.product.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.rest.product.CbsRest;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2016/4/20.
 */
@Component
public class CbsRestImpl implements CbsRest {
    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;
    @Override
    public Response findCbsInfo(@Context HttpServletRequest req) {
        // 得到当前用户
        AuthUser user = RequestContextUtil.getAuthUserInfo(req);
		Criteria example = new Criteria();
		example.put("ownerId", user.getAccount());
	    example.put("mgtObjSid", user.getMgtObjSid());
		example.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
		example.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
		List<ServiceInstance> list = this.serviceInstanceService.selectVolumeStorageInfo(example);
//		// 返回结果
		return Response.status(Response.Status.OK).entity(new RestResult(list)).build();

    }
}

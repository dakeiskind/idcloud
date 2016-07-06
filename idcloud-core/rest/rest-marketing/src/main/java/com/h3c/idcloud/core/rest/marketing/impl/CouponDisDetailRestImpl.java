package com.h3c.idcloud.core.rest.marketing.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.marketing.CouponDisDetail;
import com.h3c.idcloud.core.rest.marketing.CouponDisDetailRest;
import com.h3c.idcloud.core.service.marketing.api.CouponDisDetailService;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2016/4/14.
 */

@Component
public class CouponDisDetailRestImpl implements CouponDisDetailRest {

    @Reference(version = "1.0.0")
    CouponDisDetailService couponDisDetailService;

    @Override
    public Response bindingBydistributionDetailSid(Map<String, Object> data) {
        String id = data.get("distributionDetailSid").toString();
        String uid = data.get("userSid").toString();
        CouponDisDetail couponDisDetail = new CouponDisDetail();
        couponDisDetail.setDistributionDetailSid(new Long(id));
        couponDisDetail.setUserSid(new Long(uid));
        couponDisDetail.setUsedStatus(1);
        WebUtil.prepareUpdateParams(couponDisDetail);
        int result = this.couponDisDetailService.updateByPrimaryKeySelective(couponDisDetail);
        String json = JsonUtil.toJson(result);
        return Response.status(Response.Status.OK).entity(json).build();
    }
}

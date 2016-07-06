package com.h3c.idcloud.core.rest.system.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.system.CodeArea;
import com.h3c.idcloud.core.pojo.dto.system.CodeCity;
import com.h3c.idcloud.core.pojo.dto.system.CodeProvince;
import com.h3c.idcloud.core.rest.system.CodeAddressRest;
import com.h3c.idcloud.core.service.system.api.CodeAreaService;
import com.h3c.idcloud.core.service.system.api.CodeCityService;
import com.h3c.idcloud.core.service.system.api.CodeProvinceService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by gujie on 2016/3/31.
 */
@Component
public class CodeAddressRestImpl implements CodeAddressRest{

    @Reference(version = "1.0.0")
    CodeProvinceService codeProvinceService;

    @Reference(version = "1.0.0")
    CodeCityService codeCityService;

    @Reference(version = "1.0.0")
    CodeAreaService codeAreaService;

    @Override
    public Response getProvince() {
        Criteria example = new Criteria();
        List<CodeProvince> list = codeProvinceService.selectByParams(example);
        return Response.ok(JsonUtil.toJson(new RestResult(list))).build();
    }

    @Override
    public Response getCityByProvince(Long provinceId) {
        Criteria example = new Criteria();
        example.put("provinceId",provinceId);
        List<CodeCity> list = codeCityService.selectByParams(example);
        return Response.ok(JsonUtil.toJson(new RestResult(list))).build();
    }

    @Override
    public Response getAreaByCity(Long cityId) {
        Criteria example = new Criteria();
        example.put("cityId",cityId);
        List<CodeArea> list = codeAreaService.selectByParams(example);
        return Response.ok(JsonUtil.toJson(new RestResult(list))).build();
    }
}

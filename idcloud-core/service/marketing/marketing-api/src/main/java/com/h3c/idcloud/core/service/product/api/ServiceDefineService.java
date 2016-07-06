package com.h3c.idcloud.core.service.product.api;

import com.h3c.idcloud.core.pojo.dto.product.ServiceDefine;
import com.h3c.idcloud.core.pojo.vo.common.CommonServiceCode;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public interface ServiceDefineService  {
    int countByParams(Criteria example);

    ServiceDefine selectByPrimaryKey(Long serviceSid);

    List<ServiceDefine> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long serviceSid);

    int updateByPrimaryKeySelective(ServiceDefine record);

    int insertSelective(ServiceDefine record);

    /**
     * 根据条件查询记录集
     */
    List<ServiceDefine> selectServiceByCatalogSidList(Criteria example);

    /**
     * 后台新增服务
     *
     * @param service
     * @return
     */
    boolean insertPlatformService(ServiceDefine service);

    /**
     * 后台更新服务
     *
     * @param service
     * @return
     */
    boolean updatePlatformService(ServiceDefine service);

    /**
     * 查询公共的服务定义codes
     * @param example
     * @return
     */
   List<CommonServiceCode> findCommonServiceCodes(Criteria example);
}

package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.persist.res.dao.ResObjectStorageUsageMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResOSUsageSum;
import com.h3c.idcloud.core.pojo.dto.res.ResObjectStorageUsage;
import com.h3c.idcloud.core.service.res.api.ResObjectStorageUsageService;
import com.h3c.idcloud.infrastructure.common.pojo.RESTHttpResponse;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.RSETClientUtil;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResObjectStorageUsageServiceImpl implements ResObjectStorageUsageService {
    private static final Logger logger = LoggerFactory.getLogger(ResObjectStorageUsageServiceImpl.class);
    @Autowired
    private ResObjectStorageUsageMapper resObjectStorageUsageMapper;

    public void scanUsage(boolean retrieveAll) {
        String[] accounts = retrieveAll ? null : this.getOSAccounts();
        List<ResObjectStorageUsage> usages = this.scanUsageFromHy(accounts, retrieveAll);
        if (usages != null) {
            this.batchInsertUsages(usages);
        }

    }

    private List<ResObjectStorageUsage> scanUsageFromHy(String[] accounts, boolean retrieveAll) {
        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        List<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();
        providers.add(jsonProvider);

        String adaptorURL = PropertiesUtil.getProperty("opadaptor.url");

        Map postData = new HashMap<String, Object>();
        postData.put("retrieveAll", retrieveAll);
        postData.put("accounts", accounts);
        RESTHttpResponse response = null;
        try {
            response = RSETClientUtil.post(adaptorURL + "/resources/list_os_accounts_metadata",
                                           JsonUtil.toJson(postData)
            );
            if ("200".equals(response.getStatus())) {
                Map respMap = JsonUtil.fromJson(response.getContent(), Map.class);
                if (respMap != null && respMap.containsKey("os_metadata")) {
                    List list = (List) respMap.get("os_metadata");
                    if (list != null && list.size() > 0) {
                        List<ResObjectStorageUsage> usages = new ArrayList<ResObjectStorageUsage>();
                        for (Object o : list) {
                            try {
                                Map data = (Map) o;
                                ResObjectStorageUsage usage = new ResObjectStorageUsage();
                                try {
                                    usage.setAccount(Long.parseLong(data.get("account").toString()));
                                } catch (NumberFormatException ex) {
                                    continue;
                                }

                                usage.setBytes(Long.parseLong(data.get("bytes").toString()));
                                usage.setContainerCount(Long.parseLong(data.get("container_count").toString()));
                                usage.setObjectCount(Long.parseLong(data.get("object_count").toString()));
                                Calendar now = Calendar.getInstance();
                                usage.setCreateDt(now.getTime());
                                Calendar countDt = Calendar.getInstance();
                                countDt.clear();
                                countDt.set(now.get(Calendar.YEAR),
                                            now.get(Calendar.MONTH),
                                            now.get(Calendar.DATE) - 1
                                );
                                usage.setCountDt(countDt.getTime());
                                usages.add(usage);
                            } catch (Exception ex) {
                                logger.error(String.format("扫描对象存储使用量错误，元数据%s，错误原因%s", JsonUtil.toJson(o), ex));
                            }
                        }
                        return usages;
                    }
                }
            }
        } catch (Exception e) {
            throw new RpcException(RpcException.UNKNOWN_EXCEPTION, "系统错误");
        }

        return null;
    }

    private String[] getOSAccounts() {
        return null;
    }

    private void batchInsertUsages(List<ResObjectStorageUsage> usages) {
        if (usages != null) {
            for (ResObjectStorageUsage usage : usages) { this.resObjectStorageUsageMapper.insertSelective(usage); }
        }
    }

    public List<ResObjectStorageUsage> getUsage(long tenantId, Date begin, Date end) {
        Map params = new HashMap<String, Object>();
        params.put("begin", begin);
        params.put("end", end);
        params.put("account", tenantId);
        return this.resObjectStorageUsageMapper.selectByMap(params);
    }

    public ResOSUsageSum getUsageSum(long tenantId, Date begin, Date end) {
        Map params = new HashMap<String, Object>();
        params.put("begin", begin);
        params.put("end", end);
        params.put("account", tenantId);
        return this.resObjectStorageUsageMapper.selectSum(params);
    }
}
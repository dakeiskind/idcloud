package com.h3c.idcloud.infra.test;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 泛用测试Service <br/>
 * 可以提供服务任意的服务调用
 *
 * FIXME 现在无法取得调用的类名，所以只能以方法名作为key；多个Service中有重名方法则有问题
 *
 * @author Chaohong.Mao
 */
public class TestGenericService implements GenericService {
    private Map<String, Object> resultMap = new HashMap<>();

    /**
     * 根据调用的方法设置返回结果
     *
     * @param methodName the method name
     * @param result     the result
     * @return the result by method
     */
    public Map<String, Object> setResultByMethod(String methodName, Object result) {
        this.resultMap.put(methodName, result);
        return this.resultMap;
    }

    /**
     * 获得对应方法名所对应的返回结果
     *
     * @param methodName the method name
     * @return the result by method
     */
    public Map<String, Object> getResultByMethod(String methodName) {
        return this.resultMap;
    }

    @Override
    public Object $invoke(String methodName, String[] parameterTypes, Object[] args) throws GenericException {
        Map<String, Object> result = new HashMap<>();
        result.put("methodName", methodName);
        try {
            result.put("param", JSON.json(args));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("泛用实现Service : " + result.toString());
        return this.resultMap.get(methodName);
    }
}

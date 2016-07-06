package com.h3c.idcloud.infrastructure.common.exception.handle;


import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.base.Throwables;
import com.h3c.idcloud.infrastructure.common.constants.RestConst;
import com.h3c.idcloud.infrastructure.common.exception.BizException;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Rest exception handler 类.
 * 统一异常Handle，对外唯一异常出口
 *
 * @author Chaohong.Mao
 */
@Provider
public class RestExceptionHandler implements ExceptionMapper<Exception> {

    Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    public Response toResponse(Exception ex) {
        Response resp = null;
        RestResult restResult = new RestResult();
        Response.Status status;
        if (ex instanceof RpcException) {
            // 如果是RPC异常，取得异常的类型code
            if (((RpcException) ex).isBiz()) {
                status = Response.Status.OK;
                restResult.setMessage(ex.getMessage())
                          .setCode(RestConst.HttpConst.OK)
                          .setStatus(RestResult.Status.FAILURE);
                logger.debug("业务异常：{}", ex.getMessage());
            } else {
                status = Response.Status.INTERNAL_SERVER_ERROR;
                restResult.setMessage("内部错误发生。")
                          .setCode(RestConst.BizError.BIZ_ERROR)
                          .setStatus(RestResult.Status.FAILURE);
                logger.error("系统异常发生：{}", Throwables.getStackTraceAsString(ex));
            }
            resp = Response.status(status).entity(JsonUtil.toJson(restResult)).build();

        } else if (ex instanceof BizException) {
            restResult.setMessage(ex.getMessage())
                      .setCode(((BizException) ex).getRestEnum())
                      .setStatus(RestResult.Status.FAILURE);
            resp = Response.status(Response.Status.OK).entity(JsonUtil.toJson(restResult)).build();
        } else if (ex instanceof NotAllowedException) {
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED)
                           .entity(Response.Status.METHOD_NOT_ALLOWED.toString()).build();
        } else if (ex instanceof NotAcceptableException) {
            resp = Response.status(Response.Status.NOT_ACCEPTABLE)
                           .entity(Response.Status.NOT_ACCEPTABLE.toString()).build();
        } else if (ex instanceof NotFoundException) {
            resp = Response.status(Response.Status.NOT_FOUND)
                           .entity(Response.Status.NOT_FOUND.toString()).build();
        } else {
            // 非捕获的java/javax异常系，作为系统错误输出
            restResult.setMessage("内部错误发生。")
                      .setCode(RestConst.SysError.SYS_ERROR)
                      .setStatus(RestResult.Status.FAILURE);
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.toJson(restResult)).build();
            logger.error("系统异常发生：{}", Throwables.getStackTraceAsString(ex));
        }

        return resp;
    }
}


package com.h3c.idcloud.infrastructure.common.exception;

import com.h3c.idcloud.infrastructure.common.constants.RestConst;

/**
 * Biz exception 类.
 *
 * @author Chaohong.Mao
 */
public class BizException extends RuntimeException {

    /** 错误类型 */
    private RestConst.RestEnum restEnum;

    public RestConst.RestEnum getRestEnum() {
        return restEnum;
    }

    public void setRestEnum(RestConst.RestEnum restEnum) {
        this.restEnum = restEnum;
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param restEnum  the error enum
     * @param frdMessage the frd message
     */
    public BizException(RestConst.RestEnum restEnum, String frdMessage) {
        super(frdMessage);
        this.restEnum = restEnum;
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param restEnum the error enum
     * @param throwable the throwable
     */
    public BizException(RestConst.RestEnum restEnum, Throwable throwable) {
        super(throwable);
        this.restEnum = restEnum;
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param restEnum  the error enum
     * @param frdMessage the frd message
     * @param throwable  the throwable
     */
    public BizException(RestConst.RestEnum restEnum, String frdMessage, Throwable throwable) {
        super(frdMessage, throwable);
        this.restEnum = restEnum;
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param frdMessage the frd message
     */
    public BizException(String frdMessage) {
        super(frdMessage);
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param throwable the throwable
     */
    public BizException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param frdMessage the frd message
     * @param throwable  the throwable
     */
    public BizException(String frdMessage, Throwable throwable) {
        super(frdMessage, throwable);
    }

}

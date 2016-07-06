package com.h3c.idcloud.infrastructure.common.pojo;

/**
 * Created by swq on 3/2/2016.
 */
public class SimpleRestResult {
    private String code;
    private Object data;



    public SimpleRestResult(Object data){
        this.code = CODE.SUCCESS;
        this.data = data;
    }

    public SimpleRestResult(boolean isSuccess){
        if(isSuccess){
            this.code = CODE.SUCCESS;
            this.data = true;
        }else {
            this.code = CODE.ERROR;
            this.data = false;
        }
    }

    public SimpleRestResult(String code,Object data){
        this.code = code;
        this.data = data;
    }

    public SimpleRestResult(boolean isSuccess,Object data){
        if(isSuccess)
           this.code = CODE.SUCCESS;
        else
           this.code = CODE.ERROR;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    interface CODE{
        String SUCCESS = "200";
        String ERROR = "500";
    }
}

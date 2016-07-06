package com.h3c.idcloud.core.pojo.dto.res;

import java.util.Date;

/**
 * Res vm monitor 类.
 *
 * @author Chaohong.Mao
 */
public class ResVmMonitor {
    /**
     * The Time.
     */
    private Date time;
    /**
     * The Value.
     */
    private float value;
    /**
     * The In val.
     */
    private float in_val;
    /**
     * The Out val.
     */
    private float out_val;

    /**
     * 获得 time.
     *
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设定 time.
     *
     * @param time the time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获得 value.
     *
     * @return the value
     */
    public float getValue() {
        return value;
    }

    /**
     * 设定 value.
     *
     * @param value the value
     */
    public void setValue(float value) {
        this.value = value;
    }

    /**
     * 获得 in val.
     *
     * @return the in val
     */
    public float getIn_val() {
        return in_val;
    }

    /**
     * 设定 in val.
     *
     * @param in_val the in val
     */
    public void setIn_val(float in_val) {
        this.in_val = in_val;
    }

    /**
     * 获得 out val.
     *
     * @return the out val
     */
    public float getOut_val() {
        return out_val;
    }

    /**
     * 设定 out val.
     *
     * @param out_val the out val
     */
    public void setOut_val(float out_val) {
        this.out_val = out_val;
    }

}

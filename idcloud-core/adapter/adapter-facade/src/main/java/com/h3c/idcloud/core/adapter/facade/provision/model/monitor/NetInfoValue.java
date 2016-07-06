package com.h3c.idcloud.core.adapter.facade.provision.model.monitor;

import org.codehaus.jackson.annotate.JsonProperty;

public class NetInfoValue {

    private String time;
    private String in;
    private String out;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("in_val")
    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    @JsonProperty("out_val")
    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

}

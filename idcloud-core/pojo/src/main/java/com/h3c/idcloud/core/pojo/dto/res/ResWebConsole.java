/*
 * ts-resource
 * 概要：ResWebConsole.java
 *
 * 创建人：asdc002
 * 创建日：2014年12月24日
 * 更新履历
 * 2014年12月24日  asdc002  创建
 *
 * @(#)ResWebConsole.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.dto.res;

/**
 * ResWebConsole.java
 *
 * @author asdc002
 */
public class ResWebConsole {

    /**
     * The Modes.
     */
    public int modes;
    /**
     * The Msg mode.
     */
    public int msgMode;
    /**
     * The Advanced config.
     */
    public String advancedConfig;
    /**
     * The Host.
     */
    public String host;
    /**
     * The Thumb.
     */
    public String thumb;
    /**
     * The Allow ssl errors.
     */
    public boolean allowSSLErrors;
    /**
     * The Ticket.
     */
    public String ticket;
    /**
     * The User.
     */
    public String user;
    /**
     * The Pass.
     */
    public String pass;
    /**
     * The Vm id.
     */
    public String vmID;
    /**
     * The Datacenter.
     */
    public String datacenter;
    /**
     * The Vm path.
     */
    public String vmPath;

    /**
     * 获得 modes.
     *
     * @return the modes
     */
    public int getModes() {
        return modes;
    }

    /**
     * 设定 modes.
     *
     * @param modes the modes
     */
    public void setModes(int modes) {
        this.modes = modes;
    }

    /**
     * 获得 msg mode.
     *
     * @return the msg mode
     */
    public int getMsgMode() {
        return msgMode;
    }

    /**
     * 设定 msg mode.
     *
     * @param msgMode the msg mode
     */
    public void setMsgMode(int msgMode) {
        this.msgMode = msgMode;
    }

    /**
     * 获得 advanced config.
     *
     * @return the advanced config
     */
    public String getAdvancedConfig() {
        return advancedConfig;
    }

    /**
     * 设定 advanced config.
     *
     * @param advancedConfig the advanced config
     */
    public void setAdvancedConfig(String advancedConfig) {
        this.advancedConfig = advancedConfig;
    }

    /**
     * 获得 host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * 设定 host.
     *
     * @param host the host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 获得 thumb.
     *
     * @return the thumb
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * 设定 thumb.
     *
     * @param thumb the thumb
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * Is allow ssl errors boolean.
     *
     * @return the boolean
     */
    public boolean isAllowSSLErrors() {
        return allowSSLErrors;
    }

    /**
     * 设定 allow ssl errors.
     *
     * @param allowSSLErrors the allow ssl errors
     */
    public void setAllowSSLErrors(boolean allowSSLErrors) {
        this.allowSSLErrors = allowSSLErrors;
    }

    /**
     * 获得 ticket.
     *
     * @return the ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * 设定 ticket.
     *
     * @param ticket the ticket
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    /**
     * 获得 user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * 设定 user.
     *
     * @param user the user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 获得 pass.
     *
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * 设定 pass.
     *
     * @param pass the pass
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * 获得 vm id.
     *
     * @return the vm id
     */
    public String getVmID() {
        return vmID;
    }

    /**
     * 设定 vm id.
     *
     * @param vmID the vm id
     */
    public void setVmID(String vmID) {
        this.vmID = vmID;
    }

    /**
     * 获得 datacenter.
     *
     * @return the datacenter
     */
    public String getDatacenter() {
        return datacenter;
    }

    /**
     * 设定 datacenter.
     *
     * @param datacenter the datacenter
     */
    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    /**
     * 获得 vm path.
     *
     * @return the vm path
     */
    public String getVmPath() {
        return vmPath;
    }

    /**
     * 设定 vm path.
     *
     * @param vmPath the vm path
     */
    public void setVmPath(String vmPath) {
        this.vmPath = vmPath;
    }

}

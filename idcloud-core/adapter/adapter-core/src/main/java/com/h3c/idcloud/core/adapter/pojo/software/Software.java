package com.h3c.idcloud.core.adapter.pojo.software;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

import java.util.Map;

public class Software extends Base {

    private String id;
    private String name;
    private String version;
    private String type;
    private String mediaPath;
    private String scriptPath;
    private String mediaLib;
    private String scriptLib;
    private boolean success;
    private String osVersion;
    private Map<String, String> softWareConfig;//软件配置选项

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getMediaLib() {
        return mediaLib;
    }

    public void setMediaLib(String mediaLib) {
        this.mediaLib = mediaLib;
    }

    public String getScriptLib() {
        return scriptLib;
    }

    public void setScriptLib(String scriptLib) {
        this.scriptLib = scriptLib;
    }

    public Map<String, String> getSoftWareConfig() {
        return softWareConfig;
    }

    public void setSoftWareConfig(Map<String, String> softWareConfig) {
        this.softWareConfig = softWareConfig;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}

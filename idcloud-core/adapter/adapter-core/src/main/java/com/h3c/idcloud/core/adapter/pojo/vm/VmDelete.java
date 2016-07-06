package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class VmDelete extends Base {

    private String serverId;
    private boolean isDueToFailOfCreating;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public boolean isDueToFailOfCreating() {
        return isDueToFailOfCreating;
    }

    public void setDueToFailOfCreating(boolean isDueToFailOfCreating) {
        this.isDueToFailOfCreating = isDueToFailOfCreating;
    }

}

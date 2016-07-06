package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.ArrayList;
import java.util.List;

public class Templates extends CommonAdapterResult {

    private List<Template> listdata = new ArrayList<Template>();

    private List<Template> images = new ArrayList<Template>();

    public List<Template> getImages() {
        return images;
    }

    public void setImages(List<Template> images) {
        this.images = images;
    }

    public List<Template> getListdata() {
        return listdata;
    }

    public void setListdata(List<Template> listdata) {
        this.listdata = listdata;
    }

}

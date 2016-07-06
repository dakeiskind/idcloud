package com.h3c.idcloud.core.pojo.vo.product;

import java.util.Date;

/**
 * Created by Apple on 2016/2/18.
 */
public class ServerCatalogVo {

    /**
     * 目录SID
     */
    private Long catalogSid;

    /**
     * 目录名称
     */
    private String catalogName;

    /**
     * 描述
     */
    private String description;

    /**
     * 目录分类
     */
    private String catelogGroup;

    /**
     * 父级目录编号
     */
    private Long parentCatalogSid;

    /**
     * 展示图片路径
     */
    private String imagePath;

    /**
     * 背景图片路径
     */
    private String bgImagePath;

    /**
     * 所需租户
     */
    private String tanentId;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedDt;

    /**
     * 版本号
     */
    private Long version;
}

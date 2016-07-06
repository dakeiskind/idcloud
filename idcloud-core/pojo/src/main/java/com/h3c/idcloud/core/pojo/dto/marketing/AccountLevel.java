package com.h3c.idcloud.core.pojo.dto.marketing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountLevel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 用户SID
     */
    private Long levelSid;

    /**
     * 等级名称
     */
    private String levelName;

    private String levelNameLike;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 等级满足额度
     */
    private Long levelLimit;

    /**
     * 优先级别
     */
    private Integer prior;

    /**
     * 等级描述
     */
    private String levelDescription;

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

    /**
     * 是否能预渲染(0:否1:是)
     */
    private Integer isAllowPrerender;

    /**
     * 是否能合成视频(0:否1:是)
     */
    private Integer isAllowCreateMovie;

    /**
     * 是否能场景分析(0:否1:是)
     */
    private Integer isAllowSceneAnalysis;

    /**
     * 是否能生成小图(0:否1:是)
     */
    private Integer isAllowCreateThumbnail;

    /**
     * 可预渲染帧数
     */
    private Integer maxAllowedFrameCount;





    /**
     * @return 用户SID
     */
    public Long getLevelSid() {
        return levelSid;
    }

    /**
     * @param levelSid
     *            用户SID
     */
    public void setLevelSid(Long levelSid) {
        this.levelSid = levelSid;
    }

    /**
     * @return 等级名称
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * @param levelName
     *            等级名称
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelNameLike() {
        return levelNameLike;
    }

    public void setLevelNameLike(String levelNameLike) {
        this.levelNameLike = levelNameLike;
    }

    /**
     * @return 折扣
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * @param discount
     *            折扣
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * @return 等级满足额度
     */
    public Long getLevelLimit() {
        return levelLimit;
    }

    /**
     * @param levelLimit
     *            等级满足额度
     */
    public void setLevelLimit(Long levelLimit) {
        this.levelLimit = levelLimit;
    }

    /**
     * @return 优先级别
     */
    public Integer getPrior() {
        return prior;
    }

    /**
     * @param prior
     *            优先级别
     */
    public void setPrior(Integer prior) {
        this.prior = prior;
    }

    /**
     * @return 等级描述
     */
    public String getLevelDescription() {
        return levelDescription;
    }

    /**
     * @param levelDescription
     *            等级描述
     */
    public void setLevelDescription(String levelDescription) {
        this.levelDescription = levelDescription;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return 创建时间
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * @param createdDt
     *            创建时间
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * @return 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     *            更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * @param updatedDt
     *            更新时间
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * @return 版本号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version
     *            版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return 是否能预渲染(0:否1:是)
     */
    public Integer getIsAllowPrerender() {
        return isAllowPrerender;
    }

    /**
     * @param isAllowPrerender
     *            是否能预渲染(0:否1:是)
     */
    public void setIsAllowPrerender(Integer isAllowPrerender) {
        this.isAllowPrerender = isAllowPrerender;
    }

    /**
     * @return 是否能合成视频(0:否1:是)
     */
    public Integer getIsAllowCreateMovie() {
        return isAllowCreateMovie;
    }

    /**
     * @param isAllowCreateMovie
     *            是否能合成视频(0:否1:是)
     */
    public void setIsAllowCreateMovie(Integer isAllowCreateMovie) {
        this.isAllowCreateMovie = isAllowCreateMovie;
    }

    /**
     * @return 是否能场景分析(0:否1:是)
     */
    public Integer getIsAllowSceneAnalysis() {
        return isAllowSceneAnalysis;
    }

    /**
     * @param isAllowSceneAnalysis
     *            是否能场景分析(0:否1:是)
     */
    public void setIsAllowSceneAnalysis(Integer isAllowSceneAnalysis) {
        this.isAllowSceneAnalysis = isAllowSceneAnalysis;
    }

    /**
     * @return 是否能生成小图(0:否1:是)
     */
    public Integer getIsAllowCreateThumbnail() {
        return isAllowCreateThumbnail;
    }

    /**
     * @param isAllowCreateThumbnail
     *            是否能生成小图(0:否1:是)
     */
    public void setIsAllowCreateThumbnail(Integer isAllowCreateThumbnail) {
        this.isAllowCreateThumbnail = isAllowCreateThumbnail;
    }

    public Integer getMaxAllowedFrameCount() {
        return maxAllowedFrameCount;
    }

    public void setMaxAllowedFrameCount(Integer maxAllowedFrameCount) {
        this.maxAllowedFrameCount = maxAllowedFrameCount;
    }
}
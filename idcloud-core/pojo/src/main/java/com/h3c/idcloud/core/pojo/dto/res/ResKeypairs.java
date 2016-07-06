package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResKeypairs implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 秘钥对ID
     */
    private String resKeypairsSid;

    /**
     * 秘钥对名称
     */
    private String keypairsName;
    private String keypairsNameLike;

    /**
     * 秘钥对ID/名称
     */
    private String keypairsNameAndSid;

    /**
     * 指纹
     */
    private String fingerprint;

    /**
     * 描述
     */
    private String description;

    /**
     * 所属租户ID
     */
    private Long mgtObjSid;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Long version;

    private String ownerId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return 秘钥对ID
     */
    public String getResKeypairsSid() {
        return resKeypairsSid;
    }

    /**
     * @param resKeypairsSid 秘钥对ID
     */
    public void setResKeypairsSid(String resKeypairsSid) {
        this.resKeypairsSid = resKeypairsSid;
    }

    /**
     * @return the publicKey
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * @param publicKey the publicKey to set
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * @return 秘钥对名称
     */
    public String getKeypairsName() {
        return keypairsName;
    }

    public String getKeypairsNameLike() {
        return keypairsNameLike;
    }

    public void setKeypairsNameLike(String keypairsNameLike) {
        this.keypairsNameLike = keypairsNameLike;
    }

    /**
     * @param keypairsName 秘钥对名称
     */
    public void setKeypairsName(String keypairsName) {
        this.keypairsName = keypairsName;
    }

    /**
     * @return 指纹
     */
    public String getFingerprint() {
        return fingerprint;
    }

    /**
     * @param fingerprint 指纹
     */
    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    /**
     * @return 所属租户ID
     */
    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    /**
     * @param mgtObjSid 所属租户ID
     */
    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    /**
     * @return the privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * @param privateKey the privateKey to set
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdDt
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * @param createdDt the createdDt to set
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDt
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * @param updatedDt the updatedDt to set
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeypairsNameAndSid() {
        return keypairsNameAndSid;
    }

    public void setKeypairsNameAndSid(String keypairsNameAndSid) {
        this.keypairsNameAndSid = keypairsNameAndSid;
    }
}
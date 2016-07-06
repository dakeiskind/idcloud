package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;

public class UserTopic implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 主题SID
     */
    private Long topicSid;

    /**
     * 用户SID
     */
    private Long userSid;

    /**
     * 主题显示顺序
     */
    private Integer sortRank;
    
    /**
     * @return 主题SID
     */
    public Long getTopicSid() {
        return topicSid;
    }

    /**
     * @param topicSid 
	 *            主题SID
     */
    public void setTopicSid(Long topicSid) {
        this.topicSid = topicSid;
    }

    /**
     * @return 用户SID
     */
    public Long getUserSid() {
        return userSid;
    }

    /**
     * @param userSid 
	 *            用户SID
     */
    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }

    /**
     * @return 主题显示顺序
     */
    public Integer getSortRank() {
        return sortRank;
    }

    /**
     * @param sortRank 
	 *            主题显示顺序
     */
    public void setSortRank(Integer sortRank) {
        this.sortRank = sortRank;
    }
}
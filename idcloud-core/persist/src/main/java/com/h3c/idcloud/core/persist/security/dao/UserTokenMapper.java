package com.h3c.idcloud.core.persist.security.dao;

import com.h3c.idcloud.core.pojo.dto.security.UserToken;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by qct on 2016/2/25.
 */
@Repository
public interface UserTokenMapper {

    /**
     * 根据条件查询记录总数
     * @param example
     * @return
     */
    int countByParams(Criteria example);

    /**根据条件删除记录
     *
     * @param example
     * @return
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     * @param userTokenId
     * @return
     */
    int deleteByPrimaryKey(Long userTokenId);

    /**
     * 插入
     * @param userToken
     * @return
     */
    int insert(UserToken userToken);

    /**
     * 部分插入
     * @param userToken
     * @return
     */
    int insertSelective(UserToken userToken);

    /**
     * 根据条件查询记录
     * @param example
     * @return
     */
    List<UserToken> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     * @param userTokenId
     * @return
     */
    UserToken selectByPrimaryKey(Long userTokenId);
}

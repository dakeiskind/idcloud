package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResKeypairsVe;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 接口 Res keypairs ve mapper.
 *
 * @author Chaohong.Mao
 */
@Repository
public interface ResKeypairsVeMapper {

    /**
     * 根据条件删除记录
     *
     * @param example the example
     * @return the int
     */
    int deleteByParams(Criteria example);


    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResKeypairsVe record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResKeypairsVe> selectByParams(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param resKeypairsVe the res keypairs ve
     * @return the list
     */
    ResKeypairsVe selectById(ResKeypairsVe resKeypairsVe);


}
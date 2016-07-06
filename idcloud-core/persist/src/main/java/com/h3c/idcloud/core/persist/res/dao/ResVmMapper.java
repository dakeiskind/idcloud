package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res vm mapper.
 *
 * @author Chaohong.Mao
 */
@Repository
public interface ResVmMapper {
    /**
     * 根据条件查询记录总数
     *
     * @param example the example
     * @return the int
     */
    int countByParams(Criteria example);

    /**
     * 根据条件查询记录总数
     *
     * @param uuid the uuid
     * @return the res vm
     */
    ResVm selectByVmUUID(String uuid);

    /**
     * 根据条件删除记录
     *
     * @param example the example
     * @return the int
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     *
     * @param resVmSid the res vm sid
     * @return the int
     */
    int deleteByPrimaryKey(String resVmSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResVm record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResVm record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResVm> selectByParams(Criteria example);

    /**
     * Select by res host sid res vm.
     *
     * @param allocateResHostSid the allocate res host sid
     * @return the res vm
     */
    ResVm selectByResHostSid(String allocateResHostSid);

    /**
     * Select by power ve list.
     *
     * @param resVeSid the res ve sid
     * @return the list
     */
    List<ResVm> selectByPowerVe(String resVeSid);

    /**
     * 根据主键查询记录
     *
     * @param resVmSid the res vm sid
     * @return the res vm
     */
    ResVm selectByPrimaryKey(String resVmSid);

    /**
     * 统计虚拟机信息
     *
     * @param resTopologySid the res topology sid
     * @return the res vm
     */
    ResVm statisticalTopologyOfVm(String resTopologySid);

    /**
     * 统计计算资源池下面的虚拟机
     *
     * @param resPoolSid the res pool sid
     * @return res vm
     */
    ResVm statisticalComputePoolOfVm(String resPoolSid);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResVm record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResVm record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResVm record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResVm record);

    /**
     * Select vm res sum list.
     *
     * @param example the example
     * @return the list
     */
    List<ResVm> selectVmResSum(Criteria example);

    /**
     * 根据UUID查询对应的虚拟机或者VIOS
     *
     * @param uuid the uuid
     * @return string string
     */
    String selectByUuidInVmVios(String uuid);

    /**
     * Select vm res sum by os type list.
     *
     * @param example the example
     * @return list list
     */
    List<ResVm> selectVmResSumByOsType(Criteria example);

    /**
     * Select unnanotube vm by host list.
     *
     * @param criteria the criteria
     * @return list list
     */
    List<ResVm> selectUnnanotubeVmByHost(Criteria criteria);

    /**
     * Select base info by param list.
     *
     * @param criteria the criteria
     * @return list list
     */
    List<ResVm> selectBaseInfoByParam(Criteria criteria);
}
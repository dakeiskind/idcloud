package com.h3c.idcloud.core.service.system.provider;

import java.util.Date;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.SidMapper;
import com.h3c.idcloud.core.pojo.dto.system.Sid;
import com.h3c.idcloud.core.service.system.api.SidService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class SidServiceImpl implements SidService {
    @Autowired
    private SidMapper sidMapper;
   
    /**
     * 取得最大的流水号
     * 
     * @param sidCategory
     *            流水号类别
     * @return 最大的流水号
     */
    public String getMaxSid(String sidCategory) {
        // 最大流水号
        String maxSid = "";
        // 获取当前流水类别记录
        Criteria params = new Criteria();
        params.put("sidCategory", sidCategory);

        List<Sid> sidList = this.sidMapper.selectByParams(params);
        // 如果没有找到流水类别，返回一个空的流水号
        if (sidList == null || sidList.size() == 0) {
            return null;
        }
        
        Sid sysSid = sidList.get(0);

        // 获取前缀
        String frefix = (sysSid.getSidFrefix() == null) ? "" : sysSid.getSidFrefix();
        // 流水号长度
        int sidLength = sysSid.getSidLength();
        // 流水号记录番号
        Long curNo = 0L;
        if (sysSid.getCurNo() != null) {
            curNo = sysSid.getCurNo();
        }

        Long maxNo = curNo + 1;
        // 处理流水号长度
        String strMaxNo = StringUtil.formatIntToString(maxNo, sidLength);

        // 判断是否跟日期有关
        String isDate = sysSid.getIsDate();
        if ("true".equalsIgnoreCase(isDate)) {
            // 比较当前日期和流水记录日期是否相同，如果不同需要重新计数
            String curDateDB = sysSid.getCurDate();
            String dateFormat = sysSid.getDateFormat();
            String curDate = StringUtil.dateFormat(new Date(), dateFormat);
            if (!curDate.equalsIgnoreCase(curDateDB)) {
                maxNo = 1L;
                strMaxNo = StringUtil.formatIntToString(maxNo, sidLength);
            }
            strMaxNo = curDate + strMaxNo;
            sysSid.setCurDate(curDate);
        }
        sysSid.setCurNo(maxNo);

        // 记录当前最大流水及日期
        this.sidMapper.updateByPrimaryKeySelective(sysSid);

        maxSid = frefix + strMaxNo;

        return maxSid;
    }
    
    /**
     * 查询最大的流水号
     * 
     * @param sidCategory
     *            流水号类别
     * @return 最大的流水号
     */
    public String searchMaxSid(String sidCategory) {
        // 最大流水号
        String maxSid = "";
        // 获取当前流水类别记录
        Criteria params = new Criteria();
        params.put("sidCategory", sidCategory);

        List<Sid> sidList = this.selectByParams(params);
        // 如果没有找到流水类别，返回一个空的流水号
        if (sidList == null || sidList.size() == 0) {
            return null;
        }
        
        Sid sysSid = sidList.get(0);

        // 获取前缀
        String frefix = (sysSid.getSidFrefix() == null) ? "" : sysSid.getSidFrefix();
        // 流水号长度
        int sidLength = sysSid.getSidLength();
        // 流水号记录番号
        Long curNo = 0L;
        if (sysSid.getCurNo() != null) {
            curNo = sysSid.getCurNo();
        }

        Long maxNo = curNo + 1;
        // 处理流水号长度
        String strMaxNo = StringUtil.formatIntToString(maxNo, sidLength);

        // 判断是否跟日期有关
        String isDate = sysSid.getIsDate();
        if ("true".equalsIgnoreCase(isDate)) {
            // 比较当前日期和流水记录日期是否相同，如果不同需要重新计数
            String curDateDB = sysSid.getCurDate();
            String dateFormat = sysSid.getDateFormat();
            String curDate = StringUtil.dateFormat(new Date(), dateFormat);
            if (!curDate.equalsIgnoreCase(curDateDB)) {
                maxNo = 1L;
                strMaxNo = StringUtil.formatIntToString(maxNo, sidLength);
            }
            strMaxNo = curDate + strMaxNo;
        }

        maxSid = frefix + strMaxNo;

        return maxSid;
    }
    
    public int countByParams(Criteria example) {
        int count = this.sidMapper.countByParams(example);
        return count;
    }

    public Sid selectByPrimaryKey(Long sid) {
        return this.sidMapper.selectByPrimaryKey(sid);
    }

    public List<Sid> selectByParams(Criteria example) {
        return this.sidMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long sid) {
        return this.sidMapper.deleteByPrimaryKey(sid);
    }

    public int updateByPrimaryKeySelective(Sid record) {
        return this.sidMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Sid record) {
        return this.sidMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.sidMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Sid record, Criteria example) {
        return this.sidMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Sid record, Criteria example) {
        return this.sidMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Sid record) {
        return this.sidMapper.insert(record);
    }

    public int insertSelective(Sid record) {
        return this.sidMapper.insertSelective(record);
    }
}
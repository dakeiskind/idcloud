package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.CodeMapper;
import com.h3c.idcloud.core.pojo.dto.system.Code;
import com.h3c.idcloud.core.service.system.api.CodeService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0")
@Component
public class CodeServiceImpl implements CodeService {
    @Autowired
    private CodeMapper codeMapper;

    private static Logger logger = LoggerFactory.getLogger(CodeServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.codeMapper.countByParams(example);
        logger.debug("count: {}", count);
        System.out.println("count: {====}"+count);
        System.out.println("================================");
        return count;
    }

    public Code selectByPrimaryKey(Long codeSid) {
        return this.codeMapper.selectByPrimaryKey(codeSid);
    }

    public List<Code> selectByParams(Criteria example) {
        return this.codeMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long codeSid) {
        return this.codeMapper.deleteByPrimaryKey(codeSid);
    }

    public int updateByPrimaryKeySelective(Code record) {
        return this.codeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Code record) {
        return this.codeMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.codeMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Code record, Criteria example) {
        return this.codeMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Code record, Criteria example) {
        return this.codeMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Code record) {
        return this.codeMapper.insert(record);
    }

    public int insertSelective(Code record) {
        return this.codeMapper.insertSelective(record);
    }

    @Override
    public List<Code> findImageSoftWare(Criteria example) {
        return this.codeMapper.findImageSoftWare(example);
    }

    @Override
    public String getSoftwareName(String softwareVersion) {
        String softwareName = null;
        Code code= this.getCodeByValue(softwareVersion, WebConstants.CodeCategroy.SOFTWARE_VERSION);
        if(code != null) {
            softwareName = code.getCodeDisplay();
        }
        return softwareName;
    }

    @Override
    public Code getCodeByValue(String value, String codeCategory) {
        Code code = null;
        Criteria criteria = new Criteria();
        criteria.put("enabled", WebConstants.CodeEnable.ABLE);
        criteria.put("codeCategory", codeCategory);
        criteria.put("codeValue", value);
        List<Code> codeList = this.selectByParams(criteria);
        if(codeList.size() > 0) {
            code = codeList.get(0);
        }
        return code;
    }

    @Override
    public List<Code> findParentCodeByCodeVaule(Criteria example) {
        return this.codeMapper.findParentCodeByCodeVaule(example);
    }

    @Override
    public List<Code> getParentCodeByCodeVaule(Criteria example) {
        return this.codeMapper.getParentCodeByCodeVaule(example);
    }
}
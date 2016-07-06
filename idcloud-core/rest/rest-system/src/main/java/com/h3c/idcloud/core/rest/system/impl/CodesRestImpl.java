/**
 *
 */
package com.h3c.idcloud.core.rest.system.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.system.Code;
import com.h3c.idcloud.core.rest.system.CodesRest;
import com.h3c.idcloud.core.service.system.api.CodeService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author jipeigui
 */
@Component
public class CodesRestImpl implements CodesRest {

    Logger log = Logger.getLogger(this.getClass());

    /**
     * 数据字典Service
     */
    /*@Autowired
    private CodeMapper codeMapper;*/

    @Reference(version = "1.0.0")
    private CodeService codeService;

	/*@Reference(version = "1.0.0")
    private ResImageService imageService;*/

	/*@Autowired
    private ResSoftwareService resSoftwareService;*/

    /**
     * 根据参数查询数据字典数据
     */
    @Override
    public Response getCodesByParams(String params) {
        String json = "";

        Map<String, Object> map = new HashMap<String, Object>();
        map = JsonUtil.fromJson(params, Map.class);
        Criteria criteria = new Criteria();
        if (map.isEmpty()) {
            criteria.put("enabled", WebConstants.CodeEnable.ABLE);
        } else {
            map.put("enabled", WebConstants.CodeEnable.ABLE);
            criteria.setCondition(map);
        }
        List<Code> codes = this.codeService.selectByParams(criteria);
        json = JsonUtil.toJson(new RestResult(codes));

        return Response.ok(json).build();
    }

    @Override
    public Response getCodesByOsVersionParams(String params) {
        String json = "";

        Map<String, Object> map = new HashMap<String, Object>();
        map = JsonUtil.fromJson(params, Map.class);
        Criteria criteria = new Criteria();
        if (map.isEmpty()) {
            criteria.put("enabled", WebConstants.CodeEnable.ABLE);
            criteria.put("codeCategory", "OS_VERSION");
        } else {
            map.put("enabled", WebConstants.CodeEnable.ABLE);
            map.put("codeCategory", "OS_VERSION");
            criteria.setCondition(map);
        }
        List<Code> codes = this.codeService.selectByParams(criteria);
        json = JsonUtil.toJson(new RestResult(codes));

        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response findUserStatus(String params) {
        String json = "";
        try {
//			Map<String, Object> map=new HashMap<String, Object>();
//			map = JsonUtil.fromJson(params, Map.class);
            Criteria criteria = new Criteria();
//			if(map.isEmpty()){
            criteria.put("enabled", WebConstants.CodeEnable.ABLE);
            criteria.put("codeCategory", "USER_STATUS");
//			}
            List<Code> codes = this.codeService.selectByParams(criteria);
            List<Code> userStatus = new ArrayList<Code>();
            for (int i = 0; i < codes.size(); i++) {
                if ("0".equals(codes.get(i).getCodeValue())) {
                    userStatus.add(codes.get(i));
                }
                if ("1".equals(codes.get(i).getCodeValue())) {
                    userStatus.add(codes.get(i));
                }
            }
            Code code = new Code();
            code.setCodeValue("全部");
            code.setCodeDisplay("全部");
            code.setEnabled("1");
            userStatus.add(0, code);
            json = JsonUtil.toJson(new RestResult(userStatus));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(Status.OK).entity(json).build();
    }


    /**
     * 根据数据字典类型查询数据字典数据
     */
    @Override
    public Response getCodesByCategory(String codeCategory) {

        Criteria criteria = new Criteria();
        criteria.put("enabled", WebConstants.CodeEnable.ABLE);
        criteria.put("codeCategory", codeCategory);
        List<Code> codes = this.codeService.selectByParams(criteria);
        String json = JsonUtil.toJson(new RestResult(codes));
        return Response.ok(json).build();
    }

    /**
     * 查寻镜像可部署的数据库和中间件数据
     */
    @Override
    public Response findImageSoftWare(String params) {
        String json = "";
//		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<Code> codeList = new ArrayList<Code>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map = JsonUtil.fromJson(params, Map.class);
            Criteria criteria = new Criteria();
            if (map.isEmpty()) {
                criteria.put("enabled", WebConstants.CodeEnable.ABLE);
                criteria.put("codeCategory", "SOFTWARE_CATEGORY");
                List<Code> codes = this.codeService.findImageSoftWare(criteria);
                for (int i = 0; i < codes.size(); i++) {
                    Map<String, Object> serMap = new HashMap<String, Object>();
                    if ("db".equals(codes.get(i).getSoftWareValue())) {
                        codeList.add(codes.get(i));
                    }
                    if ("mw".equals(codes.get(i).getSoftWareValue())) {
                        codeList.add(codes.get(i));
                    }
                }
                json = JsonUtil.toJson(new RestResult(codeList));
                return Response.status(Status.OK).entity(json).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(Status.OK).entity(json).build();
    }


    @Override
    public Response getCodesByEquipCategory(String params) {
        String json = "";
        List<Code> codelist = new ArrayList<Code>();

        Map<String, Object> map = new HashMap<String, Object>();
        map = JsonUtil.fromJson(params, Map.class);
        Criteria criteria = new Criteria();
        if (map.isEmpty()) {
            criteria.put("enabled", WebConstants.CodeEnable.ABLE);
        } else {
            map.put("enabled", WebConstants.CodeEnable.ABLE);
            criteria.setCondition(map);
        }
        List<Code> codes = this.codeService.selectByParams(criteria);
        for (int i = 0; i < codes.size(); i++) {
            if ("RS".equals(codes.get(i).getCodeValue())) {
                codelist.add(codes.get(i));
            }
            if ("BS".equals(codes.get(i).getCodeValue())) {
                codelist.add(codes.get(i));
            }
        }
        json = JsonUtil.toJson(new RestResult(codelist));

        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response findSoftWareByImage(String params) {
        String json = "";
        List<Code> codeList = new ArrayList<Code>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map = JsonUtil.fromJson(params, Map.class);
            Criteria criteria = new Criteria();
            if (!map.isEmpty()) {
                if (!StringUtil.isNullOrEmpty(map.get("osVersion"))) {
                    Criteria example = new Criteria();
                    example.put("osVersion", map.get("osVersion"));
                    //得到该版本的模板所支持所有的软件
					/*List<ResImage> images = resImageMapper.selectByParams(example);
					List<String> softs = new ArrayList<String>();
					//取得模版上带的软件
					if(!CollectionUtils.isEmpty(images)){
						for (ResImage image : images) {
							if(!StringUtil.isNullOrEmpty(image.getInstalledSoftware())){
								List<String> softversions = Arrays.asList(image.getInstalledSoftware().split(","));
								softs.removeAll(softversions);
								softs.addAll(softversions);
							}
						}
					}*/
                    //取得该操作系统可以自动化部署的软件
					/*List<ResSoftware> softWares = resSoftwareService.selectByParams(example);
					if(!CollectionUtils.isEmpty(softWares)){
						List<String> softWareVersions = new ArrayList<String>();
						for (ResSoftware software : softWares) {
							softWareVersions.add(software.getSoftwareVersion());
						}
						softs.removeAll(softWareVersions);
						softs.addAll(softWareVersions);
					}
					criteria.put("softVersionList", softs);
					*/
                }
                if (!StringUtil.isNullOrEmpty(map.get("codeCategory"))) {
                    criteria.put("codeCategory", map.get("codeCategory"));
                }
                if (!StringUtil.isNullOrEmpty(map.get("parentCodeValue"))) {
                    criteria.put("parentCodeValue", map.get("parentCodeValue"));
                }
                criteria.put("enabled", WebConstants.CodeEnable.ABLE);
                List<Code> codes = this.codeService.selectByParams(criteria);
                json = JsonUtil.toJson(new RestResult(codes));
                return Response.status(Status.OK).entity(json).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response findImageSoftWareTypeByCodeValue(
            @PathParam("installedSoftware") String installedSoftware) {
        Criteria criteria = new Criteria();
        List<Code> softType_list = new ArrayList<Code>();

        criteria.put("codeValue", installedSoftware);
        List<Code> code_list = this.codeService.selectByParams(criteria);
        for (Code code : code_list) {
            criteria = new Criteria();
            criteria.put("codeValue", code.getParentCodeValue());
            softType_list = this.codeService.selectByParams(criteria);
        }
        String json = JsonUtil.toJson(new RestResult(softType_list));
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response findOsVersion(@Context HttpServletRequest request) {
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param, "");
        param.put("codeCategory", "OS_VERSION");
        System.out.println("============================");
        System.out.println(JsonUtil.toJson(param));
        List<Code> list = this.codeService.selectByParams(param);
        int total = this.codeService.countByParams(param);
        String json = JsonUtil.toJson(new BaseGridReturn(total, list));
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response creatOsVersion(Code code) {
        String returnJson;
        int result = 0;
        code.setCodeCategory("OS_VERSION");
        WebUtil.prepareInsertParams(code);
        result = this.codeService.insertSelective(code);
		/*if(1 == result){
			returnJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil
					.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
		}else{
			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
		}
		return Response.status(Status.OK).entity(returnJson).build();
		*/
        return Response.ok().build();
    }


    @Override
    public Response deleteUser(@QueryParam("codeSids") String codeSids) {
        String json = "";
        int result = 0;
        String[] sids = codeSids.split(",");
        if (sids != null && sids.length > 0) {
            for (int i = 0; i < sids.length; i++) {
                result += this.codeService.deleteByPrimaryKey(Long.parseLong(sids[i]));
            }
        }
		/*if(result > 0){
			json = JsonUtil.toJson(new RestResult(RestResult.SUCCESS,
					WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
		}else{
			json = JsonUtil.toJson(new RestResult(RestResult.FAILURE,
					WebUtil.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
		}
		return Response.status(Status.OK).entity(json).build();*/
        return Response.ok().build();
    }


    @Override
    public Response updateSystemCode(Code code) {
        String returnJson = "";
        WebUtil.prepareUpdateParams(code);
        int result = this.codeService.updateByPrimaryKeySelective(code);
		/*if(1==result){
			returnJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil
					.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
		}else{
			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
		}
		return Response.status(Status.OK).entity(returnJson).build();*/
        return Response.ok().build();
    }


    @Override
    public Response createOsType(Code code) {
        String returnJson;
        int result = 0;
        Criteria criteria = new Criteria();
        criteria.put("codeCategory", "OS_TYPE");
        List<Integer> sort = new ArrayList<Integer>();
        List<Code> sortlist = this.codeService.selectByParams(criteria);
        if (sortlist.size() > 0) {
            for (int i = 0; i < sortlist.size(); i++) {
                sort.add(sortlist.get(i).getSort());
            }
        }
        int maxSort = Collections.max(sort);
//		String[] array = (String[])sort.toArray(new String[sort.size()]);  
        code.setCodeCategory("OS_TYPE");
        code.setCodeDisplay(code.getCodeValue());
        code.setSort(maxSort + 1);
        WebUtil.prepareInsertParams(code);
        result = this.codeService.insertSelective(code);
		/*if(1 == result){
			returnJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil
					.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
		}else{
			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
		}
		return Response.status(Status.OK).entity(returnJson).build();*/
        return Response.ok().build();
    }

}

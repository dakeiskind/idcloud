package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairCreateResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairListGetResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.keyVo.KeyVo;
import com.h3c.idcloud.core.pojo.dto.res.ResKeypairs;
import com.h3c.idcloud.core.pojo.dto.system.Attachments;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.rest.res.ResKeyPairsRest;
import com.h3c.idcloud.core.service.res.api.ResKeypairsService;
import com.h3c.idcloud.core.service.system.api.AttachmentService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.UuidConstants;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.*;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.io.*;
import java.util.*;

@Component
public class ResKeyPairsRestImpl implements ResKeyPairsRest {

	@Reference(version = "1.0.0")
	MgtObjService mgtObjService;
	@Reference(version = "1.0.0")
	ResKeypairsService resKeypairsService;
	@Reference(version = "1.0.0")
	AttachmentService attachmentService;
	@Reference(version = "1.0.0")
	UserService userService;


	@Override
	public Response findKeypairsByMgtObjSid(String mgtObjSid) {
		Criteria example = new Criteria();
		example.put("mgtObjSid",mgtObjSid);
		example.setOrderByClause("CREATED_DT DESC");
		List<ResKeypairs> keypairs = this.resKeypairsService.selectByParams(example);
		List<ResKeypairs> keypairsList = new ArrayList<ResKeypairs>();
		for (ResKeypairs resKeypairs : keypairs) {
			ResKeypairs resKeypairs1 = new ResKeypairs();
			resKeypairs1.setResKeypairsSid(resKeypairs.getResKeypairsSid());
			resKeypairs1.setKeypairsName(resKeypairs.getKeypairsName());
			keypairsList.add(resKeypairs1);
		}
		String json = JsonUtil.toJson(new RestResult(keypairsList));
		return Response.ok(json).build();
	}

	/**
	 * 查询秘钥对
	 */
	@Override
	public Response findKeypairs(String params) {
		Map<String,Object> map = JsonUtil.fromJson(params,Map.class);
		Criteria example = new Criteria();
		example.put("mgtObjSid", map.get("mgtObjSid"));
		example.put("keypairsNameLike",map.get("keypairsName"));
		example.setOrderByClause("CREATED_DT DESC");
		List<ResKeypairs> Keypairs = this.resKeypairsService.selectByParams(example);
		for(int i=0;i<Keypairs.size();i++){
			ResKeypairs keypairs = Keypairs.get(i);
			keypairs.setKeypairsNameAndSid(keypairs.getResKeypairsSid()+"<br/>"+keypairs.getKeypairsName());
		}
		String json = JsonUtil.toJson(new RestResult(Keypairs));
		return Response.ok(json).build();
	}
	
	/**
	 * 删除秘钥对
	 */
	@Override
	public Response deleteKeypairs(@Context HttpServletRequest request,String[] resKeypairsSids) {
		String resultJson = "";
		int[] result = new int[resKeypairsSids.length];
		AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
		ResCommonInst resCommonInst = new ResCommonInst();
		resCommonInst.setUserAccount(authUser.getAccount());
		resCommonInst.setMgtObjSid(authUser.getMgtObjSid());
//		todo 没有ZoneId 等以后有了加上
//		resCommonInst.setZoneId();
		//循环操作
		for(int i = 0;i < resKeypairsSids.length;i++){
			Map<String,Object> map = new HashMap<>();
			map.put("keypairsSid",resKeypairsSids[i]);
			resCommonInst.setResSpecParam(JsonUtil.toJson(map));
			KeypairDeleteResult keypairDeleteResult = this.resKeypairsService.deleteKeypairs(resCommonInst);
//			if (null != keypairDeleteResult && keypairDeleteResult.isSuccess()) {
				result[i] = this.resKeypairsService.deleteByPrimaryKey(resKeypairsSids[i]);
//			}
		}
		//如有一条删除失败 则认为全部删除失败(以后改进)
		for(int i = 0; i < result.length;i++){
			if(result[i] != 1){
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
							.getMessage(WebConstants.MsgCd.ERROR_OPERATE_FAILURE), null));
				break;
			}else if(i == (result.length-1)){
				resultJson=JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
			}
		}
		return Response.ok(resultJson).build();
	}

	/**
	 * 创建秘钥对
	 */
	@Override
	public Response creatKeypairs(@Context HttpServletRequest request,String params) {
		String resultJson = "";
		Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
		AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
		ResCommonInst resCommonInst = new ResCommonInst();
		resCommonInst.setResSpecParam(params);
		resCommonInst.setMgtObjSid(authUser.getMgtObjSid());
		resCommonInst.setUserAccount(authUser.getAccount());
		//得到私钥
		String privateKey = resKeypairsService.createKeypairs(resCommonInst);
		//将附件信息保存到数据库
		Attachments attach = new Attachments();
		attach.setOriginalName(map.get("keypairsName").toString()+".pem");
		attach.setExtName("pem");
		String attachmentName = UUID.randomUUID().toString();
		attach.setAttachmentName(attachmentName);
		String dataPath = DateFormatUtils.format(new Date(), "yyyy-MM/dd");
		String attachmentUrl =  dataPath + "/" + attachmentName +".pem";
		attach.setAttachmentUrl(attachmentUrl);
		attach.setUploadDate(new Date());
		int result = attachmentService.insertSelective(attach);

		if(1 == result){
			String uploadPath = "privateKey/"+PropertiesUtil.getProperty("upload.path")+attachmentUrl;
			File filename = new File(uploadPath);
			if (!filename.getParentFile().exists()) {
				filename.getParentFile().mkdirs();
			}
			File filepath = new File(uploadPath);
			try {
				if(!filepath.exists()){
					filepath.createNewFile();
				}
				FileUtil.writeTxtFile(privateKey, filepath);
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS), attach.getAttachmentName()));
			} catch (Exception e) {
				e.printStackTrace();
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
			}
		}
		return Response.ok(resultJson).build();
	}

	/**
	 * 下载密钥私钥
	 */
	@Override
	public Response exportprivateKeys(@PathParam("attachmentName") String attachmentName,@Context HttpServletResponse servletResponse) {

		Criteria param = new Criteria();
		param.put("attachmentName",attachmentName);
		List<Attachments> attach = this.attachmentService.selectByParams(param);
		if(attach.get(0) != null){
			String path = "privateKey/"+PropertiesUtil.getProperty("upload.path")+attach.get(0).getAttachmentUrl();
			FileUtil.fileDownLoad(servletResponse, path, attach.get(0).getOriginalName());
			POIUtil excelUtil = new POIUtil();
			excelUtil.deleteFile(path);
			this.attachmentService.deleteByParams(param);//附件下载成功后 删除附件表的数据
		}
		return null;
	}


	/**
	 * 导入秘钥对
	 */
	@Override
	public Response importKeypairs(@Context HttpServletRequest request,String params) {
		String resultJson = "";
		AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
		try {
			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
			String mgtObjSid = authUser.getMgtObjSid().toString();
			String importkeypairName = map.get("keypairsName").toString();
			String publicKeys = map.get("publicKeys").toString();

			ResCommonInst resCommonInst = new ResCommonInst();
			resCommonInst.setResSpecParam(params);
//			todo 没有ZoneId 等以后有了加上
//			resCommonInst.setZoneId();
			resCommonInst.setUserAccount(authUser.getAccount());
			resCommonInst.setMgtObjSid(authUser.getMgtObjSid());
			KeypairCreateResult keypairCreateResult = this.resKeypairsService.importKeypairs(resCommonInst);
			if (null != keypairCreateResult && keypairCreateResult.isSuccess()) {
				ResKeypairs keypairs = new ResKeypairs();
				keypairs.setResKeypairsSid(UuidUtil.getShortUuid(UuidConstants.PrefixCode.KEYPAIR));
				keypairs.setKeypairsName(importkeypairName);
				keypairs.setMgtObjSid(Long.valueOf(mgtObjSid));
				keypairs.setFingerprint(keypairCreateResult.getKeyVo().getFingerprint());
				keypairs.setPrivateKey(publicKeys);
				keypairs.setOwnerId(authUser.getAccount());
				WebUtil.prepareInsertParams(keypairs);
				int result = this.resKeypairsService.insertSelective(keypairs);
				if(1==result){
					resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
							.getMessage(WebConstants.MsgCd.INFO_OPERATE_SUCCESS), null));
				}else {
					resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
							.getMessage(WebConstants.MsgCd.ERROR_OPERATE_FAILURE), null));
				}
			}else {
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage(WebConstants.MsgCd.ERROR_OPERATE_FAILURE), null));
			}
		}catch(Exception e){
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_OPERATE_FAILURE), null));
		}
		return Response.ok(resultJson).build();
	}

	/**
	 * 导出租户下所有秘钥对
	 * 
	 */
	@Override
	public Response exportAllKeypairs(@PathParam("userSid") String userSid,@Context HttpServletResponse servletResponse) {
		String resultJson = "";
		User user = userService.selectByPrimaryKey2(Long.valueOf(userSid));
		try {
			MgtObj mgtObj = this.mgtObjService.selectByPrimaryKey(user.getMgtObjSid());
			ResCommonInst resCommonInst = new ResCommonInst();
			resCommonInst.setMgtObjSid(user.getMgtObjSid());
			resCommonInst.setUserAccount(user.getAccount());
//			todo 没有ZoneId 等以后有了加上
//			resCommonInst.setZoneId();
			KeypairListGetResult keypairListGetResult = this.resKeypairsService.exportAllKeypairs(resCommonInst);

//			对导出excel表格进行测试
//			KeypairListGetResult keypairListGetResult = new KeypairListGetResult();
//			List<KeyVo> keyVos = new ArrayList<>();
//			KeyVo keyVo = new KeyVo();
//			keyVo.setName("test1");
//			keyVo.setFingerprint("8d:54:56:b9:05:6b:e7:f5:d4:8c:00:1a:c3:ba:ce:56");
//			keyVo.setPublicKey("ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCgelY1gfOTHYxMnH23esZWtu6BM2VezJieSp8muHyPm33I03VkgUROiOHXild04Nk");
//			keyVos.add(keyVo);
//			keypairListGetResult.setKeyVos(keyVos);
//			keypairListGetResult.setSuccess(true);
			if(null!=keypairListGetResult && keypairListGetResult.isSuccess()){

				POIUtil excelUtil = new POIUtil();
				//1.显示的表头文本
				String[] headerTitle = new String[] { "SSH公钥/私钥名称", "指纹", "公钥"};
				//2.表头对应的字段
				String[] headerField = new String[] {"keypairsName", "fingerprint", "publicKey"};
				//3.设置表头每列的宽度
				short[] headerWidth = new short[] { 30 * 256, 50 * 256, 120 * 256};
				//4.设置显示的具体数据列表
				List<Map> dataList = new ArrayList<Map>();
				Map<String, String> dataMap = null;
				for(int i = 0; i < keypairListGetResult.getKeyVos().size(); i++){
					dataMap = new HashMap<String, String>();
					dataMap.put("keypairsName", keypairListGetResult.getKeyVos().get(i).getName());
					dataMap.put("fingerprint", keypairListGetResult.getKeyVos().get(i).getFingerprint());
					dataMap.put("publicKey", keypairListGetResult.getKeyVos().get(i).getPublicKey());
					dataList.add(dataMap);
				}
				//5.sheet名称，可以多个sheet
				String[] sheetName = new String[]{"SSH公钥私钥"};
				//6.输出的文件名称
				String date = StringUtil.dateFormat(new Date(), StringUtil.DF_YMD);
//				String systemName = PropertiesUtil.getProperty("system.name");
//				String fileName = systemName+"-"+mgtObj.getName()+"-SSH公钥私钥-"+date+".xls";
				String fileName = mgtObj.getName()+"-SSH公钥私钥-"+date+".xls";
				//7.调用通用方法，生成excel文件
				Integer[] type = new Integer[]{};
				excelUtil.doFromDataToExecl(servletResponse, headerTitle, headerField, headerWidth, dataList, sheetName, fileName,type);
			}else{
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage(WebConstants.MsgCd.ERROR_OPERATE_FAILURE)));
				return Response.ok(resultJson).build();
			}
		}catch(Exception e){
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_OPERATE_FAILURE), null));
			return Response.ok(resultJson).build();
		}
		return null;
	}
	
	/**
	 * 检测秘钥对名称是否重复
	 */
	@Override
	public Response checkKeypairs(@PathParam("keypairsName")String keypairsName) {
		String returnJson = "";
		Criteria criteria = new Criteria();
		criteria.put("keypairsName", keypairsName);
		List<ResKeypairs> keypairs = this.resKeypairsService.selectByParams(criteria);
		if(keypairs.size()>0){
			returnJson = JsonUtil.toJson(
					new RestResult(RestResult.Status.FAILURE,
							"name repetition"));
		} else {
			returnJson = JsonUtil.toJson(
					new RestResult(RestResult.Status.SUCCESS,
							"name available"));
		}
		return Response.ok(returnJson).build();
	}

	/**
	 * 修改密钥名称与描述
	 *
	 */
	@Override
	public Response modifyKeypairs(ResKeypairs resKeypairs){
		String returnJson = "";
		int result = this.resKeypairsService.updateByPrimaryKeySelective(resKeypairs);
		if (1 == result) {
			returnJson = JsonUtil.toJson(
					new RestResult(RestResult.Status.SUCCESS,
							WebUtil.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
		} else {
			returnJson = JsonUtil.toJson(
					new RestResult(RestResult.Status.FAILURE,
							WebUtil.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
		}

		return Response.ok(returnJson).build();
	}

	/**
	 * 根据名称查看秘钥对
	 *
	 */
	@Override
	public Response findKeypairsByName(String params) {
		String resultJson = "";
//		ResKeypairs keypair = new ResKeypairs();
//		try {
//			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//			String mgtObjSid = map.get("mgtObjSid").toString();
//			String resKeypairsSid = map.get("resKeypairsSid").toString();
//			MgtObj mgtObj = this.mgtObjService.selectByPrimaryKey(Long.valueOf(mgtObjSid));
//			ResKeypairs resKeypairs = this.resKeypairsService.selectByPrimaryKey(resKeypairsSid);
//
//			KeypairGetResult keypairGetResult = this.resKeypairsService.findKeypairsByName(resKeypairs,mgtObj);
//			if(null!=keypairGetResult && keypairGetResult.isSuccess()){
////				ResKeypairs keypair = new ResKeypairs();
//				keypair.setKeypairsName(keypairGetResult.getKeyVo().getName());
//				keypair.setFingerprint(keypairGetResult.getKeyVo().getFingerprint());
//				keypair.setPublicKey(keypairGetResult.getKeyVo().getPublicKey());
//				keypair.setPrivateKey(keypairGetResult.getKeyVo().getPrivateKey());
//			}else{
//				resultJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil
//						.getMessage(WebConstants.MsgCd.ERROR_OPERATE_FAILURE), null));
//				return Response.status(Status.OK).entity(resultJson).build();
//			}
//
//		}catch(Exception e){
//			resultJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil
//					.getMessage(WebConstants.MsgCd.ERROR_OPERATE_FAILURE), null));
//			return Response.status(Status.OK).entity(resultJson).build();
//		}
		String keypair="";
		return Response.status(Status.OK).entity(keypair).build();
	}
	
}

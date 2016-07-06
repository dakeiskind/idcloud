<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/biz-mgt-model.js"></script>
    <style type="text/css">
		#jqxExpanderResBiz .ul{
			margin:0px;
    	    padding:0px;
    		width:99%;
    		margin-left:0.5%;
    		height:30px;
    		background:#F4F4F4;
    		list-style:none;
    		border:1px solid #E5E5E5;
    		border-top:0px;
		}
		
		#jqxExpanderResBiz .resoure{
    	    margin:1px; 
    	    height:26px;
    	    line-height:30px;
    	    padding:1px 8px 1px 8px;
    	    cursor:pointer;
    		float:left;
    	}
    	
    	#jqxExpanderResBiz .resoure:hover{
    		background:#DEDEDE;
    	}
    	
    	#jqxExpanderResBiz .show{
    		background:#fff;
    		border:1px solid #e5e5e5;
    		border-bottom:0px;
    		height:27px;
    	}
		
		#jqxExpanderResBiz .content{
    		width:99%;
    		margin-left:0.5%;
    		height:86%;
    		display:none;
    		border:1px solid #e5e5e5;
    		border-top:0px;
    		overflow-y:auto;
    	}
    	#jqxExpanderResBiz .contentShow{
    		display:block;
    	}
    	#jqxExpanderResBiz .jqx-panel{
    		border:0px;
    	}
    	#jqxExpanderResBiz .jqx-tree-dropdown-root{
			width:100%;
    	}
    </style>
    </head>
<body>
	<div id='jqxExpanderBiz' style="border:1px;float: left;width: 20%;height:100%;">
		<div>业务类型管理</div>
		<div >
			<div style="border: none;" id='jqxTreeBiz'>
<!-- 				<div id='jqxBizMenu'> -->
<!-- 		            <ul> -->
<!-- 		                <li>新增</li> -->
<!-- 		                <li>编辑</li> -->
<!-- 		   		        <li>删除</li> -->
<!-- 		            </ul> -->
<!-- 		         </div> -->
			</div>
		</div>
	</div>
	
	<div id='jqxTabsBiz' style="display: inline; float: left; margin-left: 3px;">
		<ul>
			<li style="font-size: 14px">业务类型</li>
		</ul>
		<div>
			<div style="width: 69%;float:left;">
			         <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
				           <table border="0" height="100%" cellspacing="0" cellpadding="2">
				            	<tr>
				            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;项目名称：</td>
				           			<td><input type="text" id="search-biz-type-name" />&nbsp;&nbsp;</td>
				           			<td align="right" nowrap="nowrap">业务属性：</td>
				           			<td><input type="text" id="search-biz-type-property"/>&nbsp;&nbsp;</td>
				           			<td><a id="search-biz-type-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
				            	</tr>
				            </table>
				    </div>
					<div id='jqxgridBiz' style="margin-left: 1%"></div>
			</div>
			
 			<div id='jqxExpanderResBiz' style="width:30%;height:98%; float:left;">
 			   <div style="margin:0px;padding:0px;width:99%;height:35px;margin-top:5px;margin-left:0.5%;line-height:35px;background:#F4F4F4;border:1px solid #E5E5E5;">
 			   		 <a style="margin-left: 5px;" id="bizTypeResBtn">&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>保存关联</a>
 			   </div>
				<ul class="ul">
			        <li class="resoure show" style="margin-left: 15px;display:none">资源分区</li>
			        <li class="resoure show" style="margin-left: 15px;display:none">计算资源</li>
			        <li class="resoure show" style="display:none">网络资源</li>
			    </ul>
			    <div class="content contentShow">
			       <div id='jqxTreeRz' style="width:100%;height:100%;border:0px;overflow:hidden;"></div> 
			    </div>
			    <div class="content contentShow" style="display:none">  
			       <div id='jqxTreeCompute' style="width:100%;height:100%;border:0px;overflow:hidden"></div> 
			    </div>
			    <div class="content contentShow" style="display:none">
			       <div id='jqxTreeNetwork' style="width:100%;height:100%;border:0px;overflow:hidden"></div>
			    </div>
			</div>
		</div>
	</div>

	<div id="addBizTypeWindow" style="display:none">
     	<div>新增业务类型</div>
     	<div id="addBizTypeForm" style="overflow: hidden;">
     	<input type="hidden" data-name="bizSid" id="add-bizSid"/>
     	<input type="hidden" data-name="parentBizSid" id="add-parentBizSid"/>
     	<input type="hidden" data-name="level" id="add-level"/>
     	
         <table border="0" width="100%" cellspacing="5" cellpadding="0">
             <tr>
                 <td align="right"><font style="color:red">*</font>项目名称:</td>
                 <td align="left"><input type="text" data-name="bizName" id="add-bizName"/></td>
             </tr> 
             <tr>
				 <td align="right">业务属性:</td>
                 <td align="left" ><input type="text" data-name="parentBizName" id="add-parentBizName"/></td>
             </tr>
             <%-- 
             <tr>
				 <td align="right">排序:</td>
                      <td align="left"><div data-name="sortRank" id='add-biz-sortRank'></div></td>
             </tr>
             --%>
             <tr>
				 <td align="right">状态:</td>
                 <td align="left"><div data-name="status" id='add-biz-status'></div></td>
             </tr>
             <tr>             
                 <td align="right">业务类型描述:</td>
                 <td align="left" colspan="3"><textarea data-name="bizDesc" id="add-bizDesc"></textarea></td>
             </tr>
             <tr>             
                 <td align="right">维护人员:</td>
                 <td align="left" colspan="3"><input type="text" data-name="owner" id="add-owner"/></td>
             </tr>
             <tr>             
                 <td align="right"><font style="color:red">*</font>维护人员电话:</td>
                 <td align="left" colspan="3"><input type="text" data-name="ownerTel" id="add-ownerTel"/></td>
             </tr>
             <tr class="pm">             
                 <td align="right">项目经理:</td>
                 <td align="left" colspan="3"><input type="text" data-name="pm" id="add-pm"/></td>
             </tr>
             <tr class="pm">             
                 <td align="right"><font style="color:red">*</font>项目经理电话:</td>
                 <td align="left" colspan="3"><input type="text" data-name="pmTel" id="add-pmTel"/></td>
             </tr>
             <tr class="pm">             
                 <td align="right">项目经理邮箱:</td>
                 <td align="left" colspan="3"><input type="text" data-name="pmEmail" id="add-pmEmail"/></td>
             </tr>
             
              <tr class="hide">             
                 <td align="right">开通网络类型:</td>
                 <td align="left" colspan="3"><div data-name="networktype" id="add-networktype"></div></td>
             </tr>
             
             <tr class="hide">             
                 <td align="right">是否开通防火墙:</td>
                 <td align="left" colspan="3"><div data-name="fwPort" id="add-fwPort"></div></td>
             </tr>
             <tr class="hide">             
                 <td align="right">是否商户合同号:</td>
                 <td align="left" colspan="3"><div data-name="isBizCont" id="add-isBizCont"></div></td>
             </tr>
             <tr class="hide">             
                 <td align="right">是否项目立项号:</td>
                 <td align="left" colspan="3"><div data-name="isProNO" id="add-isProNo"></div></td>
             </tr>
             <tr class="hide">
                 <td align="right">是否项目附件:</td>
                 <td align="left" colspan="3"><div data-name="isProAttach" id="add-isProAttach"></div></td>
             </tr> 
             <tr>
                 <td align="right" colspan="4">
                 <input style="margin-right: 5px;" type="button" id="saveAddBizType" value="保存" />
                 <input id="cancelAddBizType" type="button" value="取消" /></td>
             </tr>
         </table>
   			</div>
	</div>
			
	<div id="editBizTypeWindow" style="display:none">
     	<div>编辑业务类型</div>
     	<div id="editBizTypeForm" style="overflow: hidden;">
     	<input type="hidden" data-name="orgSid" id="edit-bizSid"/>
     	
        <table border="0" width="100%" cellspacing="5" cellpediting="0">
             <tr>
                 <td align="right"><font style="color:red">*</font>项目名称:</td>
                 <td align="left"><input type="text" data-name="bizName" id="edit-bizName"/></td>
             </tr> 
             <tr>
				 <td align="right">业务属性:</td>
                 <td align="left" ><input type="text" data-name="parentBizName" id="edit-parentBizName"/></td>
             </tr>
             <%-- 
             <tr>
				 <td align="right">排序:</td>
                      <td align="left"><div data-name="sortRank" id='edit-biz-sortRank'></div></td>
             </tr>
             --%>
             <tr>
				 <td align="right">状态:</td>
                 <td align="left"><div data-name="status" id='edit-biz-status'></div></td>
             </tr>
             <tr> 
                 <td align="right">创建时间:</td>
                 <td align="left" colspan="3"><div data-name="createDt" id="edit-createDt"></div></td>
             </tr>
             <tr>             
                 <td align="right">业务类型描述:</td>
                 <td align="left" colspan="3"><textarea data-name="bizDesc" id="edit-bizDesc"></textarea></td>
             </tr>
             <tr>             
                 <td align="right">维护人员:</td>
                 <td align="left" colspan="3"><input type="text" data-name="owner" id="edit-owner"/></td>
             </tr>
             <tr>             
                 <td align="right"><font style="color:red">*</font>维护人员电话:</td>
                 <td align="left" colspan="3"><input type="text" data-name="ownerTel" id="edit-ownerTel"/></td>
             </tr>
             <tr class="pm">             
                 <td align="right">项目经理:</td>
                 <td align="left" colspan="3"><input type="text" data-name="pm" id="edit-pm"/></td>
             </tr>
             <tr class="pm">             
                 <td align="right"><font style="color:red">*</font>项目经理电话:</td>
                 <td align="left" colspan="3"><input type="text" data-name="pmTel" id="edit-pmTel"/></td>
             </tr>
             <tr class="pm">             
                 <td align="right">项目经理邮箱:</td>
                 <td align="left" colspan="3"><input type="text" data-name="pmEmail" id="edit-pmEmail"/></td>
             </tr>
              <tr class="hide">             
                 <td align="right">开通网络类型:</td>
                 <td align="left" colspan="3"><div data-name="networktype" id="edit-networktype"></div></td>
             </tr>
             <tr class="hide">             
                 <td align="right">是否开通防火墙:</td>
                 <td align="left" colspan="3"><div data-name="fwPort" id="edit-fwPort"></div></td>
             </tr>
             <tr class="hide">              
                 <td align="right">是否商户合同号:</td>
                 <td align="left" colspan="3"><div data-name="isBizCont" id="edit-isBizCont"></div></td>
             </tr>
             <tr class="hide">            
                 <td align="right">是否项目立项号:</td>
                 <td align="left" colspan="3"><div data-name="isProNO" id="edit-isProNo"></div></td>
             </tr>
             <tr class="hide"> 
                 <td align="right">是否项目附件:</td>
                 <td align="left" colspan="3"><div data-name="isProAttach" id="edit-isProAttach"></div></td>
             </tr>
             <tr>
                 <td align="right" colspan="4">
                 <input style="margin-right: 5px;" type="button" id="saveEditBizType" value="保存" />
                 <input id="cancelEditBizType" type="button" value="取消" /></td>
             </tr>
         </table>
   			</div>
	</div>
	<div id="setQuotaWindow" style="display:none">
     	<div>设置配额</div>
     	<div id="editQuotaForm" style="overflow: hidden;">
     	<input type="hidden" data-name="bizSid" id="edit-bizSid"/>
   		<table border="0" width="100%" cellspacing="5" cellpediting="0">
             <tr>
                 <td align="right" width="90px">选择配额类型:</td>
                 <td align="left" width="120px"><div data-name="quotaType" id="set-quotaType" style="width:200px"></div></td>
                 <td align="right" width="60px"><label id="remainQuotaText">剩余配额:</label></td>
                 <td align="left" width="30px"><div id="remainQuota">-</div></td>
                 <td align="left"><input style="margin-right: 5px;width:60px;height:23px" type="button" id="addQuotaType" value="添加"/><input style="margin-right: 5px;width:60px;height:23px" type="button" id="delQuotaType" value="删除"/></td>
             </tr>
             <tr>
                 <td align="right" colspan="5"><div id="editQuotaGrid"></div></td>
             </tr>
             <tr>
                 <td align="right" colspan="5">
                 <input style="margin-right: 5px;width:60px;height:23px" type="button" id="saveQuotaInfo" value="保存" />
                 <input id="cancelQuotaInfo"  style="margin-right: 5px;width:60px;height:23px"  type="button" value="取消" /></td>
             </tr>
         </table>
        </div>
	</div>
</body>
</html>

<script type="text/javascript">

	$(function(){

		initBizMgtPageJs();
	
		// 资源拓扑结构手写选项卡
		$("#jqxExpanderResBiz .resoure").each(function(index){
	    	 $(this).click(index, function(event){
	    		 $("#jqxExpanderResBiz .resoure").removeClass("show");
	    		 $(this).addClass("show");
	    		 $("#jqxExpanderResBiz .content").not(event.data).hide();
	    		 $("#jqxExpanderResBiz .content").eq(event.data).show();
	    	 });
	     });


	
	});
	
	function initBizMgtPageJs() {
		// 初始化服务实例-index页面model
		var model = new bizTypeModel();
		// 初始化组件
		model.initInput();
		// 初始化业务类型树
		model.initBizTypeTree();
		// 初始化组件验证规则
		model.initValidator();
		// 绑定事件
		model.initBindPageEvent();
		
		return model;
	}

</script>

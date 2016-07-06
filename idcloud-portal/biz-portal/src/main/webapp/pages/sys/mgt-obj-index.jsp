<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/mgt-obj-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/mgt-obj-customNetwork-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/mgt-obj-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/mgt-obj-edit-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/mgt-obj-customNetwork-mgtIp-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/mgt-obj-customNetwork-remark-ip-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/mgt-obj-resRelation-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/mgt-obj-find-projectUser-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/mgt-obj-find-projectUser-window-model.js"></script>
		
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
		#mgtObj-containerPool{
			width:99.9%;
			height:99%;
			margin:0px;
		}
		
		.tableCss{
			border-top:1px solid #ccc;
			border-left:1px solid #ccc;
		}
		.tdBorder{
			text-align:center;
			border-bottom:1px solid #CCCCCC;
			border-right:1px solid #CCCCCC;
			height: 35px;
		}
		.box{
			position:relative;
			float:left;
		} 
		input.uploadFile{
			position:absolute;
	/* 		right:0px; */
			left: 0;
			top:0px;
			opacity:0;
			filter:alpha(opacity=0);
			cursor:pointer;
			width:280px;
			height:30px;
			overflow: hidden;
		}
		input.textbox{
			float:left;
			padding:5px;
			color:#999;
			height:18px;
			line-height:24px;
			border:1px #ccc solid;
			width:200px;
			margin-right:4px;
		}
		a.link{
			float:left;
			display:inline-block;
			padding:4px 16px;
			color:#5E5252;
			font:14px "Microsoft YaHei", Verdana, Geneva, sans-serif;
			cursor:pointer;
			background-color:#E8E7E7;
			line-height:22px;
			text-decoration:none;
			height:22.5px;
		}
    </style>  
    </head>
<body>
	<!-- 
	<div id='jqxExpanderBiz' style="border:1px;float: left;width: 20%;height:100%;">
		<div>项目管理</div>
		<div >
			<div style="border: none;" id='jqxTreeBiz'></div>
		</div>
	</div
	 -->
	<div id="mgtObj-containerPool">
         <div id="mgtObj-mainSplitter" style="border:0px">
            <div class="splitter-panel">
                <div style="width:100%;height:100%;border:0px;overflow:hidden" id='jqxTreeBiz'></div>
            </div>
            <div class="listContent">
               	<div id='jqxTabsBiz' style="display: inline; float: left; margin-left: 3px;">
					<ul>
						<li style="font-size: 14px">项目类型</li>
					</ul>
				<div>
						<div style="width: 100%;float:left;">
						         <div style="width:100%;height:60px;padding:10px 0px 10px 0px;">
							           <table border="0" height="100%" cellspacing="0" cellpadding="2">
							            	<tr>
							           			<%--<td align="right">&nbsp;项目开始时间：</td>--%>
							           			<%--<td><div id="search-mgtobj-startDate-start"></div></td>--%>
							           			<%--<td>~</td>--%>
							           			<%--<td><div id="search-mgtobj-startDate-end"></div></td>--%>
							            		<td align="right">&nbsp;项目名称：</td>
							           			<td><input type="text" id="search-mgtobj-name" /></td>
							           			<td align="right">项目类型：</td>
							           			<td><div id="search-mgtobj-type"></div></td>
							           			<%--<td align="right">项目结束时间：</td>--%>
							           			<%--<td><div id="search-mgtobj-endDate-start"></div></td>--%>
							           			<%--<td>~</td>--%>
							           			<%--<td><div id="search-mgtobj-endDate-end"></div></td>--%>
							           			<td align="right">项目经理：</td>
							           			<td><input type="text" id="search-mgtobj-manager"/></td>
							           			<td colspan="2">&nbsp;&nbsp;<a id="search-mgtObj-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
							            	</tr>
							            </table>
							    </div>
								<div id='jqxgridBiz' style="margin-left: 1%"></div>
								<div style="height: 30px;"><p style="margin:0px;margin-top:7px;font-size:14px;color: green;">
							    	※列表中<u style="color: #FB4242;">红色</u>代表项目已到期，<u style="color: rgb(205, 205, 56);">黄色</u>代表项目即将到期</p>
							    </div>
						</div>
						
			 			<%--<div id='jqxExpanderResBiz' style="width:31%;height:98%; float:left;">--%>
			 			   <%--<div style="margin:0px;padding:0px;width:99%;height:35px;margin-top:5px;margin-left:0.5%;line-height:35px;background:#F4F4F4;border:1px solid #E5E5E5;">--%>
<%--<!-- 			 			   		 <a style="margin-left: 5px;" id="mgtObjResRelation"><i class='icons-blue icon-plus-3'></i>关联资源&nbsp;&nbsp;</a> -->--%>
									<%--<a style="margin-left: 5px;" id="bizTypeResBtn">&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>保存关联</a>--%>
			 			   <%--</div>--%>
							<%--<ul class="ul">--%>
						        <%--<li class="resoure show" style="margin-left: 15px;display:none">资源分区</li>--%>
						        <%--<li class="resoure show" style="margin-left: 15px;display:none">计算资源</li>--%>
						        <%--&lt;%&ndash;<li class="resoure show" style="display:none">网络资源</li>&ndash;%&gt;--%>
						        <%--&lt;%&ndash;<li class="resoure show" style="display:none;">存储资源</li> &ndash;%&gt;--%>
						    <%--</ul>--%>
						    <%--<div class="content contentShow" style="display:none">--%>
						       <%--<div id='jqxTreeRz' style="width:100%;height:100%;border:0px;overflow:hidden;"></div>--%>
						    <%--</div>--%>
						    <%--<div class="content contentShow" style="display:none">--%>
						       <%--<div id='jqxTreeCompute' style="width:100%;height:100%;border:0px;overflow:hidden"></div>--%>
						    <%--</div>--%>
<%--<!-- 						    <div class="content contentShow" style="display:none"> -->--%>
<%--<!-- 						       <div id='jqxTreeNetwork' style="width:100%;height:100%;border:0px;overflow:hidden"></div> -->--%>
<%--<!-- 						    </div> -->--%>
<%--<!-- 						    <div class="content contentShow" style="display:none;"> -->--%>
<%--<!-- 						       <div id='jqxTreeStorage' style="width:100%;height:100%;border:0px;overflow:hidden;"></div>  -->--%>
<%--<!-- 						    </div> -->--%>
						<%--</div>--%>
					</div>
				</div>
            </div>
         </div>
   </div>
	
	<!-- 窗口模板 -->
	<div id="popTemplate" style="display:none">
		<div class="title"></div>
		<div class="form">
			<table class="content" border="0" width="100%" cellspacing="5" cellpadding="0"> 
				<input class="oldProjectName" type="hidden"/>
				<input class="oldProjectEName" type="hidden"/>
				<input class="oldProjectId" type="hidden"/>
				<tr style="display:none" >
					<td align="right" class="label"></td>
					<td align="left" class="value"></td>
				</tr>
				<tbody class="autoTrs">
					
				</tbody> 
				<tr>
					<td align="right"><font style="color:red">＊</font>项目经理</td>
					<td align="left">
						<input class="projectManagerSid" type="hidden"/>
						<input class="projectManager" type="text" disabled="disabled"/>
						<i class='icons-blue icon-search' style="font-size: 15px;" onclick="getProjectManager()"></i>
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
						<input style="margin-right: 5px;" type="button" class="save" value="保存" />
						<input class="cancel" type="button" value="取消" /></td>
				</tr>
			</table>
		</div>
	</div>

	<div id="chooseManagerWindow">
     	<div>选择项目经理</div>
     	<div id="chooseManagerForm" style="overflow: hidden;">
     		<div style="width:100%;height:30px;padding:5px 0px 5px 0px;">
	           <table  border="0" height="100%" cellspacing="0" cellpadding="2">
	           		<tr>
	           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;用户姓名：</td>
	           			<td><input type="text" id="search-manager-name" />&nbsp;&nbsp;</td>
	           			<td><a  onclick="searchManager()" id="search-manager-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
	            	</tr>
	            </table>
		    </div>
		    <div style="width:99%;height:70%;margin-left:0.5%;overflow-y:auto;"> 
		     	<div id='managerDatagrid'></div> 
		    </div>
     		<div style="text-align: right;margin-top: 8px;">
     			<input style="margin-right: 5px;" type="button" id="saveMgtManager" onclick="saveMgtManager()" value="保存" />
	            <input id="cancelMgtManager" type="button" value="取消" />
     		</div>
   		</div>
	</div>
	
	<div id="addGroupWindow">
     	<div>新增项目分类</div>
     	<div id="addGroupForm" style="overflow: hidden;">
        	 <table border="0" width="100%" cellspacing="5" cellpadding="5">
	             <tr>
	                 <td align="right"><font style="color:red">*</font>项目分类名称:</td>
	                 <td align="left"><input type="text" data-name="name" id="add-mgtObjName"/></td>
	             </tr> 
	             <tr>             
	                 <td align="right">描述:</td>
	                 <td align="left" colspan="3"><textarea data-name="description" id="add-mgtObjDesc"></textarea></td>
	             </tr>
	             <tr>
	                 <td align="right" colspan="4">
	                 <input style="margin-right: 5px;" type="button" id="saveAddGroupType" onclick="submitAddGroup()" value="保存" />
	                 <input id="cancelAddGroupType" type="button" value="取消" /></td>
	             </tr>
	         </table>
   		</div>
	</div>

	<div id="editGroupWindow">
     	<div>编辑业务类型</div>
     	<div id="editGroupForm" style="overflow: hidden;">
     	<input type="hidden" data-name="mgtObjSid" id="edit-mgtObjSid"/>
        <table border="0" width="100%" cellspacing="5" cellpediting="0">
             <tr>
                 <td align="right"><font style="color:red">*</font>项目名称:</td>
                 <td align="left"><input type="text" data-name="name" id="edit-mgtObjName"/></td>
             </tr> 
             <tr>             
                 <td align="right">描述:</td>
                 <td align="left" colspan="3"><textarea data-name="description" id="edit-mgtObjDesc"></textarea></td>
             </tr>
             <tr>
                 <td align="right" colspan="4">
                 <input style="margin-right: 5px;" type="button" id="saveEditGroupType" onclick="submitEditGroup()" value="保存" />
                 <input id="cancelEditGroupType" type="button" value="取消" /></td>
             </tr>
         </table>
   			</div>
	</div>
	<div id="approveMgtObjWindow">
		<div>审核</div>
     	<div id="approveMgtObjForm" style="overflow: hidden;">
	     	<input type="hidden" id="approve-mgtObjSid"/>
	   		<table border="0" width="100%" cellspacing="5" cellpediting="0">
	             <tr>
	                 <td align="right">项目名称:</td>
	                 <td align="left"><span id="mgtObj-Name"></span></td>
	                 <td align="right">所属项目组:</td>
	                 <td align="left"><span id="mgtObj-parentName"></span></td>
	             </tr>
	             <tr>
	                 <td align="right">项目开始时间:</td>
	                 <td align="left"><span id="mgtObj-startDt"></span></td>
	                 <td align="right">项目结束时间:</td>
	                 <td align="left"><span id="mgtObj-endDt"></span></td>
	             </tr>
             </table>
             <table border="0" width="100%" cellspacing="5" cellpediting="0" style="margin-top: 20px;">
	             <tr>
	             	 <td align="right" width="30%">审核结果：</td>
	             	 <td align="left"><div id="mgtApproveResult"></div></td>
	             </tr>
	             <tr>
	                 <td align="right" colspan="2">
	                 	<input type="button" id="saveApproveResult" onclick="saveMgtObjApprove()" value="保存" />
	                 	<input id="cancelApproveResult" type="button" value="取消" />
	                 </td>
	             </tr>
	         </table>
     	</div>
	</div>
	<div id="setQuotaWindow">
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
	<div id="mgtQuotaWindow">
		<div>设置配额</div>
     	<div id="editQuotaForm1" style="overflow: hidden;">
     		<input type="hidden" id="quota-mgtObjSid"/>
   			<table border="0" width="100%" cellspacing="0" cellpadding="5" class="tableCss">
				<tr><td class="tdBorder" rowspan="2">云主机类型</td>
					<td class="tdBorder" colspan="3">云主机数量</td>
					<td class="tdBorder" colspan="3">CPU（核）</td>
					<td class="tdBorder" colspan="3">内存（GB）</td>
					<td class="tdBorder" colspan="3">外置存储（GB）</td>
				</tr>
				<tr>
					<td class="tdBorder">总配额</td>
					<td class="tdBorder">已使用</td>
					<td class="tdBorder">未使用</td>
					<td class="tdBorder">总配额</td>
					<td class="tdBorder">已使用</td>
					<td class="tdBorder">未使用</td>
					<td class="tdBorder">总配额</td>
					<td class="tdBorder">已使用</td>
					<td class="tdBorder">未使用</td>
					<td class="tdBorder">总配额</td>
					<td class="tdBorder">已使用</td>
					<td class="tdBorder">未使用</td>
				</tr>
				<tr><td class="tdBorder">Linux&Windows</td>
					<td class="tdBorder"><div id="winVmSum"></div></td>
					<td class="tdBorder"><span id="winVmUsed"></span></td>
					<td class="tdBorder"><span id="winVmUnused"></span></td>
					<td class="tdBorder"><div id="winCpuSum"></div></td>
					<td class="tdBorder"><span id="winCpuUsed"></span></td>
					<td class="tdBorder"><span id="winCpuUnused"></span></td>
					<td class="tdBorder"><div id="winMemSum"></div></td>
					<td class="tdBorder"><span id="winMemUsed"></span></td>
					<td class="tdBorder"><span id="winMemUnused"></span></td>
					<td class="tdBorder"><div id="winStSum"></div></td>
					<td class="tdBorder"><span id="winStUsed"></span></td>
					<td class="tdBorder"><span id="winStUnused"></span></td>
				</tr>
				<tr><td class="tdBorder">IBM AIX</td>
					<td class="tdBorder"><div id="aixVmSum"></div></td>
					<td class="tdBorder"><span id="aixVmUsed"></span></td>
					<td class="tdBorder"><span id="aixVmUnused"></span></td>
					<td class="tdBorder"><div id="aixCpuSum"></div></td>
					<td class="tdBorder"><span id="aixCpuUsed"></span></td>
					<td class="tdBorder"><span id="aixCpuUnused"></span></td>
					<td class="tdBorder"><div id="aixMemSum"></div></td>
					<td class="tdBorder"><span id="aixMemUsed"></span></td>
					<td class="tdBorder"><span id="aixMemUnused"></span></td>
					<td class="tdBorder"><div id="aixStSum"></div></td>
					<td class="tdBorder"><span id="aixStUsed"></span></td>
					<td class="tdBorder"><span id="aixStUnused"></span></td>
				</tr>
			</table>
			<div style="margin-top: 10px; text-align: right;">
                 <input type="button" id="saveMgtQuota" onclick="saveMgtQuota()" value="保存" />
                 <input id="cancelMgtQuota" type="button" value="取消" />
            </div>
        </div>
	</div>
	
 <div id="view-mgtObjMsg">
     	<div>项目详情信息</div>
     	<div style="overflow: hidden;">
	   		<table border="0" width="100%" cellspacing="0" cellpadding="5" class="tableCss">
	             <tbody id="view-mgtObjDetail"></tbody>
	         </table>
	         <div style="text-align: right;margin-top: 10px;"><input id="cancelViewMgtObj" style="margin-right: 5px;width:60px;height:23px"  type="button" value="取消" /></div>
        </div>
	</div>
	
 	<%@ include file="mgt-obj-customNetwork-index.jsp"%>
 	<%@ include file="mgt-obj-add-customNetwork-index.jsp"%>
	<%@ include file="mgt-obj-edit-customNetwork-index.jsp"%>
	<%@ include file="mgt-obj-customNetwork-mgtIp-index.jsp"%>
	<%@ include file="mgt-obj-customNetwork-remark-ip-index.jsp"%>
	<%--<%@ include file="mgt-obj-resRelation-index.jsp"%>--%>
	<%@ include file="mgt-obj-find-projectUser-index.jsp"%>
	<%@ include file="mgt-obj-find-projectUser-window-index.jsp"%>

	<script type="text/javascript">
		var resourcePoolSid;
		$(function(){
			$('#mgtObj-mainSplitter').jqxSplitter({ width: "100%", height: "100%", theme:currentTheme, panels: [{ size: 250 ,max:300 ,min:100}] });
			initBizMgtPageJs();
			// 资源拓扑结构手写选项卡
			$("#jqxExpanderResBiz .resoure").each(function(index){
		    	 $(this).click(index, function(event){
		    		 $("#jqxExpanderResBiz .resoure").removeClass("show");
		    		 $(this).addClass("show");
		    		 $("#jqxExpanderResBiz .content").hide();
		    		 $("#jqxExpanderResBiz .content").eq(event.data).show();
		    		 $("#jqxExpanderResBiz .content").eq(event.data).children().jqxTree('expandAll');
		    	 });
		     });
		});
		
		function initBizMgtPageJs() {
			// 初始化服务实例-index页面model
			var model = new MgtObjModel();
			model.mgtObjCondition['level'] = '1';
			// 初始化组件
			model.initInput();
//			model.popMgtQuotaWindow();
			// 初始化业务类型树
			model.initBizTypeTree();
			// 初始化组件验证规则
			model.initValidator();
			// 绑定事件
			model.initBindPageEvent();
			
			model.initBizTypeDatagrid();
	
			return model;
		}
	
	</script>
</body>
</html>

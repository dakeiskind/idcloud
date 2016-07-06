<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
	String resVmSid = request.getParameter("resVmSid");
	String monitorNodeId = request.getParameter("monitorNodeId");
%>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-storageVolume-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/highcharts/highcharts.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/highcharts/highcharts-more.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/highcharts/modules/exporting.js"></script>
		<style type="text/css">
			#allVmInfo{
				width:100%;
				height:100%;
				padding:0px;
				margin:0px;
			}
			
			#monitorContent .customPanel{
				display:none;
				overflow:hidden;
			}
			.showContent{
				display:block;
			}
			
		</style>
</head>
<body class='default'>
	   <div style="position:absolute;top:0px;left:0px;width:32%;height:99.5%;">
	   			<div class="customPanel" style="width:98%; height: 310px;margin-left:5px;margin-top:7px;">
				   		<div class="title">&nbsp;&nbsp;基本信息</div>
						<div>
							<table border="0" width="100%" cellspacing="2" cellpadding="5">
								<tr>
									<td align="right" width="120">虚拟机名称:</td>
									<td align="left"><span id="allocateVmName"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">操作系统:</td>
									<td align="left"><span id="osTypeName"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">操作系统名称:</td>
									<td align="left"><span id="osName"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">CPU(核):</td>
									<td align="left"><span id="cpuCores"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">内存(MB):</td>
									<td align="left" colspan="3"><span id="memorySize"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">置备容量(GB):</td>
									<td align="left" colspan="3"><span id="provisionStorage"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">虚机状态:</td>
									<td align="left"><span id="statusName"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">所属主机:</td>
									<td align="left"><span id="hostName1"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">管理账户/密码:</td>
									<td align="left"><span id="accountPasswd"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">创建时间:</td>
									<td align="left"><span id="vmCreateTime"></span></td>
								</tr>
							</table>
						</div>
			   </div>
			   
			    <div class="customPanel" style="width:98%; height:130px;margin-left:5px;margin-top:7px;">
					<div class="title">&nbsp;&nbsp;系统用户
						<font style="cursor:pointer;position:absolute;right:45px" onclick="popAddVmUserWindow()"><i class="icons-blue icon-plus-1"></i></font>
		  		    	<font style="cursor:pointer;position:absolute;right:25px" onclick="popEditVmUserWindow()"><i class="icons-blue icon-pencil"></i></font>
		  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="removeVmUserInfo()"><i class="icons-blue icon-trash"></i></font>
					</div>
					<div>
						<div style="width:100%; height: 100px; margin-top: 5px;overflow-y:auto;">
							<div id='vmUserDatagrid' style="width: 98%;margin-left: 1%"></div>
						</div>
					</div>
			   </div>
			   
			   <div class="customPanel" style="width:98%;height: 130px;margin-left:5px;margin-top:7px;">
					<div class="title">&nbsp;&nbsp;存储信息
						<font style="cursor:pointer;position:absolute;right:65px" onclick="popViewVmVdWindow()"><i class="icons-blue icon-doc-text"></i></font>
						<font style="cursor:pointer;position:absolute;right:45px" onclick="popAddVmVdWindow()"><i class="icons-blue icon-plus-1"></i></font>
		  		    	<font style="cursor:pointer;position:absolute;right:25px" onclick="popEditVmVdWindow()"><i class="icons-blue icon-pencil"></i></font>
		  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="removeVmVdInfo()"><i class="icons-blue icon-trash"></i></font>
					</div>
					<div>
						<div style="width:100%; height: 100px; margin-top: 5px;overflow-y:auto;">
							<div id='vmStorageDatagrid' style="width: 98%;margin-left: 1%"></div>
						</div>
					</div>
			   </div>
			   
			   <div class="customPanel" style="width:98%; height:130px;margin-left:5px;margin-top:7px;">
					<div class="title">&nbsp;&nbsp;软件信息
						<font style="cursor:pointer;position:absolute;right:45px" onclick="popAddVmSoftwareWindow()"><i class="icons-blue icon-plus-1"></i></font>
		  		    	<font style="cursor:pointer;position:absolute;right:25px" onclick="popEditVmSoftwareWindow()"><i class="icons-blue icon-pencil"></i></font>
		  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="removeVmSoftwareInfo()"><i class="icons-blue icon-trash"></i></font>
					</div>
					<div>
						<div style="width:100%; height: 100px; margin-top: 5px;overflow-y:auto;">
							<div id='vmSoftwareDatagrid' style="width: 98%;margin-left: 1%"></div>
						</div>
					</div>
			   </div>
			   
			   <div class="customPanel" style="width:98%; height:130px;margin-left:5px;margin-top:7px;">
					<div class="title">&nbsp;&nbsp;网卡信息</div>
					<div>
						<div style="width:100%; height: 100px; margin-top: 5px;overflow-y:auto;">
							<div id='vmNicDatagrid' style="width: 98%;margin-left: 1%"></div>
						</div>
					</div>
			   </div>
			   
	   </div>
	   
	   <div id="monitorContainer" style="position:absolute;top:0px;left:32.5%;width:67.2%;height:99.5%;">
	   		 <div class="customPanel" style="position:absolute;left:0.5%;top:10px;width:99%;height:97%;overflow-y:auto;">
				<div class="title">&nbsp;&nbsp;监控信息</div>
				<div>
					<div style="width:100%;height:100%;">
					   		<div style="width:98%;height:35px;margin-left:1%;">
					   			 <table border="0" width="100%" height="35px" cellpadding="5" cellspacing="0">
					   			     <tr>
					   			 	 	<td align="right" width="60px">周期:</td>
					   			 	 	<td align="left"  width="105px"><div id="searchMonitorInfo"></div></td>
					   			 	 	<td align="left" width="60px">
					   			 	 	     <input id="searchMonitorBtn" type="button" onclick="setMonitorPerid()" value="查询" />
					   			 	 	</td>
					   			 	 	<td align="right">切换至:</td> 
					   			 	 	<td align="right" width="105px"><div id="searchMonitorContent"></div></td>
					   			 	 </tr>
					   			 </table>
					   		</div>
					   		
							<div id="monitorContent" style="width:98%;height:99%;margin-left:1%;margin-top:7px;">   		
						   		<div  class="customPanel" style="display:block; width:100%;">
							  		   <div class="title">&nbsp;&nbsp;CPU使用率</div>
								        <div id="cpuVmContainer">
								        	
								        </div>
						  		</div>
						  		
						  		<div class="customPanel" style="width:100%;height:300px;margin-top:7px;">
							  		    <div class="title">&nbsp;&nbsp;内存使用率
							  		    	<div style="position:absolute;top:0px;left:50%;width:50%;height:20px;text-align:right">
							  		    		最大使用率:<font id="mem_max_usage" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    		平均使用率:<font id="mem_avg_usage" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    		最小使用率:<font id="mem_min_usage" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    	</div>
							  		    </div>
								        <div>
								        	<div id='memoryVmUsedchartContainer' style="width:100%; height:275px;border:0px;"></div>
								        </div>
						  		</div>
						  		<div id="customPanelDisk" class="customPanel" style="width:100%;margin-top:7px">
							  		    <div class="title">&nbsp;&nbsp;虚拟机磁盘占用率</div>
								        <div id="diskVmContainer">
								        	
								        </div>
						  		</div>
					  	</div>
			     </div>
             </div>
          </div> 
	 </div>
	 
	 <!-- 修改虚拟机名称 -->
	 <div id="editVmNameWindow">
	       <div>修改虚机名称</div>
	       <div id="modifyVmForm" style="overflow: hidden;">
	           <input type="hidden" data-name="resVmSid" id="edit-vm-resVmSid"/>
	           <table border="0" width="100%" cellspacing="2" cellpadding="0">
	               <tr>
	                   <td align="right" height="30px;">虚拟机名称:</td>
	                   <td align="left" >
	                       <input type="text" data-name="vmName" id="edit-vm-name" />
	                   </td>
	               </tr>
	               <tr>
	               	<td colspan="2" height="30px;" align="right" >
	               		<input style="margin-right: 5px;" onclick="submitVmNameSave()" id="modifyVmSave" type="button" value="保存" />
	               		<input id="modifyVmCancel" type="button" value="取消" />
	               	</td>
	               </tr>
	           </table>
	       </div>
	  </div> 
	  
	  <!-- 新增管理账户 -->
	 <div id="addVmUserWindow">
	       <div>新增系统用户</div>
	       <div id="addVmUserForm" style="overflow: hidden;">
	           <input type="hidden" data-name="resSid" id="add-vm-user-resVmSid"/>
	           <input type="hidden" data-name="resType" id="add-type-user"/>
	           <table border="0" width="100%" cellspacing="2" cellpadding="0">
	               <tr>
	                   <td align="right" height="30px;"><font style="color:red">*</font>用户名:</td>
	                   <td align="left" >
	                       <input type="text" data-name="userName" id="add-vm-user-name" />
	                   </td>
	                   <td align="right" height="30px;"><font style="color:red">*</font>密码:</td>
	                   <td align="left" >
	                       <input type="password" data-name="password" id="add-vm-user-password" />
	                   </td>
	               </tr>
	               <tr>
	                   <td align="right" height="30px;">用户组:</td>
	                   <td align="left" >
	                       <input type="text" data-name="userGroup" id="add-vm-user-userGroup" />
	                   </td>
	                    <td align="right" height="30px;">权限:</td>
	                   <td align="left" >
	                       <input type="text" data-name="privilege" id="add-vm-user-privilege" />
	                   </td>
	               </tr>
	                <tr>
	                   <td align="right" height="30px;">描述:</td>
	                   <td align="left" colspan="3">
	                       <textarea data-name="description" id="add-vm-user-description" ></textarea>
	                   </td>
	               </tr>
	               <tr>
	               	<td colspan="4" height="30px;" align="right" >
	               		<input style="margin-right: 5px;" onclick="submitVmUserInfo()" id="addVmUserSave" type="button" value="保存" />
	               		<input id="addVmUserCancel" type="button" value="取消" />
	               	</td>
	               </tr>
	           </table>
	       </div>
	  </div>
	  
	  <!-- 编辑管理账户 -->
	 <div id="editVmUserWindow">
	       <div>编辑系统用户</div>
	       <div id="editVmUserForm" style="overflow: hidden;">
	           <input type="hidden" data-name="osUserSid" id="edit-vm-user-sid"/>
	           <table border="0" width="100%" cellspacing="2" cellpadding="0">
	               <tr>
	                   <td align="right" height="30px;"><font style="color:red">*</font>用户名:</td>
	                   <td align="left" >
	                       <input type="text" data-name="userName" id="edit-vm-user-name" />
	                   </td>
	                    <td align="right" height="30px;"><font style="color:red">*</font>密码:</td>
	                   <td align="left" >
	                       <input type="password" data-name="password" id="edit-vm-user-password" />
	                   </td>
	               </tr>
	               <tr>
	                   <td align="right" height="30px;">用户组:</td>
	                   <td align="left" >
	                       <input type="text" data-name="userGroup" id="edit-vm-user-userGroup" />
	                   </td>
	                    <td align="right" height="30px;">权限:</td>
	                   <td align="left" >
	                       <input type="text" data-name="privilege" id="edit-vm-user-privilege" />
	                   </td>
	               </tr>
	                <tr>
	                   <td align="right" height="30px;">描述:</td>
	                   <td align="left" colspan="3">
	                       <textarea data-name="description" id="edit-vm-user-description" ></textarea>
	                   </td>
	               </tr>
	               <tr>
	               	<td colspan="4" height="30px;" align="right" >
	               		<input style="margin-right: 5px;" onclick="submitEditVmUserInfo()" id="editVmUserSave" type="button" value="保存" />
	               		<input id="editVmUserCancel" type="button" value="取消" />
	               	</td>
	               </tr>
	           </table>
	       </div>
	  </div>
	  
	  <!-- 编辑管理账户 -->
	  <div id="editVmMgtUserWindow">
	       <div>编辑管理账户</div>
	       <div id="editVmMgtUserForm" style="overflow: hidden;">
	           <input type="hidden" data-name="resVmSid" id="edit-vm-mgt-user-sid"/>
	           <table border="0" width="100%" cellspacing="2" cellpadding="0">
	               <tr>
	                   <td align="right" height="30px;"><font style="color:red">*</font>账户名称:</td>
	                   <td align="left" >
	                       <input type="text" data-name="managementAccount" id="edit-vm-mgt-user-name" />
	                   </td>
	               </tr>
	               <tr>
	                   <td align="right" height="30px;"><font style="color:red">*</font>密码:</td>
	                   <td align="left" >
	                       <input type="password" data-name="managementPassword" id="edit-vm-mgt-user-password" />
	                   </td>
	               </tr>
	               <tr>
	               	<td colspan="2" height="30px;" align="right" >
	               		<input style="margin-right: 5px;" onclick="submitVmMgtUserAndPasswordName()" id="editVmMgtUserSave" type="button" value="保存" />
	               		<input id="editVmMgtUserCancel" type="button" value="取消" />
	               	</td>
	               </tr>
	           </table>
	       </div>
	  </div>
	  
	  <!-- 编辑操作系统名称 -->
	  <div id="editVmOsNameWindow">
	       <div>设置操作系统名称</div>
	       <div id="editVmOsNameForm" style="overflow: hidden;">
	           <input type="hidden" data-name="resVmSid" id="edit-vm-os-name-sid"/>
	           <table border="0" width="100%" cellspacing="5" cellpadding="0">
	               <tr>
	                   <td align="right" height="30px;"><font style="color:red">*</font>操作系统名称:</td>
	                   <td align="left" >
	                       <input type="text" data-name="osName" id="edit-vm-os-name" />
	                   </td>
	                   <td colspan="2" height="30px;" align="right" >
		               		<input style="margin-right: 5px;" onclick="submitVmOsName()" id="editVmOsNameSave" type="button" value="保存" />
		               		<input id="editVmOsNameCancel" type="button" value="取消" />
		               </td>
	               </tr>
	           </table>
	       </div>
	  </div>
	  
	  <!-- 新增虚拟机磁盘 -->
	  <div id="addVmVdWindow">
	       <div>新增磁盘</div>
	       <div id="addVmVdForm" style="overflow: hidden;">
	           <input type="hidden" data-name="resVmSid" id="add-vm-vd-sid"/>
	           <table border="0" width="100%" cellspacing="5" cellpadding="0">
	               <tr>
	                   <td align="right" height="30px;"><font style="color:red">*</font>磁盘名称:</td>
	                   <td align="left">
	                       <input type="text" data-name="vdName" id="add-vm-vd-name" />
	                   </td>
	                   <td align="right" height="30px;"><font style="color:red">*</font>逻辑卷名称:</td>
	                   <td align="left">
	                       <input type="text" data-name="logicVolume" id="add-vm-vd-logicVolume" />
	                   </td>
	               </tr>
	               <tr>
	               	   <td align="right" height="30px;"><font style="color:red">*</font>选择存储:</td>
	                   <td align="left" >
	                       <div data-name="allocateResStorageSid" id="add-vm-vd-storage" ></div>
	                   </td>
	                    <td align="right" height="30px;"><font style="color:red">*</font>分配磁盘大小:</td>
	                   <td align="left" >
	                       <input type="text" data-name="allocateDiskSize" id="add-vm-vd-size" />
	                   </td>
	               </tr>
	               <tr>
	               	   <td align="right" height="30px;"><font style="color:red">*</font>磁盘用途:</td>
	                   <td align="left" >
	                       <div data-name="storagePurpose" id="add-vm-vd-storagePurpose" ></div>
	                   </td>
	                    <td align="right" height="30px;"><font style="color:red">*</font>磁盘路径:</td>
	                   <td align="left" >
	                       <input type="text" data-name="path" id="add-vm-vd-devicePath" />
	                   </td>
	               </tr>
	                <tr>
	               	   <td align="right" height="30px;"><font style="color:red">*</font>挂载点:</td>
	                   <td align="left" >
	                       <input type="text" data-name="mountPoint" id="add-vm-vd-mountPoint" />
	                   </td>
	                    <td align="right" height="30px;"><font style="color:red">*</font>文件系统类型:</td>
	                   <td align="left" >
	                       <input type="text" data-name="fileSystemType" id="add-vm-vd-fileSystemType" />
	                   </td>
	               </tr>
	               <tr>
	               		<td height="30px;" align="right" colspan="4">
		               		<input style="margin-right: 5px;" onclick="submitVmVdInfo()" id="addVmVdInfoSave" type="button" value="保存" />
		               		<input id="addVmVdInfoCancel" type="button" value="取消" />
		               </td>
	               </tr>
	           </table>
	       </div>
	  </div>
	  
	  <!-- 编辑虚拟机磁盘 -->
	  <div id="editVmVdWindow">
	       <div>编辑磁盘</div>
	       <div id="editVmVdForm" style="overflow: hidden;">
	           <input type="hidden" data-name="resVdSid" id="edit-vm-vd-sid"/>
	           <table border="0" width="100%" cellspacing="5" cellpadding="0">
	               <tr>
	                   <td align="right" height="30px;"><font style="color:red">*</font>磁盘名称:</td>
	                   <td align="left">
	                       <input type="text" data-name="vdName" id="edit-vm-vd-name" />
	                   </td>
	                   <td align="right" height="30px;"><font style="color:red">*</font>分配磁盘大小:</td>
	                   <td align="left" >
	                       <input type="text" data-name="allocateDiskSize" id="edit-vm-vd-size" />
	                   </td>
	                  
	               </tr>
	               <tr>
	               	   <td align="right" height="30px;"><font style="color:red">*</font>逻辑卷名称:</td>
	                   <td align="left">
	                       <input type="text" data-name="logicVolume" id="edit-vm-vd-logicVolume" />
	                   </td>
	                   <td align="right" height="30px;"><font style="color:red">*</font>已使用磁盘大小:</td>
	                   <td align="left" >
	                       <input type="text" data-name="useDiskSize" id="edit-vm-useDiskSize" />
	                   </td>
	               </tr>
	               <tr>
	               	   <td align="right" height="30px;"><font style="color:red">*</font>磁盘用途:</td>
	                   <td align="left" >
	                       <div data-name="storagePurpose" id="edit-vm-vd-storagePurpose" ></div>
	                   </td>
	                    <td align="right" height="30px;"><font style="color:red">*</font>磁盘路径:</td>
	                   <td align="left" >
	                       <input type="text" data-name="path" id="edit-vm-vd-devicePath" />
	                   </td>
	               </tr>
	                <tr>
	               	   <td align="right" height="30px;"><font style="color:red">*</font>挂载点:</td>
	                   <td align="left" >
	                       <input type="text" data-name="mountPoint" id="edit-vm-vd-mountPoint" />
	                   </td>
	                    <td align="right" height="30px;"><font style="color:red">*</font>文件系统类型:</td>
	                   <td align="left" >
	                       <input type="text" data-name="fileSystemType" id="edit-vm-vd-fileSystemType" />
	                   </td>
	               </tr>
	               <tr>
	               		<td height="30px;" align="right" colspan="4">
		               		<input style="margin-right: 5px;" onclick="submitEditVmVdInfo()" id="editVmVdInfoSave" type="button" value="保存" />
		               		<input id="editVmVdInfoCancel" type="button" value="取消" />
		               </td>
	               </tr>
	           </table>
	       </div>
	  </div>
	  
	  <!-- 新增软件 -->
	  <div id="addVmSoftwareWindow">
	       <div>新增软件</div>
	       <div id="addVmSoftwareForm" style="overflow: hidden;">
	           <input type="hidden" data-name="resSid" id="add-vm-software-sid"/>
	           <input type="hidden" data-name="resType" id="add-vm-software-type"/>
	           <table border="0" width="100%" cellspacing="5" cellpadding="0">
	               <tr>
	                   <td align="right" height="30px;"><font style="color:red">*</font>软件分类:</td>
	                   <td align="left">
	                       <div data-name="softwareCategory" id="add-vm-software_category"></div>
	                   </td>
	                   <td align="right" height="30px;"><font style="color:red">*</font>软件类型:</td>
	                   <td align="left">
	                       <div data-name="softwareType" id="add-vm-software_type"></div>
	                   </td>
	               </tr>
	               <tr>
	               	   <td align="right" height="30px;"><font style="color:red">*</font>软件版本:</td>
	                   <td align="left" >
	                       <div data-name="softwareVersion" id="add-vm-software_version" ></div>
	                   </td>
	                    <td align="right" height="30px;">安装路径:</td>
	                   <td align="left" >
	                       <input type="text" data-name="installPath" id="add-vm-install_path" />
	                   </td>
	               </tr>
	               <tr>
	               	   <td align="right" height="30px;">安装用户组:</td>
	                   <td align="left" >
	                       <input type="text" data-name="installUserGroup" id="add-vm-install_user_group" />
	                   </td>
	                    <td align="right" height="30px;">安装用户:</td>
	                   <td align="left" >
	                       <input type="text" data-name="installUser" id="add-vm-install_user" />
	                   </td>
	               </tr>
	               <tr>
	               		<td height="30px;" align="right" colspan="4">
		               		<input style="margin-right: 5px;" onclick="submitAddVmSoftwareInfo()" id="addVmSoftwareInfoSave" type="button" value="保存" />
		               		<input id="addVmSoftwareInfoCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
		               </td>
	               </tr>
	           </table>
	       </div>
	  </div>
	  
	  <!-- 编辑软件 -->
	  <div id="editVmSoftwareWindow">
	       <div>编辑软件</div>
	       <div id="editVmSoftwareForm" style="overflow: hidden;">
	           <input type="hidden" data-name="resSortwareSid" id="edit-software-sid"/>
	           <table border="0" width="100%" cellspacing="5" cellpadding="0">
	               <tr>
	                   <td align="right" height="30px;"><font style="color:red">*</font>软件分类:</td>
	                   <td align="left">
	                       <div data-name="softwareCategory" id="edit-vm-software_category"></div>
	                   </td>
	                   <td align="right" height="30px;"><font style="color:red">*</font>软件类型:</td>
	                   <td align="left">
	                       <div data-name="softwareType" id="edit-vm-software_type"></div>
	                   </td>
	               </tr>
	               <tr>
	               	   <td align="right" height="30px;"><font style="color:red">*</font>软件版本:</td>
	                   <td align="left" >
	                       <div data-name="softwareVersion" id="edit-vm-software_version" ></div>
	                   </td>
	                    <td align="right" height="30px;"><font style="color:red">*</font>安装路径:</td>
	                   <td align="left" >
	                       <input type="text" data-name="installPath" id="edit-vm-install_path" />
	                   </td>
	               </tr>
	               <tr>
	               	   <td align="right" height="30px;"><font style="color:red">*</font>安装用户组:</td>
	                   <td align="left" >
	                       <input type="text" data-name="installUserGroup" id="edit-vm-install_user_group" />
	                   </td>
	                    <td align="right" height="30px;"><font style="color:red">*</font>安装用户:</td>
	                   <td align="left" >
	                       <input type="text" data-name="installUser" id="edit-vm-install_user" />
	                   </td>
	               </tr>
	               <tr>
	               		<td height="30px;" align="right" colspan="4">
		               		<input style="margin-right: 5px;" onclick="submitEditVmSoftwareInfo()" id="editVmSoftwareInfoSave" type="button" value="保存" />
		               		<input id="editVmSoftwareInfoCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
		               </td>
	               </tr>
	           </table>
	       </div>
	  </div>
	  
	  <!-- 编辑软件 -->
	  <div id="viewVmStorageWindow">
	       <div>存储卷</div>
	       <div id="vmStorageVolumeDatagrid" style="overflow: hidden;"></div>
	  </div>
	  

</body>
<script type="text/javascript">
    var resVmSid = '<%=resVmSid %>';
    var monitorNodeId = '<%=monitorNodeId %>';
    
 	// 初始化查询和刷新按钮
	var dataPerid = [
	     {name:"天",value:"Day"},
	     {name:"周",value:"Week"},
	     {name:"月",value:"Month"}
	];
    var source = {
           datatype: "json",
           datafields: [
               { name: 'name' },
               { name: 'value' }
           ],
           localData:dataPerid
    };
    var dataAdapter = new $.jqx.dataAdapter(source);
    
    $("#searchMonitorInfo").jqxDropDownList({
        selectedIndex: 0, source: dataAdapter, displayMember: "name", valueMember: "value", width: 100, height: 22,theme:currentTheme,autoDropDownHeight: true
    }); 
    
    
 	// 初始化查询和刷新按钮
	var dataContent = [
	     {name:"CPU使用率",value:"cpu"},
	     {name:"内存使用率",value:"memory"},
	     {name:"文件系统使用率",value:"vd"}
	];
    var source = {
           datatype: "json",
           datafields: [
               { name: 'name' },
               { name: 'value' }
           ],
           localData:dataContent
    };
    var dataAdapter = new $.jqx.dataAdapter(source);
    
    $("#searchMonitorContent").jqxDropDownList({
        selectedIndex: 0, source: dataAdapter, displayMember: "name", valueMember: "value", width: 150, height: 22,theme:currentTheme,autoDropDownHeight: true
    }); 
    
    // 查询按钮
    $("#searchMonitorBtn").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
	
    var info = new vmInfoModel(resVmSid);
    // 基本信息
    info.setVmBasicInfo();
 	// 初始化window
 	info.initPopWindow();
 	info.initValidator();
 	
 	// 用户管理信息
    info.initVmUserDatagrid();
    info.searchVmUserInfo();
    
    // 存储信息
    info.initVmStorageDatagrid();
    info.searchVmStorageInfo();
    
    // 网卡信息
 	info.initVmNicDatagrid();
 	info.searchVmNicInfo();
 	
 	// 软件信息
 	info.initVmSoftwareDatagrid();
 	info.searchVmSoftwareInfo();
    
    $('#searchMonitorContent').on('select',function (event){
    	var args = event.args;
        if (args) {
        	
	        var item = args.item;
	        var value = item.value;
	        var vmInfo = new vmInfoModel();
	        var timeType = $("#searchMonitorInfo").val();
	        
	        if(monitorNodeId != null  && monitorNodeId != "" && monitorNodeId != "null"){
	        	if("cpu" == value){
		        	$("#monitorContent .customPanel").css("display","none");
		        	$("#monitorContent .customPanel").eq(0).css("display","block");
		        	
		        	if("Day" == timeType){
		        		var peroid = getThePeridTime(1);
			        	vmInfo.initVmCpuUsedRate(monitorNodeId,peroid,timeType);
		        	}else if("Week" == timeType){
		        		var peroid = getThePeridTime(7);
			        	vmInfo.initVmCpuUsedRate(monitorNodeId,peroid,timeType);
		        	}else if("Month" == timeType){
		        		var peroid = getThePeridTime(30);
			        	vmInfo.initVmCpuUsedRate(monitorNodeId,peroid,timeType);
		        	}
		        }else if("memory" == value){
		        	$("#monitorContent .customPanel").css("display","none");
		        	$("#monitorContent .customPanel").eq(1).css("display","block");
		        	
		        	if("Day" == timeType){
		        		var peroid = getThePeridTime(1);
			        	vmInfo.initVmMemoryUsedRate(monitorNodeId,peroid,timeType);
		        	}else if("Week" == timeType){
		        		var peroid = getThePeridTime(7);
			        	vmInfo.initVmMemoryUsedRate(monitorNodeId,peroid,timeType);
		        	}else if("Month" == timeType){
		        		var peroid = getThePeridTime(30);
			        	vmInfo.initVmMemoryUsedRate(monitorNodeId,peroid,timeType);
		        	}
		        	
		        }else if("vd" == value){
		        	$("#monitorContent .customPanel").css("display","none");
		        	$("#monitorContent .customPanel").eq(2).css("display","block");
		        	
		        	if("Day" == timeType){
		        		var peroid = getThePeridTime(1);
			        	vmInfo.initVmDiskHistoryUsedRate(monitorNodeId,peroid,timeType);
		        	}else if("Week" == timeType){
		        		var peroid = getThePeridTime(7);
			        	vmInfo.initVmDiskHistoryUsedRate(monitorNodeId,peroid,timeType);
		        	}else if("Month" == timeType){
		        		var peroid = getThePeridTime(30);
			        	vmInfo.initVmDiskHistoryUsedRate(monitorNodeId,peroid,timeType);
		        	}
		        }
	        }
        }
 	});
    
    
    
	// 软件分类改动
	$('#add-vm-software_category').on('select',function (event){
	    	var args = event.args;
	        if (args) {
		        var item = args.item;
		        var value = item.value;
		        
		        var codesearch = new codeModel({width:150,autoDropDownHeight:true});
            	codesearch.getCommonCodeByConditions("add-vm-software_type",false,{parentCodeValue:value});
	        }
	 });
	
	// 软件类型改动
	$('#add-vm-software_type').on('select',function (event){
	    	var args = event.args;
	        if (args) {
		        var item = args.item;
		        var value = item.value;
		        
		        var codeversion = new codeModel({width:150,dropDownHeight:150,autoDropDownHeight:false});
            	codeversion.getCommonCodeByConditions("add-vm-software_version",false,{parentCodeValue:value});
	        }
	 });
    
	// 监控信息,默认选中cpu
    if(monitorNodeId == null || monitorNodeId == "" || monitorNodeId == "null"){
    }else{
    	info.initMonitorInfo();
    }
</script>

</html>
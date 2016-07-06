<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="addVeWindow">
         <div>新增资源环境</div>
         <div id="addVeForm" style="overflow: hidden;">
             <div style="width:100%;height:100%;">
             	 <div style="width:30%;height:100%;float:left;border-right:1px solid #DADADA;">
             	 	<ul style="list-style:none;width:100%;height:80%;margin:0px;padding:0px;">
             	 		<li id="virtualInfo" style="width:100%;height:35px;margin-top:10px;color:#0099d7"><i class='icon-ok'></i>资源环境信息</li>
             	 		<li id="confirmInfo"style="width:100%;height:35px;color:gray"><i class='icon-ok'></i>确定信息</li>
             	 	</ul>
             	 </div>
             	 <div style="position:relative;width:69.3%;height:100%;float:left;overflow:hidden;">
             	 	<div id="veInfo" style="position:absolute;top:0px;left:0px;width:100%;height:100%;">
             	 		<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>区域:</td>
		                        <td align="left">
		                           <div id="add-ve-zone"></div>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>数据中心:</td>
		                        <td align="left">
		                             <div data-name="parentTopologySid" id="add-ve-dc"></div>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>环境名称:</td>
		                        <td align="left"><input type="text" data-name="resTopologyName" id="add-ve-resTopologyName"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>拓扑类型:</td>
		                        <td align="left">
		                            <div data-name="resTopologyType" id="add-ve-resTopologyType"></div>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>虚拟平台类型:</td>
		                        <td align="left">
		                           <div data-name="virtualPlatformType" id="add-ve-virtualPlatformType"></div>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>虚拟平台版本:</td>
		                        <td align="left">
		                            <div data-name="virtualPlatformVersion" id="add-ve-virtualPlatformVersion"></div>
		                        </td>
		                    </tr>
		                    <tr class="otherIvm">
		                        <td align="right"><font style="color:red">*</font>管理地址:</td>
		                        <td align="left"><input type="text" data-name="managementUrl" id="add-ve-managementUrl"/></td>
		                    </tr>
		                    <tr class="otherIvm">
		                        <td align="right"><font style="color:red">*</font>用户名:</td>
		                        <td align="left"><input type="text" data-name="managementAccount" id="add-ve-managementAccount"/></td>
		                    </tr>
		                    <tr class="otherIvm">
		                        <td align="right"><font style="color:red">*</font>密码:</td>
		                        <td align="left"><input type="password" data-name="managementPassword" id="add-ve-managementPassword"/></td>
		                    </tr>
		                    <tr class="otherIvm">
		                        <td align="right"><font style="color:red">*</font>确认密码:</td>
		                        <td align="left"><input type="password"  id="add-ve-confirmPassword"/></td>
		                    </tr>
		                    <tr class="otherIvm">
		                        <td align="right"><font style="color:red">*</font>更新周期(小时):</td>
		                        <td align="left"><input type="text" data-name="updateCycle" id="add-ve-updateCycle"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right" colspan="2" height="32px">
		                        	<input style="margin-right: 5px;" onclick="confirmVirtualInfo()" type="button" id="addVeSave" value="下一步" />
		              				<input id="addVeCancel" type="button" value="取消" />&nbsp;&nbsp;
		                        </td>
		                    </tr>
		                </table>
             	 	</div>
             	 	<div id="confirmVeInfo" style="position:absolute;top:0px;left:304px;width:100%;height:100%;">
             	 		<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" ><font style="color:red">*</font>区域:</td>
		                        <td align="left" width="160px" height="23px">
		                           <span id="add-ve-confirm-zone"></span>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>数据中心:</td>
		                        <td align="left" height="23px">
		                           <span id="add-ve-confirm-dc"></span>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>环境名称:</td>
		                        <td align="left" height="23px">
		                           <span id="add-ve-confirm-resTopologyName"></span>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>拓扑类型:</td>
		                        <td align="left" height="23px">
		                            <span id="add-ve-confirm-resTopologyType"></span>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>虚拟平台类型:</td>
		                        <td align="left" height="23px">
		                           <span id="add-ve-confirm-virtualPlatformType"></span>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>虚拟平台版本:</td>
		                        <td align="left" height="23px">
		                            <span id="add-ve-confirm-virtualPlatformVersion"></span>
		                        </td>
		                    </tr>
		                    <tr class="otherIvm">
		                        <td align="right"><font style="color:red">*</font>管理地址:</td>
		                        <td align="left" height="23px">
		                           <span id="add-ve-confirm-managementUrl"></span>
		                        </td>
		                    </tr>
		                    <tr class="otherIvm">
		                        <td align="right"><font style="color:red">*</font>用户名:</td>
		                        <td align="left" height="23px">
		                           <span id="add-ve-confirm-managementAccount"></span>
		                        </td>
		                    </tr>
		                    <tr class="otherIvm">
		                        <td align="right"><font style="color:red">*</font>密码:</td>
		                        <td align="left" height="23px">
		                           <span id="add-ve-confirm-managementPassword"></span>
		                        </td>
		                    </tr>
		                    <tr class="otherIvm">
		                        <td align="right"><font style="color:red">*</font>确认密码:</td>
		                        <td align="left" height="23px">
		                           <span id="add-ve-confirm-confirmPassword"></span>
		                        </td>
		                    </tr>
		                    <tr class="otherIvm">
		                        <td align="right"><font style="color:red">*</font>更新周期(小时):</td>
		                        <td align="left" height="23px">
		                            <span id="add-ve-confirm-updateCycle"></span>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right" colspan="2" height="32px">
		                        	<input style="margin-right: 5px;" onclick="goInsertVirtualInfo()" type="button" id="addVeGoVirtual" value="上一步" />
		                        	<input style="margin-right: 5px;" onclick="saveConfirmVirtualInfo()" type="button" id="addVeConfirmSave" value="确定" />
		              				<input id="addVeConfirmCancel" onclick="closeAddVirtualWindow()" type="button" value="取消" />&nbsp;&nbsp;
		                        </td>
		                    </tr>
		                </table>
             	 	</div>	
             	 
             	 </div>
             </div>
         </div>
 </div>
 
  <!-- 新增集群Window -->
  <div id="addHostClusterWindow">
          <div>新增集群</div>
          <div id="addHostClusterForm" style="overflow: hidden;">
         		<input type="hidden" data-name="parentTopologySid" id="add-cluster-parentTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="add-cluster-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="5" cellpadding="2">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>集群名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="add-cluster-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">集群描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="add-cluster-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="addHostClusterSubmit()" type="button" id="addHostClusterSave" value="保存" />
		              		<input id="addHostClusterCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  <div id="newfileUploadWindow">
  		<div>批量导入</div>
  		 <div id="newfileUploadForm" style="overflow: hidden;">
		<form id="upload" name="upload" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
			<table class="main">
			<!-- 	 <tr>
					<td height="5px" align="right" style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择附件：</td>
					<td align="left" class="l-border-bottom-blue"colspan="3"> 
	     				<input type="file" id="fileAttach" name="fileAttach" style='width: 215px;cursor: pointer;' />
					</td>
				</tr>  -->
				<tr>
					<td>
						<div  class="box">
			            <input type="file" class="uploadFile" id="fileAttach" name="fileAttach" onchange="getFile(this,'copyFile')" />
						<input type="text" name="copyFile"  class="textbox" />
						<a href="javascript:void(0);"  class="link">浏览</a>
						</div>
		            </td>
				</tr> 
 				<tr>
					<td height="1px" align="right" style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><span id="info" height="10px" align="right" style="font-size: 14px"></span></td>
				</tr> 
				 <tr>
                    <td align="right" colspan="4">
                    <input style="margin-right: 5px;" onclick='importHostStorangModel()' type="button" id="importFile" value="导入" />
                    <input id="importFileCancel" type="button" value="取消" /></td>
                 </tr>
			</table>
		</form>
		</div>
   </div>
 

<script type="text/javascript">
	 //初始化sys-index页面model
	 var veAdd = new virtualVeAddModel();
	 // 初始化弹出框
	 veAdd.initPopWindow();
	 // 关联下拉列表框
	 veAdd.initComboxLinkage();
	 // 验证初始化
	 veAdd.initValidator();
	 
	 $("#add-ve-virtualPlatformType").on('select', function (event){
		    var args = event.args;
		    if (args) {
			    var item = args.item;
			    var value = item.value;
			    if("IVM" == value || "Other" == value){
			    	$(".otherIvm").hide();
			    }else{
			    	$(".otherIvm").show();
			    }
			}                        
	 });

	 
	 function getFile(obj,inputName){
		var file_name = $(obj).val();
		file_name = file_name.substring(file_name.lastIndexOf("\\")+1,file_name.length);
		$("input[name='"+inputName+"']").val(file_name);
	 }
</script>

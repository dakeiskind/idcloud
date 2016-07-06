<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="findImageWindow">
         <div>发布操作系统模板</div>
         <div id="findImageForm" style="overflow: auto;">
<!--          <div id="findImageForm" style="overflow: hidden;"> -->
         <input type="hidden" data-name="uuid" id="uuid"/>
         <input type="hidden" data-name="resVeSid" id="add-image-resVeSid"/>
        <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
              <table border="0" width="100%" cellspacing="4" cellpadding="0">
              				<tr>
		                        <td align="right" style="width:15%;"><font style="color:red">*</font>模板名称:</td>
		                        <!-- <td align="left" colspan="3"><input type="text" data-name="imageName" id="add-image-imageName"/></td>  -->
		                        <td align="left"><input type="text" data-name="imageName" id="add-image-imageName"/></td> 
		                        <td align="right"><font style="color:red">*</font>系统盘大小(GB):</td>
		                        <td align="left"><input type="text" data-name="imageSize" id="add-image-imageSize"/></td> 
		                    </tr>
		                     <tr>
		                        <td align="right"><font style="color:red">*</font>操作系统类型:</td>
		                        <td align="left">
		                           <div data-name="osType" id="add-image-osType"></div>
		                        </td>
		                   <!--  </tr>
		                    <tr> -->
		                        <td align="right"><font style="color:red">*</font>操作系统版本:</td>
		                        <td align="left">
		                             <div data-name="osVersion" id="add-image-osVersion"></div>
		                        </td>
		                    </tr> 							
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>管理账号:</td>
		                        <td align="left"><input type="text" data-name="managementAccount" id="add-image-osAdmin"/></td> 
		                    	
		                    <!-- </tr>
		                    <tr> -->
		                        <td align="right"><font style="color:red">*</font>管理密码:</td>
		                        <td align="left"><input type="password" data-name="managementPassword" id="add-image-osPasswd"/></td> 
		                    	
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>模板类型:</td>
		                         <td align="left" valign="middle" colspan="3">
		                         <div style="width:100%;height:100%;">
		                             <div data-name="imageType" id="add-image-imageType1" class="EditfrontRole" style='float: left;'><span>公共</span></div>
		                          <!--  <div data-name="imageType" id="add-image-imageType2" class="EditfrontRole" style='margin-left: 5px;float: left;'><span>专有</span></div> -->  
		                         </div>
		                        </td>
		                    </tr>
		                    <tr>
		                    	<td align="right">资源环境:</td>
		                        <td align="left" valign="middle" colspan="4" id="add-image-resve"></td>
                    		</tr>
		                </table>
		             </fieldset>
		                <fieldset style="border-color:#FCFCFC"><legend><b>数据库</b></legend>
		                	<table border="0" width="100%" cellspacing="4" cellpadding="0">
		                		<tr height="30">
		                        <!-- <td align="right" width="100"></td> -->
		                        <td align="left" valign="middle" colspan="4" id="add-imageSoftWare-database"></td>
                    		 </tr>
							</table>
		                </fieldset>
		                <fieldset style="border-color:#FCFCFC"><legend><b>中间件</b></legend>
		                	<table border="0" width="100%" cellspacing="4" cellpadding="0">
                    		  <tr height="30">
		                       <!--  <td align="right" width="100"></td> -->
		                        <td align="left" valign="middle" colspan="4" id="add-imageSoftWare"></td>
                    		 </tr>
							</table>
		                </fieldset>
		                <table border="0" width="100%" cellspacing="4" cellpadding="0">
		                	 <tr>
		                        <td align="right" colspan="3" height="32px">
		                        	<input style="margin-right: 5px;" onclick="confirmVirtualInfo()" type="button" id="addImageSave" value="确定" />
		              				<input id="addImageCancel" type="button" value="取消" />&nbsp;&nbsp;
		                        </td>
		                    </tr>
		                </table>
         </div>
 </div>

<script type="text/javascript">
	 //初始化sys-index页面model
	 var imageAdd = new virtualImageAddModel();
	 imageAdd.initInput();
	 // 初始化弹出框
	 imageAdd.initPopWindow();
	 // 关联下拉列表框
	 imageAdd.initComboxLinkage();
	 // 验证初始化
	imageAdd.initValidator();
	//imageAdd.setVmBasicInfo();
</script>

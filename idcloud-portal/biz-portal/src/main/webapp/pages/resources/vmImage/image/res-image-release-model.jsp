<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="releaseImageWindow">
         <div>同步操作系统模板</div>
         <div id="releaseImageForm" style="overflow: hidden;">
            <table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>区域:</td>
		                        <td align="left">
		                           <div id="release-image-zone"></div>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>数据中心:</td>
		                        <td align="left">
		                             <div data-name="parentTopologySid" id="release-image-dc"></div>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>资源环境:</td>
		                    	<td align="left">
		                             <div data-name="resTopologySid" id="release-image-resTopologyName"></div>
		                        </td>
		                     </tr>
		                     <tbody id="nimMessage" style="display: none;">
			                     <tr>
			                        <td align="right"><font style="color:red">*</font>IP地址:</td>
			                    	<td align="left"><input type="text" data-name="nimIp" id="add-image-nimIp"/></td> 
			                     </tr>
			                      <tr>
			                        <td align="right"><font style="color:red">*</font>用户名:</td>
			                    	<td align="left"><input type="text" data-name="nimUser" id="add-image-nimUser"/></td>
			                     </tr>
			                     <tr>
			                        <td align="right"><font style="color:red">*</font>密码:</td>
			                    	<td align="left"><input type="password" data-name="nimPassword" id="add-image-nimPassword"/></td>
			                     </tr>
		                     </tbody>
		                     <tr>
		                        <td align="right" colspan="2" height="32px">
		                        	<input style="margin-right: 5px;" onclick="releaseConfirmVirtualInfo()" type="button" id="releaseImageSave" value="确定" />
		              				<input id="releaseImageCancel" type="button" value="取消" />&nbsp;&nbsp;
		                        </td>
		                    </tr>
		                </table>
         </div>
 </div>
<script type="text/javascript">
	 //初始化sys-index页面model
	 var imageRelease = new virtualImageReleaseModel();
	 
	 // 验证初始化
	 imageRelease.initValidator();
	 // 初始化弹出框
	 imageRelease.initPopWindow();
	 // 关联下拉列表框
	 imageRelease.initComboxLinkage();
	
</script>
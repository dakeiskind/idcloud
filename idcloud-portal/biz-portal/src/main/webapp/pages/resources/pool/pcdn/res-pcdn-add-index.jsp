<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="addPcdnWindow">
       <div>新增CDN</div>
       <div id="addPcdnForm" style="overflow: hidden;">
           <div style="width:100%;height:100%;">
          <input type="hidden" data-name="resPoolSid" id="add-pcdn-parentTopologySid"/>
          	 	<table border="0" width="100%" cellspacing="5" cellpadding="0">
          	 	   <tr>
	          	 	   	<td align="right" nowrap="nowrap">资源环境：</td>
	           			<td>
	           				<div id="add-pcdn-resTopologySid" data-name="parentTopologySid"></div>
	           			</td>
          	 	   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>CDN名称:</td>
                       <td align="left">
                            <input type="text" data-name="cdnName" id="add-pcdn-cdnName"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>验证用户名:</td>
                       <td align="left">
                           <input type="text" data-name="cdnAccount" id="add-pcdn-cdnAccount"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>验证密码:</td>
                       <td align="left">
                           <input type="text" data-name="cdnPassword" id="add-pcdn-cdnPassword"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>CDN地址:</td>
                       <td align="left">
                           <input type="text" data-name="cdnAddress" id="add-pcdn-cdnAddress"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right" colspan="2" height="32px">
                       	<input style="margin-right: 5px;" onclick="submitAddCdn()" type="button" id="addPcdnSave" value="保存" />
             				<input id="addPcdnCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
               </table>
           	 </div>
       </div>
 </div>

<script type="text/javascript">
	 //初始化
	 var pcdnadd = new addPcdnModel();
	 // 关联下拉列表框
	 pcdnadd.initPopWindow();
	 // 验证初始化
	 pcdnadd.initValidator();
	// posadd.initComboxLinkage();
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="addPosWindow">
       <div>新增对象存储资源池</div>
       <div id="addPosForm" style="overflow: hidden;">
           <div style="width:100%;height:100%;">
           <input type="hidden" data-name="resPoolSid" id="add-pos-parentTopologySid"/>
          	 	<table border="0" width="100%" cellspacing="5" cellpadding="0">
          	 	   <tr>
	          	 	   	<td align="right" nowrap="nowrap">所属资源环境：</td>
	           			<td>
	           				<div id="add-pos-resTopologySid" data-name="parentTopologySid"></div>
	           			</td>
          	 	   </tr>
          	 	   <tr>
                       <td align="right"><font style="color:red">*</font>对象存储名称:</td>
                       <td align="left">
                            <input type="text" data-name="objStorageName" id="add-pos-objStorageName"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>对象存储方位地址:</td>
                       <td align="left">
                            <input type="text" data-name="osVisitAddress" id="add-pos-osVisitAddress"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>客户端下载地址:</td>
                       <td align="left">
                           <input type="text" data-name="clientDownloadUrl" id="add-pos-clientDownloadUrl"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>总容量:</td>
                       <td align="left">
                           <input type="text" data-name="totalCapacity" id="add-pos-totalCapacity"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>UUID:</td>
                       <td align="left">
                           <input type="text" data-name="uuid" id="add-pos-uuid"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right" colspan="2" height="32px">
                       	<input style="margin-right: 5px;" onclick="submitAddObjectStorage()" type="button" id="addPosSave" value="保存" />
             				<input id="addPosCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
               </table>
           	 </div>
       </div>
 </div>

<script type="text/javascript">
	 //初始化
	 var posadd = new addPosModel();
	 // 关联下拉列表框
	 posadd.initPopWindow();
	 // 验证初始化
	 posadd.initValidator();
	// posadd.initComboxLinkage();
</script>

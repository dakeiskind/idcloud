<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="editPosWindow">
       <div>编辑对象存储</div>
       <div id="editPosForm" style="overflow: hidden;">
           <div style="width:100%;height:100%;">
           <input type="hidden" data-name="resPoolSid" id="edit-pos-parentTopologySid"/>
            <input type="hidden" data-name="resOsSid" id="edit-pos-resOsSid"/>
          	 	<table border="0" width="100%" cellspacing="5" cellpadding="0">
          	 	   <tr>
	          	 	   	<td align="right" nowrap="nowrap">资源环境：</td>
	           			<td>
	           				<div id="edit-pos-resTopologySid" data-name="parentTopologySid"></div>
	           			</td>
          	 	   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>对象存储名称:</td>
                       <td align="left">
                            <input type="text" data-name="objStorageName" id="edit-pos-objStorageName"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>对象存储方位地址:</td>
                       <td align="left">
                            <input type="text" data-name="osVisitAddress" id="edit-pos-osVisitAddress"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>客户端下载地址:</td>
                       <td align="left">
                           <input type="text" data-name="clientDownloadUrl" id="edit-pos-clientDownloadUrl"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>总容量:</td>
                       <td align="left">
                           <input type="text" data-name="totalCapacity" id="edit-pos-totalCapacity"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>UUID:</td>
                       <td align="left">
                           <input type="text" data-name="uuid" id="edit-pos-uuid"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right" colspan="2" height="32px">
                       	<input style="margin-right: 5px;" onclick="submitEditObjectStorage()" type="button" id="editPosSave" value="保存" />
             				<input id="editPosCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
               </table>
           	 </div>
       </div>
 </div>

<script type="text/javascript">
	 //初始化
	 var posedit = new editPosModel();
	 // 关联下拉列表框
	 posedit.initPopWindow();
	 // 验证初始化
	 posedit.initValidator();
</script>

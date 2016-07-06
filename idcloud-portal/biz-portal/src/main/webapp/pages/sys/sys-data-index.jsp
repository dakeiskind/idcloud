<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/sys-data-model.js"></script>
		<style type="text/css">
			table{
				font-size: 12px;
				font-family: '微软雅黑', 'Microsoft Yahei', 'segoe ui', arial, sans-serif;
				color: #767676;
			}
		</style>
    </head>
  <body>
   <div style="width:100%;height:30px;padding:5px 0px 5px 0px;">
           <table  border="0" height="100%" cellspacing="0" cellpadding="2">
           		<tr>
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;操作系统类型：</td>
           			
           			<td align="left"><input type="text"id="search-code-osType"/></td>
           			<td align="right">&nbsp;&nbsp;操作系统版本：</td>
           			
           			<td align="left"><input type="text"id="search-code-osVersion"/></td>
           			<td><a  onclick="searchCodeOsVersion()" id="search-code-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:99%;height:90%;margin-left:0.5%;overflow-y:auto;"> 
     	<div id='codeOsVersionDatagrid' style="width:100%;height:450px;"></div> 
    </div>   
    
    <div id="addCodeWindow">
            <div>新增操作系统版本</div>
            <div id="addCodeForm" style="overflow: auto;">
                <table border="0" width="100%" cellspacing="5" cellpadding="3">
                    <tr>
                        <td align="right" style="width:20%"><font style="color:red">*</font>排序号:</td>
                        <td align="left"><input type="text" data-name="sort" id="add-sort"/></td>
                        <td align="right" style="width:20%"><font style="color:red">*</font>操作系统类型:</td>
                        <td align="left" >
                            <div  data-name="parentCodeValue" id="add-parentCodeValue"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>操作系统版本:</td>
                        <td align="left"><input type="text" data-name="codeValue" id="add-codeValue"/></td>
                         <td align="right"><font style="color:red">*</font>操作系统版本(显示值):</td>
                        <td align="left"><input type="text" data-name="codeDisplay" id="add-codeDisplay"/></td>
                    </tr>
                    <tr>
                        <td align="right">属性1:</td>
                        <td align="left"><input type="text" data-name="attribute1" id="add-attribute1"/></td>
                        <td align="right">属性2:</td>
                        <td align="left"><input type="text" data-name="attribute2" id="add-attribute2"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>是否启用:</td>
                        <td align="left" >
                            <div  data-name="enabled" id="add-enabled"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='submitAddCodeInfo()' type="button" id="codeSave" value="保存" />
                        <input id="codeCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
       
       <div id="editCodeWindow">
            <div>编辑操作系统版本</div>
            <div id="editCodeForm" style="overflow: auto;">
            	<input type="hidden" data-name="codeSid" id="codeSid"/>
            	<input type="hidden" data-name="sort" id="edit-sort"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:20%"><font style="color:red">*</font>操作系统类型:</td>
                        <td align="left" >
                            <div  data-name="parentCodeValue" id="edit-parentCodeValue"></div>
                        </td>
                        <td align="right"><font style="color:red">*</font>是否启用:</td>
                        <td align="left" >
                            <div  data-name="enabled" id="edit-enabled"></div>
                        </td>
                    </tr>
                    <tr>
                          <td align="right"><font style="color:red">*</font>操作系统版本:</td>
                        <td align="left"><input type="text" data-name="codeValue" id="edit-codeValue"/></td>
                         <td align="right"><font style="color:red">*</font>操作系统版本(显示值):</td>
                        <td align="left"><input type="text" data-name="codeDisplay" id="edit-codeDisplay"/></td>
                    </tr>
                   <tr>
                        <td align="right">属性1:</td>
                        <td align="left"><input type="text" data-name="attribute1" id="edit-attribute1"/></td>
                        <td align="right">属性2:</td>
                        <td align="left"><input type="text" data-name="attribute2" id="edit-attribute2"/></td>
                    </tr>
                    </tbody>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" type="button" onclick='submitEditCodeInfo()'  id="editCodeSave" value="保存" />
                        <input id="editCodeCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
       <div id="addTypeCodeWindow">
            <div>添加操作系统类型</div>
            <div id="addTypeCodeForm" style="overflow: auto;">
               <table border="0" width="100%" cellspacing="5" cellpadding="3">
                    <tr>
                        <td align="right" style="width:20%"><font style="color:red">*</font>操作系统类型:</td>
                        <td align="left"><input type="text" data-name="codeValue" id="add-type-codeValue"/></td>
                        <td align="right"><font style="color:red">*</font>是否启用:</td>
                        <td align="left" >
                            <div  data-name="enabled" id="add-type-enabled"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">属性1:</td>
                        <td align="left"><input type="text" data-name="attribute1" id="add-type-attribute1"/></td>
                        <td align="right">属性2:</td>
                        <td align="left"><input type="text" data-name="attribute2" id="add-type-attribute2"/></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='submitAddTypeCodeInfo()' type="button" id="typecodeSave" value="保存" />
                        <input id="typecodeCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
  </body>     
</html>          
       <script type="text/javascript">
			 // 初始化sys-index页面model
   			 var syscodemodel = new syscodeModel();
       		 // 初始化页面组件
   			 syscodemodel.initInput();
       		 // 初始化弹出框
   			 syscodemodel.initPopWindow();
       		 // 初始化datagrid
   			 syscodemodel.initCodeDatagrid();
   			 // 初始化组件验证规则
   			 syscodemodel.initValidator();
       </script>
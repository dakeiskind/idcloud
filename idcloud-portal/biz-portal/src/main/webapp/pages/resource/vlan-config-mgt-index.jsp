<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;VLAN名称：</td>
            			<td><input type="text" id="search-vlan-name" />&nbsp;&nbsp;</td>
            			<td align="right">&nbsp;&nbsp;管理状态：</td>
            			<td>
            				<div id="search-vlan-mgt-status"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;使用状态：</td>
            			<td>
            				<div id="search-valn-usage-status"></div>
            			</td>
            			<td><input type="button" value="查询" data-bind='click:searchVlanConfigMgt' id='search-vlan-button' /></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='vlanConfigMgtdatagrid' style="width:100%;height:450px;"></div> 
    </div>  
    
    <div id="addVlanWindow">
            <div>新增VLAN</div>
            <div id="addVlanForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>VLAN ID:</td>
                        <td align="left"><input type="text" data-name="vlanId" id="add-vlanId"/></td>
                        <td align="right"><font style="color:red">*</font>VLAN名称:</td>
                        <td align="left"><input type="text" data-name="vlanName" id="add-vlanName"/></td>
                    </tr>
                    <tr>
                        <td align="right">标签:</td>
                        <td align="left"><input type="text" data-name="tag" id="add-tag"/></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4" height="40px">
                        <input style="margin-right: 5px;" data-bind='click:addVlanSubmit' type="button" id="addVlanSave" value="保存" />
                        <input id="addVlanCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>  
       
        <div id="editVlanWindow">
            <div>编辑VLAN</div>
            <div id="editVlanForm" style="overflow: hidden;">
            	<input type="hidden" data-name="resSid" id="resSidVlan"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>VLAN ID:</td>
                        <td align="left"><input type="text" data-name="vlanId" id="edit-vlanId"/></td>
                        <td align="right"><font style="color:red">*</font>VLAN名称:</td>
                        <td align="left"><input type="text" data-name="vlanName" id="edit-vlanName"/></td>
                    </tr>
                    <tr>
                        <td align="right">标签:</td>
                        <td align="left"><input type="text" data-name="tag" id="edit-tag"/></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4" height="40px">
                        <input style="margin-right: 5px;" data-bind='click:editVlanSubmit' type="button" id="editVlanSave" value="保存" />
                        <input id="editVlanCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>  
    
      <script type="text/javascript">
      		function initVlanConfigJs(){
	       		 // 初始化sys-index页面model
	   			 var Vlanconfig = new VlanConfigModel();
	       		 // 初始化页面组件
	   			 Vlanconfig.initInput();
	       		 // 初始化组件验证规则
	   			 Vlanconfig.initValidator();
	       		 // 初始化弹出框
	   			 Vlanconfig.initPopWindow();
	       		 // 初始化datagrid
	   			 Vlanconfig.initVlanDatagrid();
	       		 // 为datagrid赋值
	   			 Vlanconfig.searchVlanConfigInfo();
	   	
	   			 return Vlanconfig;
      		}
  
      </script>
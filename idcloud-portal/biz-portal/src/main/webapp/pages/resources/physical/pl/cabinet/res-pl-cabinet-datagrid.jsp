<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;机柜名称：</td>
           			<td><input type="text" id="search-cabinetName" />&nbsp;&nbsp;</td>
                        <td align="right">所属机房:</td>
                        <td align="left" colspan="3">
                            <div id="mf-cabinet-name"></div>
                        </td>
           			<td><a onclick="searchcabinet()" id="search-cabinetButton"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='cabinetdatagrid' style="width:100%;height:450px;"></div> 
    </div>
    
     <!-- 新增机柜页面 -->
     <div id="addCabWindow">
            <div>新增机柜</div>
            <div id="addCabForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>机柜名称:</td>
                        <td align="left"><input type="text" data-name="name" id="add-cabinet-name"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>位置:</td>
                        <td align="left" colspan="3"><input type="text" data-name="location" id="add-cabinet-location"/></td>
                    </tr>
                    <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="add-cabinet-resTopologySid"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>所属机房:</td>
                        <td align="left" colspan="3">
                            <div data-name="equipRoomSid" id="add-mf-cabinet-name"></div>
                        </td>
                    </tr>
                   <!--  <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="add-cabinet-description"></textarea></td>
                    </tr> -->
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='submitAddCabinet()' type="button" id="cabinetSave" value="保存" />
                        <input id="cabinetCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
       <!-- 编辑机房页面 -->
       <div id="editCabWindow">
            <div>编辑机柜</div>
            <div id="editCabForm" style="overflow: hidden;">
            <input type="hidden" data-name="equipSid" id="edit-equipCabinetSid"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>机柜名称:</td>
                        <td align="left"><input type="text" data-name="name" id="edit-cabinet-name"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>位置:</td>
                        <td align="left" colspan="3"><input type="text" data-name="location" id="edit-cabinet-location"/></td>
                    </tr>
                    <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="edit-cabinet-resTopologySid"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>所属机房:</td>
                        <td align="left" colspan="3">
                            <div data-name="equipRoomSid" id="edit-mf-cabinet-name"></div>
                        </td>
                    </tr>
                   <!--  <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="edit-cabinet-description"></textarea><input type="text" data-name="description" id="add-description"/></td>
                    </tr> -->
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='submitEditCabInfo()' type="button" id="editCabinetSave" value="保存" />
                        <input id="editCabinetCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>  
	
</div>

<script type="text/javascript">
//初始化sys-index页面model
	function cabinetDatagridModel(){
		var cabinet = new cabinetDatagridModels();
		 // 初始化页面组件
		 cabinet.initCabinetInput();
		 // 初始化datagrid
		 cabinet.initCabinetDatagrid();
		 // 为datagrid赋值
		 cabinet.initComboxLinkage();
		 cabinet.initPopWindow();
		 cabinet.initValue();
	
    }
	 
</script>

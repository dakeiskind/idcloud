<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;机房名称：</td>
           			<td><input type="text" id="search-labHouseName" />&nbsp;&nbsp;</td>
           			<!-- <td align="right" nowrap="nowrap">区域：</td>
           			<td>
           				<div id="search-labhost-rType"></div>
           			</td>
           			<td align="right" nowrap="nowrap">数据中心：</td>
           			<td>
           				<div id="search-labhost-dcType"></div>
           			</td> -->
           			<td><a onclick="searchLabHouse()" id="search-labHouseButton"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='labHousedatagrid' style="width:100%;height:450px;"></div> 
    </div>
    <!-- 新增机房页面 -->
     <div id="addHWindow">
            <div>新增机房</div>
            <div id="addHForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>机房名称:</td>
                        <td align="left"><input type="text" data-name="name" id="add-name"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>位置:</td>
                        <td align="left" colspan="3"><input type="text" data-name="location" id="add-location"/></td>
                    </tr>
                    <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="add-resTopologySid"/></td>
                    </tr>
                    <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="add-description"></textarea><!-- <input type="text" data-name="description" id="add-description"/> --></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='submitAddHInfo()' type="button" id="Save" value="保存" />
                        <input id="Cancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div> 
       <!-- 编辑机房页面 -->
       <div id="editHWindow">
            <div>编辑机房</div>
            <div id="editHForm" style="overflow: hidden;">
            <input type="hidden" data-name="equipRoomSid" id="edit-equipServerRoomSid"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>机房名称:</td>
                        <td align="left"><input type="text" data-name="name" id="edit-name"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>位置:</td>
                        <td align="left" colspan="3"><input type="text" data-name="location" id="edit-location"/></td>
                    </tr>
                    <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="edit-resTopologySid"/></td>
                    </tr>
                    <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="edit-description"></textarea><!-- <input type="text" data-name="description" id="add-description"/> --></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='submitEditHInfo()' type="button" id="editSave" value="保存" />
                        <input id="editCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div> 
	
</div>

<script type="text/javascript">
//初始化sys-index页面model
	function labHouseDatagridModel(){
		var labHouse = new labHouseDatagridModels();
		 // 初始化页面组件
		 labHouse.initLabHouseInput();
		 // 初始化datagrid
		 labHouse.initLabHouseDatagrid();
		 // 为datagrid赋值
		 labHouse.searchLabHouseInfo();
		 
		 labHouse.initPopWindow();
		 labHouse.initValue();
		 
	
    }
	 
</script>

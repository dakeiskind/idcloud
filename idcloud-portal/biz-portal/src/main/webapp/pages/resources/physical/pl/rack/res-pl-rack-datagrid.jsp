<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;机架编号：</td>
           			<td><input type="text" id="search-rackName" />&nbsp;&nbsp;</td>
                 <!-- <td align="right">所属机房:</td>
                 <td align="left" colspan="3">
                    <div id="rc-rack-roomname"></div>
                </td> -->
                <td align="right">所属机柜:</td>
                <td align="left" colspan="3">
                    <div id="rc-rack-name"></div>
                </td>
           			<td><a onclick="searchRack()" id="search-rackButton"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='rackdatagrid' style="width:100%;height:450px;"></div> 
    </div>
    <!-- 新增机房页面 -->
     <div id="addRCWindow">
            <div>新增机架</div>
            <div id="addRCForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>机架编号:</td>
                        <td align="left"><input type="text" data-name="name" id="add-rc-name"/></td>
                    </tr>
                   <!--  <tr>
                        <td align="right"><font style="color:red">*</font>位置:</td>
                        <td align="left" colspan="3"><input type="text" data-name="location" id="add-rc-location"/></td>
                    </tr> -->
                    <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="add-rc-resTopologySid"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>所属机柜:</td>
                        <td align="left" colspan="3">
                            <div data-name="equipCabinetSid" id="add-rc-equipCabinetSid"></div>
                        </td>
                    </tr>
              <!--  <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="add-description"></textarea><input type="text" data-name="description" id="add-description"/></td>
                    </tr> -->
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='submitAddRack()' type="button" id="rackSave" value="保存" />
                        <input id="rackCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div> 
       <!-- 编辑机房页面 -->
       <div id="editRCWindow">
            <div>编辑机房</div>
            <div id="editRCForm" style="overflow: hidden;">
            <input type="hidden" data-name="equipSid" id="edit-equipRackSid"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>机房名称:</td>
                        <td align="left"><input type="text" data-name="name" id="edit-rc-name"/></td>
                    </tr>
                    <!-- <tr>
                        <td align="right"><font style="color:red">*</font>位置:</td>
                        <td align="left" colspan="3"><input type="text" data-name="location" id="edit-rc-location"/></td>
                    </tr> -->
                    <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="edit-rc-resTopologySid"/></td>
                    </tr>
                     <tr>
                        <td align="right"><font style="color:red">*</font>所属机柜:</td>
                        <td align="left" colspan="3">
                            <div data-name="equipCabinetSid" id="edit-rc-equipCabinetSid"></div>
                        </td>
                    </tr>
                    <!-- <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="edit-description"></textarea><input type="text" data-name="description" id="add-description"/></td>
                    </tr> -->
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='submitEditRCInfo()' type="button" id="editRcSave" value="保存" />
                        <input id="editRcCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div> 
</div>

<script type="text/javascript">
//初始化sys-index页面model
	function rackDatagridModel(){
		var rack = new rackDatagridModels();
		 // 初始化页面组件
		 rack.initRackInput();
		 rack.initComboxLinkage();
		 // 初始化datagrid
		 rack.initRackDatagrid();
		 // 为datagrid赋值
		 rack.searchRackInfo();
		 rack.initPopWindow();
		 rack.initValue();
    }
</script>

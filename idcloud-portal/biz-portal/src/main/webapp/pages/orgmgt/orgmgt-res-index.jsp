<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="serviceCatalog" style="width:99.5%;height:99.2%;margin-left:0.3%;margin-top:0.3%;min-width: 1000px;">
	<div id='jqxExpanderOrg' style="display: inline; float: left;width: 20%;height:100%;">
		<div>组织管理</div>
		<div >
			<div style="border: none;" id='jqxTreeOrg'>
				<div id='jqxOrgMenu'>
		            <ul>
		                <li>新增</li>
		                <li>编辑</li>
		                <li>删除</li>
		            </ul>
		         </div>
			</div>
		</div>
	</div>
	<div id='jqxTabsOrg' style="display: inline; float: left; margin-left: 3px;">
		<ul>
			<li style="font-size: 14px">资源关联</li>
		</ul>
		<div>
		
			<div style="width: 69%;float:left;">
					<div id='jqxgridOrg' style="margin-left: 1%"></div>
			</div>
			
			<div id='jqxExpanderRes' style="display: inline; float: right;margin-right: 3px;width: 20%;height:90%;">
				<div>主机资源</div>
				<div >
					<div style="border: none;height:80%;" id='jqxTreeHost'>
					</div>
				</div>
			</div>
			
		<div id="addOrgWindow" style="display:none">
		     	<div>新增组织</div>
		     	<div id="addOrgForm" style="overflow: hidden;">
		     	 <input type="hidden" data-name="parentOrgSid" id="add-parentOrgSid"/>
		     	
		         <table border="0" width="100%" cellspacing="5" cellpadding="0">
		             <tr>
		                 <td align="right"><font style="color:red">*</font>组织名称:</td>
		                 <td align="left"><input type="text" data-name="orgName" id="add-orgName"/></td>
		             </tr> 
		             <tr>
						 <td align="right">父组织:</td>
		                 <td align="left" ><input type="text" data-name="parentOrgName" id="add-parentOrgName"/></td>
		             </tr>
		             <tr>
						 <td align="right">排序:</td>
                        <td align="left"><div data-name="sortRank" id='add-sortRank'></div></td>
		             </tr>
		             <tr>             
		                 <td align="right">组织描述:</td>
		                 <td align="left" colspan="3"><textarea data-name="orgDesc" id="add-orgDesc"></textarea></td>
		             </tr>
		             <tr>
		                 <td align="right" colspan="4">
		                 <input style="margin-right: 5px;" data-bind='click: addOrgSubmit' type="button" id="saveAddOrg" value="保存" />
		                 <input id="cancelAddOrg" type="button" value="取消" /></td>
		             </tr>
		         </table>
     			</div>
			</div>
			
			<div id="editOrgWindow" style="display:none">
		     	<div>编辑组织</div>
		     	<div id="editOrgForm" style="overflow: hidden;">
		     	<input type="hidden" data-name="orgSid" id="edit-orgSid"/>
		     	
		        <table border="0" width="100%" cellspacing="5" cellpadding="0">
		             <tr>
		                 <td align="right"><font style="color:red">*</font>组织名称:</td>
		                 <td align="left"><input type="text" data-name="orgName" id="edit-orgName"/></td>
		             </tr>
		             <tr>
						 <td align="right">父组织:</td>
		                 <td align="left" ><input type="text" data-name="parentOrgName" id="edit-parentOrgName"/></td>
		             </tr>
		             <tr>
						<td align="right">排序:</td>
                        <td align="left"><div data-name="sortRank" id='edit-sortRank'></div></td>
		             </tr>
		             <tr>             
		                 <td align="right">组织描述:</td>
		                 <td align="left" colspan="3"><textarea data-name="orgDesc" id="edit-orgDesc"></textarea></td>
		             </tr>
		             <tr>
		                 <td align="right" colspan="4">
		                 <input style="margin-right: 5px;" data-bind='click: editOrgSubmit' type="button" id="saveEditOrg" value="保存" />
		                 <input id="cancelEditOrg" type="button" value="取消" /></td>
		             </tr>
		         </table>
     			</div>
			</div>
		</div>
		
	</div>
</div>

<script type="text/javascript">
//定义右击弹出框
var contextMenu;
$(function(){
	
	initOrgTempPopWindow();
	//initOrgAddValidator();
	//initOrgEditValidator();
	
	// 当选择某个item的时候
    $('#jqxTreeOrg').on('select', function (event) {
    	var args = event.args;
        var item = $('#jqxTreeOrg').jqxTree('getItem', args.element);  
    });
    // 初始化右击操作
    contextMenu = $("#jqxOrgMenu").jqxMenu({ width: '120px',  height: '56px', autoOpenPopup: false, mode: 'popup',theme:currentTheme });
     // 当按键按下tree的时候
   attachContextMenu();
    
     // 当选中右击操作框的时候
	  $("#jqxOrgMenu").on('itemclick', function (event) {
        var item = $.trim($(event.args).text());
        switch (item) {
            case "新增":
                var item = $('#jqxTreeOrg').jqxTree('selectedItem');
                if (item != null) {
               		popAddOrgWindow(item.value);
               		//setOrgTreeValue();
              	  	attachContextMenu();
                }
                break;
            case "编辑":
          	  var item = $('#jqxTreeOrg').jqxTree('selectedItem');
          	  if (item != null) {
             	  popEditOrgWindow(item.value);
             	  //setOrgTreeValue();
                  attachContextMenu();
                }
                break;
            case "删除":
             	  var item = $('#jqxTreeOrg').jqxTree('selectedItem');
             	  if (item != null) {
             		orgDelItem();
             		//setOrgTreeValue();
                    attachContextMenu();
                  }
               break;
        }
    });
});

 
 // 是否右击
 function isRightClick(event) {
      var rightclick;
      if (!event) var event = window.event;
      if (event.which) rightclick = (event.which == 3);
      else if (event.button) rightclick = (event.button == 2);
      return rightclick;
  }
 
 // 禁止浏览器默认的右击
  $(document).on('contextmenu', function (e) {
        if ($(e.target).parents('.jqx-tree').length > 0) {
            return false;
        }
        return true;
  });
// 当按键按下tree的时候
 function attachContextMenu(){
	 $("#jqxTreeOrg").on('mousedown', function (event) {
            var target = $(event.target).parents('li:first')[0];
            var rightClick = isRightClick(event);
            if (rightClick && target != null) {
                $("#jqxTreeOrg").jqxTree('selectItem', target);
                var scrollTop = $(window).scrollTop();
                var scrollLeft = $(window).scrollLeft();
                contextMenu.jqxMenu('open', parseInt(event.clientX) + 5 + scrollLeft, parseInt(event.clientY) + 5 + scrollTop);
                return false;
            }
        });
 }
 
 
//弹出新增window
 function popAddOrgWindow(orgSid){
		// 初始化用户新增页面
     $("#add-orgName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
     $("#add-parentOrgName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme, disabled: true});
     $("#add-sortRank").jqxNumberInput({ width: '150px', height: '22px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 0, max: 10, value: 0});
     $("#add-orgDesc").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
 	 $("#saveAddOrg").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
	 $("#cancelAddOrg").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });	
     $("#add-orgName").val("");
     $("#add-orgDesc").val("");
	 var data = searchOrg(orgSid);
     $("#add-parentOrgName").val(data.orgName);
     $("#add-parentOrgSid").val(data.orgSid);

 	 // 设置弹出框位置
 	 var windowW = $(window).width(); 
 	 var windowH = $(window).height(); 
 	 $("#addOrgWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-220)/2 } });
 	 $("#addOrgWindow").jqxWindow('open');
 }


 //弹出新增ip池window
 function popEditOrgWindow(orgSid){
	// 初始化用户新增页面
    $("#edit-orgName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
    $("#edit-parentOrgName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme, disabled: true });
    $("#edit-sortRank").jqxNumberInput({ width: '150px', height: '22px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 0, max: 10, value: 0});
    $("#edit-orgDesc").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
	$("#saveEditOrg").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
	$("#cancelEditOrg").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });	    
	// 将常用的字段可以使用这个方法设置数据
	setEditOrgForm(orgSid);
 	// 设置弹出框位置
 	var windowW = $(window).width(); 
 	var windowH = $(window).height(); 
 	$("#orgSid").val(orgSid);
 	$("#editOrgWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-220)/2 } });
 	$("#editOrgWindow").jqxWindow('open');
 }
 
 this.setEditOrgForm = function(orgSid){
	 var data = searchOrg(orgSid);
	 
	 $("#edit-orgSid").val(data.orgSid);
 	 $("#edit-sortRank").jqxNumberInput('val', data.sortRank);
 	 $("#edit-orgName").jqxInput({value:data.orgName});
     $("#edit-parentOrgName").jqxInput({value:data.parentOrgName});
     $("#edit-orgDesc").jqxInput({value:data.orgDesc});
 };
 
 // 初始化模板弹出window
 this.initOrgTempPopWindow = function(){
		$("#addOrgWindow").jqxWindow({
             width: 400, 
             height:240,
             resizable: false,  
             isModal: true, 
             autoOpen: false, 
             theme:currentTheme,
             cancelButton: $("#cancelAddOrg"), 
             modalOpacity: 0.3           
      });
		
		$("#editOrgWindow").jqxWindow({
         width: 400, 
         height:240,
         resizable: false,  
         isModal: true, 
         theme:currentTheme,
         autoOpen: false, 
         cancelButton: $("#cancelEditOrg"), 
         modalOpacity: 0.3           
  });
 };
 
	// 根据sid查询资源池信息
	this.searchOrg = function(orgSid){
	var orgData;
	Core.AjaxRequest({
			url : ws_url + "/rest/org/"+orgSid+"",
			type:"get",
			async:false,
			callback : function (data) {
				orgData = data;
		    },
		    failure:function(data){
		    	
		    }
	 });
	return orgData;
};
 
 
/* // 提交新增的信息 
	this.addOrgSubmit = function(){
	 alert(111);
		// 判断是否通过了验证
		$('#addOrgForm').jqxValidator('validate');
	};
	// 提交新增的信息 
	this.editOrgSubmit = function(){
		// 判断是否通过了验证
		$('#editOrgForm').jqxValidator('validate');
	}; */
	
 /* 	// 验证Ip
 this.initOrgAddValidator = function(){
		
		$('#addOrgForm').jqxValidator({
	        rules: [  
	                  { input: '#add-orgName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-orgName', message: '组织名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },

	               ]
		});
		
		// 新增Vlan池验证成功
		$('#addOrgForm').on('validationSuccess', function (event) {
			 var pool = Core.parseJSON($("#addOrgForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/org/create",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					initTreeOrg();
						$("#addOrgWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#addOrgWindow").jqxWindow('close');
	 			    }
	 			});
	     });
 };
 
 
 // 验证Ip
 this.initOrgEditValidator = function(){
		$('#editOrgForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-orgName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-orgName', message: '组织名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	               ]
		});
	    	
		// 编辑Ip验证成功
		$('#editOrgForm').on('validationSuccess', function (event) {
			 var pool = Core.parseJSON($("#editOrgForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/org/update",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					initTreeOrg();
						$("#editOrgWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editOrgWindow").jqxWindow('close');
	 			    }
	 			});
	     });
 };  */

	function initOrgresPageJs() {
		// 初始化服务实例-index页面model
		var model = new orgresModel();
		model.initOrgTree();
		model.initResTree();
		model.initOrgTempPopWindow();
		model.searchOrgContent();
		// 初始化组件验证规则
		model.initValidator();
		//model.attachContextMenu();
		return model;
	}

</script>

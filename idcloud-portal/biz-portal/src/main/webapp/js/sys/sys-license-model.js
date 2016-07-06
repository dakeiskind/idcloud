var licenseModel = function () {
	
	var me = this;
    this.items = ko.observableArray();
	
	
	 this.initInput = function(){
		 $("#licenseSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	     $("#licenseCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	 }
	 
	 this.searchLicenseInfo = function(){
		 Core.AjaxRequest({
				url : ws_url + "/rest/licenses/licenseMessage",
       	 	 	type:'post',
       	 		params: "",
       	 	 	async:true,
       	 	 	callback : function (data) {
       	 	 	me.items(data);
 				var sourcedatagrid ={
 					datatype: "json",
 					localdata: me.items
 			    };
 				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#licensedatagrid").jqxGrid({source: dataAdapter});
       	 	 	}
		 });
	 }
	 
	 this.initLicenseDatagrid = function(){
         $("#licensedatagrid").jqxGrid({
             width: "100%",
			  theme:currentTheme,
             columnsresize: true,
             pageable: true, 
             pagesize: 10, 
             autoheight: true,
             autowidth: false,
             sortable: true,
             selectionmode:"singlerow",
             localization: gridLocalizationObj,
             columns: [
                       
                 { text: '已使用主机数', datafield: 'hostCount',cellsalign: 'right', align: 'center',width:300},
                 { text: '可使用主机数', datafield: 'maxCount',cellsalign: 'right', align: 'center',width:300},
                 { text: '到期时间', datafield: 'overDate',cellsalign: 'center', align: 'center'},
                
               
             ],
             showtoolbar: true,
             // 设置toolbar操作按钮
             rendertoolbar: function (toolbar) {
                 var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                 var registerBtn = $("<div><a id='registerBtn' onclick ='register()'>&nbsp;&nbsp;<i class='icons-blue icon-key'></i>License 注册&nbsp;&nbsp;</a></div>");
                 toolbar.append(container);
                 container.append(registerBtn);
                 $("#registerBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
             }
         });
   };
   
   this.initPopWindow = function(){
	   $("#registerLicenseWindow").jqxWindow({
		   width: 610, 
           height:150,
           resizable: false,  
           isModal: true, 
           autoOpen: false, 
           cancelButton: $("#licenseCancel"), 
           theme: currentTheme,
           modalOpacity: 0.3,
           initContent:function(){
           	 // 初始化新增用户页面组件
   	        $("#register-license").jqxInput({placeHolder: "", height: 25, width: 400, minLength: 1,theme:currentTheme});
           }
	   });
   }
}

function register(){
	$("#registerLicenseWindow").jqxWindow('open');
}

function license_submit(){
	 var licenseNo = document.getElementById("register-license").value;
		 if(""==licenseNo){
			Core.confirm({
				title:"提示",
			message:"请输入有效License序列号",
			});
		 }
		 Core.AjaxRequest({
  		 url : ws_url + "/rest/licenses/register",
  		 type:'post',
  		 params: {licenseNo:licenseNo},
	 	 	 async:true,
		 callback : function (data) {
			 document.location.reload();
 		 }
  	 });
}
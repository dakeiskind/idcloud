var resBizModifyVmAddModel = function (vm) {
 	    
    // 初始化弹出window
    this.initPopWindow = function(){
		$("#modifyVmWindow").jqxWindow({
            width: 300, 
            height:150,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#modifyVmCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#createDate").jqxDateTimeInput({width: '160px', culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss', height: 22,allowNullDate: false,theme:"metro"});
            	$("#modifyVmSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#modifyVmCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
    };

};

	
 // 弹出修改虚拟机window
 function popModifyVmWindow(){
	var rowindexex = $('#bizVmdatagrid').jqxGrid('getselectedrowindexes');
	resBizVmSidList = new Array();

	var ok =  $("#modifyVm").jqxButton("disabled");
	if(rowindexex.length > 0 && !ok){
	    for (var i = 0; i < rowindexex.length; i++){
	    	var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindexex[i]);
	    	data.createdDt = "";
	    	data.updatedDt = "";
	    	data.dredgeDate = "";
	    	resBizVmSidList.push(data);
	    }
	    
	    var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#modifyVmWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-150)/2 } });
    	$("#modifyVmWindow").jqxWindow('open');
	   
  	}	    
};	

 // 提交修改虚机信息
function modifyVmSave(){
	Core.AjaxRequest({
		url : ws_url + "/rest/resbiz/vm/modify",
		type:'post',
		params :{
			orgSid : "",
			account : "",
			createDate : $("#createDate").val(),
			resBizVmList : resBizVmSidList
		},
		callback : function (data) {
			// 清除列表选择项
			$('#bizVmdatagrid').jqxGrid('clearselection');
			// 刷新列表
			searchBizVm();
	    },
	    failure:function(data){
	    	// 清除列表选择项
			$('#bizVmdatagrid').jqxGrid('clearselection');
			// 刷新列表
			searchBizVm();
	    }
	});
}
	
	
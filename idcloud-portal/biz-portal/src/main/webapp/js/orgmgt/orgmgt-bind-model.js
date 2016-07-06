var orgBindModel = function(orgRes) {

	/**************************************************** 服务目录********************************************/

	// 新增
	this.orgAddItem = function() {
	    var selectData=[];

		var ok =  $('#orgAddBtn').jqxButton('disabled');
		if(!ok) {
			
			rowindexs = $("#jqxgridOrg").jqxGrid('getselectedrowindexes');
		    $.each(rowindexs, function(i, n) {
		    	var data = $("#jqxgridOrg").jqxGrid('getrowdata', n);
		    	selectData[i] = data;
		    });
		    
			var param = {};
			param.selectData = selectData;
//			alert(JSON.stringify(param.selectData));
			var item = $('#jqxTreeHost').jqxTree('getSelectedItem');
//			alert(JSON.stringify(item.topologyValue));

		}	
	};
	
	/** 提交新增用户的信息 */
    this.addOrgSubmit = function(){
    	// 判断是否通过了验证
    	$('#addOrgForm').jqxValidator('validate');
    };
    
	
	   /** 提交编辑服务目录信息 */
    this.editOrgSubmit = function(){
    	// 判断是否通过了验证
    	$('#editOrgForm').jqxValidator('validate');
    };
    
	
	 
};

var reportModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.searchObj = {
		
	};
	
	 // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	$("#search-os-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	$("#search-export-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        $("#search-os-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
	
	
	  // 查询告警规则列表
    this.searchInfo = function(){
    	var data = [
    	            {name:"Windows",value:"23"},
    	            {name:"Linux",value:"40"},
    	            {name:"AIX",value:"10"},
    	       ];
    	 
    	var sourcedatagrid ={
	              datatype: "json",
	              localdata: data
	    };
	    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	    $("#osReportdatagrid").jqxGrid({source: dataAdapter});
    };
    
    // 初始化用户datagrid的数据源
    this.initdatagrid = function(){
        $("#osReportdatagrid").jqxGrid({
        	 width: "60%",
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
                 { text: '操作系统名称', datafield: 'name'},
                 { text: '使用数量', datafield: 'value'}
                
             ],
             showtoolbar:false
        });
              
    };

};

// 导出
function exportTest(){
	  var hostNameLike =  $("#search-os-name").val();
	  var staObj = {
		 hostNameLike: hostNameLike,
	  };
	  
	  var params = JSON.stringify(staObj);
	  window.location = ws_url + "/rest/reports/exportTest/" + params; 
}

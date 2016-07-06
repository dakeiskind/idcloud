var remarkPniIpModel = function(){
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	 var localData = [
    	     {label:"预占",value:"00"},
    	     {label:"使用",value:"02"},
    	 ];
    	 var source ={
             datatype: "json",
             datafields: [
                 { name: 'label' },
                 { name: 'value' }
             ],
             localData:localData
         };
         var dataAdapter = new $.jqx.dataAdapter(source);
       
         $("#remark-pni-status").jqxDropDownList({
             selectedIndex: 0, source: dataAdapter, autoDropDownHeight: true, displayMember: "label", valueMember: "value",theme:currentTheme, width: 180, height: 23
         });
    	// 初始化查询组件
    	
    	$("#remark-pni-description").jqxInput({placeHolder: "", height: 40, width: 180, minLength: 1,maxLength: 512,theme:currentTheme});
        
    	$("#remarkPniIpSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        $("#remarkPniIpCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
  
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#remarkPniIpWindow").jqxWindow({
		      width: 300, 
		      height:150,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#remarkPniIpCancel"), 
		      modalOpacity: 0.3
		  });
    };
    
};

// 弹出预占window
function popPniRemarkIpInNetwork(){
	  // 判断选中的IP地址是否全是未使用的
	  var isok = true;
	  var resIpSids = "";
	  var rowindex = $('#customnNetworkIpConfigMgtdatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
	  		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#customnNetworkIpConfigMgtdatagrid').jqxGrid('getrowdata', rowindex[i]);
	   			if(data.usageStatus != '01'){
	   				isok = false;
	   			    Core.alert({
		   				title:"提示",
		   			 	message:"您选中的IP中有已使用的，不能预占！",
		   			    callback:function(){
		   			    	return;
		   			    }
	   			    });
	   			}
	   			
	   			if(i == rowindex.length-1){
	   				resIpSids += data.resSid;
				}else{
					resIpSids += data.resSid + ",";
				}
	   		}
	  }
	  
	  if(isok){
		  
		  $("#remark-pni-resSids").val(resIpSids);
		  
		  // 设置弹出框位置
		  var windowW = $(window).width(); 
	      var windowH = $(window).height(); 
	      $("#remarkPniIpWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-150)/2 } });
	      $("#remarkPniIpWindow").jqxWindow('open');
	  }
}

// 提交预占信息
function submitPniRemarkIpInfo(){
	
	var remark = Core.parseJSON($("#remarkPniIpForm").serializeJson());
	// 提交预占
	Core.AjaxRequest({
		url : ws_url + "/rest/ip/remark",
		type:"POST",
		params:remark,
		callback : function (data) {
			// 刷新ip列表
			var networkIp = new customNetworkViewIpGrid();  
			networkIp.searchObj.ipAddressLike = $("#search-pni-network-IP-address").val();
			networkIp.searchObj.ipType = $("#search-pni-network-IP-type").val()=="全部"?"":$("#search-pni-network-IP-type").val();
			networkIp.searchObj.usageStatus = $("#search-pni-network-IP-usage-status").val()=="全部"?"":$("#search-pni-network-IP-usage-status").val();
			networkIp.searchIpConfigInfo(resourcePoolSid);
			
			// 取消掉datagrid的选中状态
			$('#customnNetworkIpConfigMgtdatagrid').jqxGrid('clearselection');
			
			$("#remarkPniIpWindow").jqxWindow('close');
			
	    }
	});
}

  
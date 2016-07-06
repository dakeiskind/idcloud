// 监控主机 
var monitorHostModel = function () {
	    // 验证新增画面
		this.initValidator = function(){
	    
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	// 监控信息
	    	$("#monitorHostWindow").jqxWindow({
                width: 700, 
                height:400,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $(""), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	    	
                }
            });
	    };
 };
 
  
  
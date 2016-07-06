// 查看主机详情主机 
var detailHostModel = function () {
		// 给主机详情赋值
	    this.setdetailhostForm = function(data){
	    	$("#detail-hostName").html(data.hostName);
	    	$("#detail-fullName").html(data.fullName);
	    	$("#detail-hostType").html(data.hostType);
	    	$("#detail-clusterName").html(data.clusterName);
	    	$("#detail-rackNumber").html(data.rackNumber);
	    	$("#detail-cageEnclosure").html(data.cageEnclosure);
	    	$("#detail-uuid").html(data.uuid);
	    	$("#detail-vendor").html(data.vendor);
	    	$("#detail-dataCenter").html(data.dataCenter);
	    	
	    	$("#detail-hostIp").html(data.hostIp);
	    	$("#detail-memorySize").html(data.memorySize);
	    	$("#detail-cpuNumber").html(data.cpuNumber);
	    	$("#detail-cpuCores").html(data.cpuCores);
	    	$("#detail-cpuGhz").html(data.cpuGhz);
	    	$("#detail-cpuType").html(data.cpuType);
	    	
	    	$("#detail-virtualPlatformType").html(data.virtualPlatformType);
	    	$("#detail-hostOsType").html(data.hostOsType);
	
	    };
	    // 初始化window
	    this.initPopWindow = function(){
	    	// 主机详情
	    	$("#detailHostWindow").jqxWindow({
                width: 800, 
                height:338,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#detailhostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	    	
        	    	$("#detailhostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    };	
 };
 
  
  
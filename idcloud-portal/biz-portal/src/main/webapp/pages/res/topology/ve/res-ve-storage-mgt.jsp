<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='veTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>主机</li>  
        <li>网络</li>
        <li>存储</li>
        <li>虚拟机</li>
        <li>告警</li>
    </ul>
    <div id="veTabsContent0">  
        <%@ include file="res-ve-storage-summary.jsp"%>
    </div>
    <div id="veTabsContent1">
    
    </div>
    <div id="veTabsContent2">
    
    </div>
    <div id="veTabsContent3">
    
    </div>
    <div id="veTabsContent4">
    </div>
    <div id="veTabsContent5">
    </div>
</div>
<script type="text/javascript">
	 function initVETabs(id,type){
		  // 初始化数据中心tabs选项卡
		  $('#veTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  
		  initHostVESummary();
		  
		  $('#veTabs').on('selected', function (event) {
			  	   var pageIndex = event.args.item;
			  	 if(pageIndex == 1){
	                	$("#veTabsContent1").load(ctx+"/pages/res/host/res-host-index.jsp",function(){	
	                		initHostConfigModel();
						});
	                }else if(pageIndex == 2){
	                	$("#veTabsContent2").load(ctx+"/pages/res/network/res-network-index.jsp",function(){	
	                		// initNetworkJS();
						});
	                }else if(pageIndex == 3){
	                	$("#veTabsContent3").load(ctx+"/pages/res/storage/res-storage-index.jsp",function(){	
	                		// initNetworkJS();
						});
	                }else if(pageIndex == 4){
	                	$("#veTabsContent4").load(ctx+"/pages/res/vm/res-vm-mgt.jsp",function(){	
	                		initVMTabs();
						});
	                }else if(pageIndex == 5){
	                	$("#veTabsContent5").load(ctx+"/pages/res/alarm/alarm-console-index.jsp",function(){	
	                		initAlarmConsolePageJs();
						});
	                }
          });
	 }
	
</script>


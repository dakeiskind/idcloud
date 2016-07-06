<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='hostTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>监控</li>
        <li>虚拟机</li>
        <li>存储</li>
        <li>告警</li>
    </ul>
    <div>  
        <%@ include file="res-host-summary.jsp"%>
    </div>
    <div id="hostTabsContent1">
    	<%@ include file="../../monitor/res-monitor-host.jsp"%>
    </div>
    <div id="hostTabsContent2">
    </div>
    <div id="hostTabsContent3">
    </div>
    <div id="hostTabsContent4">
    </div>
</div>
<script type="text/javascript">
	 function initHostTabs(id,type){
		  // 初始化数据中心tabs选项卡
		  $('#hostTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});

		  // 设置选中的sid和类型
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要画面js
		  initHostSummary();
		  
		  $('#hostTabs').on('selected', function (event) {
			  	   var pageIndex = event.args.item;  
			  	   
			  	    if(pageIndex == 2){
	                	// 虚拟机
	                	$("#hostTabsContent2").load(ctx+"/pages/res/vm/res-vm-mgt.jsp",function(){	
	                		initVMTabs();
						});
	                }else  if(pageIndex == 3){
		            	// 主机存储
	                	$("#hostTabsContent3").load(ctx+"/pages/res/storage/res-host-storage.jsp",function(){	
	               			
	                	});
	                }else if(pageIndex == 4){
	                	//告警
	                	$("#hostTabsContent4").load(ctx+"/pages/res/alarm/alarm-console-index.jsp",function(){	
	                		initAlarmConsolePageJs();
						});
	                }else if(pageIndex == 5){
	                	
	                }
          });
	 }
	
</script>


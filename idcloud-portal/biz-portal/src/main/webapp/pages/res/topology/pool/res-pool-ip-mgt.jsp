<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='poolTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>IP管理</li>  
    </ul>
    <div>  
        <%@ include file="res-pool-ip-summary.jsp"%>
    </div>
    <div id="poolTabsContent1">
    	
    </div>
</div>
<script type="text/javascript">
	 function initpoolTabs(id,type){
		  // 初始化数据中心tabs选项卡
		  $('#poolTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置数据中心
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化资源池概要内容
		  initRespoolSummary();
		  
		  $('#poolTabs').on('selected', function (event) {
			  	   var pageIndex = event.args.item;  
			  	   
	               if(pageIndex == 1){
	                	$("#poolTabsContent1").load(ctx+"/pages/res/network/res-network-ip.jsp",function(){	
	                		//initHostConfigModel("pool");
						});
	                }else if(pageIndex == 2){
// 	                	$("#dcTabsContent2").load(ctx+"/pages/res/network/res-network-index.jsp",function(){	
// 	                		// initNetworkJS();
// 						});
	                }
          });
	 }
	
</script>


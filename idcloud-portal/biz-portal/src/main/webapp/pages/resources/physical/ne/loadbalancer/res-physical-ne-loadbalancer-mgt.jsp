<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='loadbalancerTabs' style="border:0px;">
    <ul>
    	<!-- <li style="margin-left: 30px;">概要</li> -->
        <li style="margin-left: 30px;">负载均衡器</li>
    </ul>
   <%--  <div id="loadbalancerTabsContent0">  
         <%@ include file="/pages/resources/topology/ve/res-ve-virtual-summary.jsp"%>
    </div> --%>
    <div id="loadbalancerTabsContent0">
    </div>
</div>

<script type="text/javascript">
	 function initLoadbalancerTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#loadbalancerTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		//  initHostVESummary();
		  $("#loadbalancerTabsContent0").load(ctx+"/pages/resources/physical/ne/loadbalancer/res-ne-loadbalancer-datagrid.jsp",
		           function(){	
		  			loadbalancerDatagridModel();
				});
		  
	 }
/* 	 $('#loadbalancerTabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;
	  	 if(pageIndex == 0){
	  		$("#loadbalancerTabsContent0").load(ctx+"/pages/resources/physical/ne/loadbalancer/res-ne-loadbalancer-datagrid.jsp",
	           function(){	
	  			loadbalancerDatagridModel();
			});
         }
     }); */
	
</script>
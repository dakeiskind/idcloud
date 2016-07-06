<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='networkDeviceTabs' style="border:0px;">
    <ul>
    	<!-- <li style="margin-left: 30px;">概要</li> -->
        <li style="margin-left: 30px;">交换机</li>
        <li>防火墙</li>
        <li>负载均衡器</li>
    </ul>
   <%--  <div id="computeDeviceTabsContent0">  
         <%@ include file="/pages/resources/topology/ve/res-ve-virtual-summary.jsp"%>
    </div> --%>
    <div id="networkDeviceTabsContent0">
    
    </div>
    <div id="networkDeviceTabsContent1">
    
    </div>
    <div id="networkDeviceTabsContent2">
    
    </div>

</div>
 <%-- <%@ include file="/pages/resources/physical/ne/switch/res-ne-switch-info.jsp"%> --%>
<script type="text/javascript">
	 function initNetworkTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#networkDeviceTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  // 初始化概要页面
		  
		//  initHostVESummary();
		  $("#networkDeviceTabsContent0").load(ctx+"/pages/resources/physical/ne/switch/res-ne-switch-datagrid.jsp"
				  //,
	              	//function(){	
	              	//	switchDatagridModel();
				//} 
		  );
		  
	 }
	 $('#networkDeviceTabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;
	  	 if(pageIndex == 1){
              	$("#networkDeviceTabsContent1").load(ctx+"/pages/resources/physical/ne/firewall/res-ne-firewall-datagrid.jsp",
              	function(){	
              		firewallDatagridModel();
			});
            }else if(pageIndex == 2){
              	$("#networkDeviceTabsContent2").load(ctx+"/pages/resources/physical/ne/loadbalancer/res-ne-loadbalancer-datagrid.jsp",
                function(){	
              		loadbalancerDatagridModel();
        	});
          }
     });
	
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='dcTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
       <!--  <li>机房</li>
        <li>机柜</li>
        <li>机框</li>  
		<li>服务器</li>
		<li>交换机</li>
		<li>负载均衡器</li>
		<li>防火墙</li>
		<li>存储</li> -->
    </ul>
    <div id="dcTabsContent0">  
        <%@ include file="res-dc-physical-summary.jsp"%>
    </div>
    <!-- <div id="dcTabsContent1">
    	
    </div>
    <div id="dcTabsContent2">
    	
    </div>
    <div id="dcTabsContent3">
    	
    </div>
    <div id="dcTabsContent4">
    	
    </div>
    <div id="dcTabsContent5">
    	
    </div>
    <div id="dcTabsContent6">
    	
    </div>
    <div id="dcTabsContent7">
    	
    </div>
    <div id="dcTabsContent8">
    	
    </div> -->
</div>
<script type="text/javascript">
	 function initDCTabs(id,type){
		  // 初始化数据中心tabs选项卡
		  $('#dcTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  // 初始化概要页面
		  initHostDCSummary();
	 }
/* 	 $('#dcTabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;
	  	 if(pageIndex == 1){
        	  $("#dcTabsContent1").load(ctx+"/pages/resources/physical/pl/labHouse/res-pl-labhost-datagrid.jsp",function(){
        		  labHouseDatagridModel();
        	  });
          }else if(pageIndex == 2){
        	  $("#dcTabsContent2").load(ctx+"/pages/resources/physical/pl/cabinet/res-pl-cabinet-datagrid.jsp",function(){
        		  cabinetDatagridModel();
        	  });	
          }else if(pageIndex == 3){
        	  $("#dcTabsContent3").load(ctx+"/pages/resources/physical/ce/machineFrame/res-ce-mf-datagrid.jsp",function(){
        		  machineFrameDatagridModel();
        	  });	
          }else if(pageIndex == 4){
        	  $("#dcTabsContent4").load(ctx+"/pages/resources/physical/ce/server/res-ce-server-datagrid.jsp",function(){
        		  serverDatagridModel();
        	  });	
          }else if(pageIndex == 5){
        	  $("#dcTabsContent5").load(ctx+"/pages/resources/physical/ne/switch/res-ne-switch-datagrid.jsp",function(){
        		  switchDatagridModel();
        	  });
          }else if(pageIndex == 6){
        	  $("#dcTabsContent6").load(ctx+"/pages/resources/physical/ne/loadbalancer/res-ne-loadbalancer-datagrid.jsp",function(){
        		  loadbalancerDatagridModel();
        	  });
          }else if(pageIndex == 7){
        	  $("#dcTabsContent7").load(ctx+"/pages/resources/physical/ne/firewall/res-ne-firewall-datagrid.jsp",function(){
        		  firewallDatagridModel();
        	  });
          }else if(pageIndex == 8){
        	  $("#dcTabsContent8").load(ctx+"/pages/resources/physical/se/san/res-se-san-datagrid.jsp",function(){
        		  sanDatagridModel();
        	  });
          }
  }); */
	
</script>


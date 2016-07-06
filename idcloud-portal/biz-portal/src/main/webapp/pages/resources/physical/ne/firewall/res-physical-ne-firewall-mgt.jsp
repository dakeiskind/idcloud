<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='firewallTabs' style="border:0px;">
    <ul>
    	<!-- <li style="margin-left: 30px;">概要</li> -->
        <li style="margin-left: 30px;">防火墙</li>
    </ul>
    <%-- <div id="firewallTabsContent0">  
         <%@ include file="/pages/resources/topology/ve/res-ve-virtual-summary.jsp"%>
    </div> --%>
    <div id="firewallTabsContent0">
    </div>
</div>

<script type="text/javascript">
	 function initFirewallTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#firewallTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		 // initHostVESummary();
		  $("#firewallTabsContent0").load(ctx+"/pages/resources/physical/ne/firewall/res-ne-firewall-datagrid.jsp",
		           function(){	
		  			firewallDatagridModel();
				});
		  
	 }
/* 	 $('#firewallTabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;
	  	 if(pageIndex == 0){
	  		$("#firewallTabsContent0").load(ctx+"/pages/resources/physical/ne/firewall/res-ne-firewall-datagrid.jsp",
	           function(){	
	  			firewallDatagridModel();
			});
         }
     }); */
	
</script>
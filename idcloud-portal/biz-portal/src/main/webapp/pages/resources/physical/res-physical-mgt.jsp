<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<!-- Topology -->
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-computeDevice-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-server-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-firewall-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-loadbalancer-datafrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-switch-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-labhost-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-cabinet-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-san-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-rack-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-bladeframe-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-switch-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-loadbalancer-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-firewall-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-storage-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/regionDc/res-edit-zone-dc-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/regionDc/res-add-zone-dc-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-server-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-physicalMonitor-info-mode.js"></script>
		
		<!-- hightcharts -->
		<script src="${ctx}/scripts/highcharts/highcharts.js"></script>
		<script src="${ctx}/scripts/highcharts/highcharts-more.js"></script>
		<script src="${ctx}/scripts/highcharts/modules/exporting.js"></script>
		
		<style type="text/css">
			#containerPhysical{
				width:99.92%;
				height:99.8%;
				margin:0px;
			}
    </style>  
</head>	
<body>
         <div id="containerPhysical">
         <div id="mainSplitter" style="border:0px">
            <div class="splitter-panel">
                <div id='jqxPhysicalDevice'style="width:100%;height:100%;border:0px;overflow:hidden"></div>
            </div>
            <div class="listContent">
               
            </div>
         </div>
   </div>
   <%@ include file="../regionDc/res-add-zone-dc-index.jsp"%>
   <%@ include file="../regionDc/res-edit-zone-dc-index.jsp"%>
   <%@ include file="/pages/resources/physical/ne/switch/res-ne-switch-info.jsp"%>
   <%@ include file="/pages/resources/physical/ce/server/res-ce-server-info.jsp"%>
</body>
<script type="text/javascript">
         // 当前选中Tree的sid
         var resTopologySid;
         // 当前选中Tree的类型
         var resTopologyType;
         // 当前选中Tree的父级sid
         var parentTopologySid;
         
         var resTopologySidDC;
		 $(function(){
			    var containerHeight = window.parent.getContainerHeight();
				$('#mainSplitter').jqxSplitter({ width: "100%", height: "100%", theme:currentTheme, panels: [{ size: 250 ,max:300 ,min:100}] });
				
				setPhysicalTreeValue();
				// 物理设备选择时
	             $('#jqxPhysicalDevice').on('select', function (event) {
	                var args = event.args;
	                var item = $('#jqxPhysicalDevice').jqxTree('getItem', args.element); 
	                
	                var attr = item.value.split(","); 
	                if(attr[1] == "R"){
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/region/res-region-mgt.jsp",function(){
	                		initRegionTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "DC"){
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/topology/dc/res-dc-physical-mgt.jsp",function(){
	                		initDCTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "CE"){
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/ce/res-physical-ce-mgt.jsp",function(){
	                		initComputeDeviceTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "F"){
                		Core.AjaxRequest({
                			url : ws_url + "/rest/topology/"+attr[0],
                			type:"get",
                			async:false,
                			callback : function (data) {
                				parentTopologySid = data.parentTopologySid;
                		    }
                	  });
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/ce/machineFrame/res-physical-computeDevice-mf-mgt.jsp",function(){
	                		initMachineFrameTabs(parentTopologySid,attr[1]);
	                	});
	                }else if(attr[1] == "S"){
                		Core.AjaxRequest({
                			url : ws_url + "/rest/topology/"+attr[0],
                			type:"get",
                			async:false,
                			callback : function (data) {
                				parentTopologySid = data.parentTopologySid;
                		    }
                	  });
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/ce/server/res-physical-computeDevice-server-mgt.jsp",function(){
	                		initServerTabs(parentTopologySid,attr[1]);
	                	});
	                }else if(attr[1] == "NE"){
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/ne/res-physical-ne-mgt.jsp",function(){
	                		initNetworkTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "SW"){
	                	Core.AjaxRequest({
                			url : ws_url + "/rest/topology/"+attr[0],
                			type:"get",
                			async:false,
                			callback : function (data) {
                				parentTopologySid = data.parentTopologySid;
                		    }
                	  });
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/ne/switch/res-physical-ne-switch-mgt.jsp",function(){
	                		initSwitchTabs(parentTopologySid,attr[1]);
	                	});
	                }else if(attr[1] == "FW"){
                		Core.AjaxRequest({
                			url : ws_url + "/rest/topology/"+attr[0],
                			type:"get",
                			async:false,
                			callback : function (data) {
                				parentTopologySid = data.parentTopologySid;
                		    }
                	  });
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/ne/firewall/res-physical-ne-firewall-mgt.jsp",function(){
	                		initFirewallTabs(parentTopologySid,attr[1]);
	                	});
	                }else if(attr[1] == "LB"){
                		Core.AjaxRequest({
                			url : ws_url + "/rest/topology/"+attr[0],
                			type:"get",
                			async:false,
                			callback : function (data) {
                				parentTopologySid = data.parentTopologySid;
                		    }
                	  });
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/ne/loadbalancer/res-physical-ne-loadbalancer-mgt.jsp",function(){
	                		initLoadbalancerTabs(parentTopologySid,attr[1]);
	                	});
	                }else if(attr[1] == "PL"){
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/pl/res-physical-pl-mgt.jsp",function(){
	                		initPhyLocationTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "H"){
	                		Core.AjaxRequest({
	                			url : ws_url + "/rest/topology/"+attr[0],
	                			type:"get",
	                			async:false,
	                			callback : function (data) {
	                				parentTopologySid = data.parentTopologySid;
	                		    }
	                	  });
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/pl/labHouse/res-physical-pl-labhost-mgt.jsp",function(){
	                		initLabHostTabs(parentTopologySid,attr[1]);
	                	});
	                }else if(attr[1] == "C"){
                		Core.AjaxRequest({
                			url : ws_url + "/rest/topology/"+attr[0],
                			type:"get",
                			async:false,
                			callback : function (data) {
                				parentTopologySid = data.parentTopologySid;
                		    }
                	  });
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/pl/cabinet/res-physical-pl-cabinet-mgt.jsp",function(){
	                		initCabinetTabs(parentTopologySid,attr[1]);
	                	});
	                }else if(attr[1] == "RC"){
	                	Core.AjaxRequest({
                			url : ws_url + "/rest/topology/"+attr[0],
                			type:"get",
                			async:false,
                			callback : function (data) {
                				parentTopologySid = data.parentTopologySid;
                		    }
                	  });
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/pl/rack/res-physical-pl-rack-mgt.jsp",function(){
	                		initRackTabs(parentTopologySid,attr[1]);
	                	});
	                }else if(attr[1] == "SE"){
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/se/res-physical-se-mgt.jsp",function(){
	                		initStorageTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "SAN"){
                		Core.AjaxRequest({
                			url : ws_url + "/rest/topology/"+attr[0],
                			type:"get",
                			async:false,
                			callback : function (data) {
                				parentTopologySid = data.parentTopologySid;
                		    }
                	  });
	                	$("#containerPhysical .listContent").load(ctx+"/pages/resources/physical/se/san/res-physical-se-san-mgt.jsp",function(){
	                		initSANTabs(parentTopologySid,attr[1]);
	                	});
	                }
	            }); 				
			});
		 
		 // 给左边的资源拓扑tree赋值 --- 主机
		 function setPhysicalTreeValue(){
			 Core.AjaxRequest({
		 			url : ws_url + "/rest/topology/physical/tree",
		 			type:'post',
		 			params:{},
		 			callback : function (data) {
		 				initPhysicalTopologyTree(data);
		 				
		 			}
		 	    });
		 }
		 // 初始化主机虚拟化的tree
		 function initPhysicalTopologyTree(data){
             var source ={
                 datatype: "json",
                 datafields: [
                     { name: 'resTopologySid' },
                     { name: 'parentTopologySid' },
                     { name: 'resTopologyName' },
                     { name: 'topologyIcon' },
                     { name: 'topologyValue'}
                 ],
                 id: 'topologySid',
                 localdata: data
             };
             var dataAdapter = new $.jqx.dataAdapter(source);
             dataAdapter.dataBind(); 
             var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'topologyValue', map: 'value'},{ name: 'topologyIcon', map: 'icon'}]);		 
             $('#jqxPhysicalDevice').jqxTree({ source: records, width: '100%',height:'100%',allowDrag:false});
             $('#jqxPhysicalDevice').jqxTree('selectItem', $("#jqxPhysicalDevice").find('li')[2]);
             $('#jqxPhysicalDevice').jqxTree('expandItem', $("#jqxPhysicalDevice").find('li')[2]);
	      }
</script>
</html>
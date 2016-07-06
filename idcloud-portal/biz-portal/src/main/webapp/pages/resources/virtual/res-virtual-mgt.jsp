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
		<script type="text/javascript" src="${ctx}/js/resources/regionDc/res-add-zone-dc-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/regionDc/res-edit-zone-dc-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/regionDc/res-add-host-to-cluster-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/regionDc/res-remove-host-out-cluster-model.js"></script>
		<!-- virtual -->
		<script type="text/javascript" src="${ctx}/js/resources/virtual/res-ve-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/virtual/res-ve-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/virtual/res-ve-edit-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/virtual/res-ve-advance-config-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/virtual/res-vc-datagrid-model.js"></script>
		<!-- host -->
		<script type="text/javascript" src="${ctx}/js/resources/host/res-host-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/host/res-pve-host-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/host/res-other-host-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/host/host-x86-add-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/host/host-x86-edit-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/host/host-x86-edit-init-model.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/resources/host/host-power-add-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/host/host-power-edit-init-model.js"></script>

		<script type="text/javascript" src="${ctx}/js/resources/host/host-storage-model.js"></script>
        <script type="text/javascript" src="${ctx}/js/resources/host/res-pve-host-relation-model.js"></script>
        <!-- vios -->
		<script type="text/javascript" src="${ctx}/js/resources/vios/res-vios-host-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vios/res-add-vios-host-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vios/res-edit-vios-host-model.js"></script>
		<!-- vm -->
		<script type="text/javascript" src="${ctx}/js/resources/vm/res-vm-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/res-pve-vm-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-detail-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/res-vm-monitor-model.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-reconfig-model.js"></script> --%>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-migrate-target-host.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-migrate-target-storage.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-config-ip.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-migrate-select.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/res-add-pve-vm-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/res-edit-pve-vm-model.js"></script>
		<!-- storage -->
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-storage-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-pve-storage-datagrid-model.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-storage-host-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-storage-set-type-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-host-storage-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-host-storage-edit-model.js"></script>
	 	<script type="text/javascript" src="${ctx}/js/resources/storage/res-storage-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-storage-edit-model.js"></script> 
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-storage-extend-model.js"></script> 
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-storageVolume-datagrid-model.js"></script> 
		
		<!-- alarm -->
		<script type="text/javascript" src="${ctx}/js/resources/alarm/alarm-console-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/alarm/alarm-info-confirm-model.js"></script>
		
		<!-- 存储类别 -->
		<script type="text/javascript" src="${ctx}/js/resources/rsc/res-rsc-index-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/rsc/res-rsc-storage-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/rsc/res-add-storage-to-rsc-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/rsc/res-remove-storage-to-rsc-model.js"></script>
		
		<!-- hightcharts -->
		<script src="${ctx}/scripts/highcharts/highcharts.js"></script>
		<script src="${ctx}/scripts/highcharts/highcharts-more.js"></script>
		<script src="${ctx}/scripts/highcharts/modules/exporting.js"></script>
		
		<style type="text/css">
			#containerVirtual{
				width:99.92%;
				height:99.8%;
				margin:0px;
			}
			
			.listContent{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
			}
			.listContentStorage{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
				display:none;
				
			}
			
			#jqxNavigationBar{
				widtt:100%;
				height:100%;
			}
			
				* {
		margin:0;
		padding:0;
	}
	body {
		font:14px Verdana, Arial, Geneva, sans-serif;
		color:#404040;
		background:#f9f9f9;
	}
	img {
		border-style:none;
	}
	.main{
		width:300px;
		height:40px;
		position:absolute;
		left:50%;
		top:50%;
		margin-left:-150px;
		margin-top:-30px;
	}
 	.box{
		position:relative;
		float:left;
	} 
	input.uploadFile{
		position:absolute;
		right:0px;
		top:0px;
		opacity:0;
		filter:alpha(opacity=0);
		cursor:pointer;
		width:276px;
		height:36px;
		overflow: hidden;
	}
	input.textbox{
		float:left;
		padding:5px;
		color:#999;
		height:18px;
		line-height:24px;
		border:1px #ccc solid;
		width:200px;
		margin-right:4px;
	}
	a.link{
		float:left;
		display:inline-block;
		padding:4px 16px;
		color:#5E5252;
		font:14px "Microsoft YaHei", Verdana, Geneva, sans-serif;
		cursor:pointer;
		background-color:#E8E7E7;
		line-height:22px;
		text-decoration:none;
		height:22.5px;
	}
    </style>  
</head>	
	
 <body>
   <div id="containerVirtual">
   		<div id="mainSplitter" style="border:0px">
            <div class="splitter-panel" style="height:100%;">
            	<div id="jqxNavigationBar" style="border:0px;">
		            <div>
		                <div style='margin-top: 2px;border:0px;'>
		                    <div style='float: left;'></div>
		                    <div style='margin-left: 4px; float: left;'>主机</div>
		                </div>
		            </div>
		            <div  style="border:0px;">
			            <div class="virtualDiv" style="width:100%;height:100%;overflow:hidden">
			            		<div id='jqxTreeVirtual'style="width:100%;height:100%;"></div>
			            </div>
		            </div>
		            <div>
		                <div style='margin-top: 2px;'>
		                    <div style='float: left;'></div>
		                    <div style='margin-left: 4px; float: left;'>存储</div>
		                </div>
		            </div>
		            <div style="border:0px;">
		            	<div class="virtualDiv" style="width:100%;height:100%;overflow:hidden">
			            		<div id='jqxTreeVirtualStorage'style="width:90%;height:100%;border:0px;overflow:hidden;"></div>
			            </div>
		            </div>
		        </div>
            	
            </div>
            <div >
                 <div id="listContent" class="listContent">
                 
                 </div>
                 <div id="listContentStorage" class="listContentStorage">
                 
                 </div>
            </div>
            
        </div>
   </div>
   
    <%@ include file="ve/res-ve-add-index.jsp"%>
    <%@ include file="ve/res-ve-edit-index.jsp"%>
    <%@ include file="ve/res-ve-advance-config-index.jsp"%>
    <%@ include file="../regionDc/res-add-zone-dc-index.jsp"%>
    <%@ include file="../regionDc/res-edit-zone-dc-index.jsp"%>
    <%@ include file="../rsc/res-add-storage-to-rsc-index.jsp"%>
    <%@ include file="../storage/res-storage-set-type-index.jsp"%>
    
    <%@ include file="../host/res-add-x86-host-index.jsp"%>
    <%@ include file="../host/res-edit-x86-host-index.jsp"%>
    <%@ include file="../host/res-add-power-host-index.jsp"%>
    <%@ include file="../host/res-edit-power-host-index.jsp"%>
   
    
    <%@ include file="../host/res-pve-host-relation-index.jsp"%>
    <%@ include file="../storage/res-host-storage-add-index.jsp"%>
    <%@ include file="../storage/res-host-storage-edit-index.jsp"%>
    <%@ include file="../storage/res-storage-add-index.jsp"%>
    <%@ include file="../storage/res-storage-edit-index.jsp"%>
    <%@ include file="../storage/res-storage-extend-index.jsp"%>
    <%--<%@ include file="../storage/res-storageVolume-datagrid-index.jsp"%>--%>
    
    <%@ include file="../vios/res-add-vios-host-index.jsp"%>
    <%@ include file="../vios/res-edit-vios-host-index.jsp"%>
	
	<%@ include file="../vm/res-add-pve-vm-index.jsp"%>
	<%@ include file="../vm/res-edit-pve-vm-index.jsp"%>
    
</body>
<script type="text/javascript">
         // 当前选中Tree的sid
         var resTopologySid;
         // 当前选中Tree的类型
         var resTopologyType;
         // 当前选中Tree的父级sid
         var parentTopologySid;
         
		 $(function(){
			    var containerHeight = window.parent.getContainerHeight();
				$('#mainSplitter').jqxSplitter({ width: "100%", height: "100%", theme:currentTheme, panels: [{ size: 250 ,max:300 ,min:100}] });
				
			    $(".virtualDiv").css("height",""+containerHeight-97+"px");
				
				// 初始化主机资源管理的Topology表
				setVirtualTreeValue();
				setStorageVirtualTreeValue();
				
				$("#jqxNavigationBar").jqxNavigationBar({ width:'100%', height:"100%", expandMode: 'single', theme:currentTheme,expandedIndexes: [0, 1]});
				
				// 当选择jqxNavigationBar时
				$('#jqxNavigationBar').on('collapsingItem', function (event) {
				      var collapsingItem = event.args.item;
				      if(1 == collapsingItem){
				    	  // 弹出主机
		 			      $("#listContentStorage").hide();
		 			      $("#listContentStorage").html("");
		 				  $("#listContent").show();
		 				  // 得到主机虚拟化选择的Tree数据
		 				  setHostVirtualTree();
				      }else if(0 == collapsingItem){
				    	  // 弹出存储
				    	  $("#listContent").html("");
				    	  $("#listContent").hide();
						  $("#listContentStorage").show();
					      // 得到主机虚拟化选择的Tree数据
					      setStorageVirtualTree();
				      }
				});
				
				// 主机虚拟化选择时
	            $('#jqxTreeVirtual').on('select', function (event) {
	                var args = event.args;
	                var item = $('#jqxTreeVirtual').jqxTree('getItem', args.element);
	                var attr = item.value.split(",");
//	                console.log(attr[0] + "=====" + attr[1]+"======"+ item.label);
	                if(attr[1] == "R"){
	                	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/region/res-region-mgt.jsp",function(){
	                		initRegionTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "DC"){
	                	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/dc/res-dc-virtual-mgt.jsp",function(){
	                		initDCTabs(attr[0],attr[1],"host");
	                	});
	                }else if(attr[1] == "VMware" || attr[1] == "Openstack"){
	                	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/ve/res-ve-virtual-mgt.jsp",function(){
	                		initVETabs(attr[0],attr[1],"host");
	                	});
	                }else if(attr[1] == "HMC"){
	                	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/ve/res-pve-virtual-mgt.jsp",function(){
	                		initPVETabs(attr[0],attr[1],"host");
	                	});
	                }else if(attr[1] == "IVM"){
	                	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/ve/res-pve-virtual-mgt.jsp",function(){
	                		initPVETabs(attr[0],attr[1],"host");
	                	});
	                }else if(attr[1] == "Other"){
	                	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/ve/res-other-virtual-mgt.jsp",function(){
	                		initOtherTabs(attr[0],attr[1],"host");
	                	});
	                }else if(attr[1] == "VC"){
	                	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/cluster/res-cluster-virtual-mgt.jsp",function(){
	                		initClusterTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "HOST"){
	                	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/host/res-host-virtual-mgt.jsp",function(){
	                		initHostTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "PHOST"){
	                	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/host/res-pve-host-virtual-mgt.jsp",function(){
	                		initPveHostTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "OHOST"){
	                	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/host/res-other-host-virtual-mgt.jsp",function(){
	                		initOtherHostTabs(attr[0],attr[1]);
	                	});
	                }
	            });
				
				// 存储虚拟化选择时
	            $('#jqxTreeVirtualStorage').on('select', function (event) {
	                var args = event.args;
	                var item = $('#jqxTreeVirtualStorage').jqxTree('getItem', args.element);  
	                var attr = item.value.split(","); 
	                if(attr[1] == "R"){
	                	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/region/res-region-mgt.jsp",function(){
	                		initRegionTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "DC"){
	                	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/dc/res-dc-virtual-mgt.jsp",function(){
	                		initDCTabs(attr[0],attr[1],"storage");
	                	});
	                }else if(attr[1] == "HMC"){
	                	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/ve/res-pve-virtual-mgt.jsp",function(){
	                		initPVETabs(attr[0],attr[1],"storage");
	                	});
	                }else if(attr[1] == "IVM"){
	                	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/ve/res-pve-virtual-mgt.jsp",function(){
	                		initPVETabs(attr[0],attr[1],"storage");
	                	});
	                }else if(attr[1] == "VMware"){
	                	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/ve/res-ve-storage-virtual-mgt.jsp",function(){
	                		initVETabs(attr[0],attr[1],"storage");
	                	});
	                }else if(attr[1] == "RSC"){
	                	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/rsc/res-rsc-virtual-mgt.jsp",function(){
	                		initRSCTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "STORAGE"){
	                	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/storage/res-storage-virtual-mgt.jsp",function(){
	                		initStorageTabs(attr[0],attr[1]);
	                	});
	                }
	            });
				
		 });
		 
		 // 给左边的资源拓扑tree赋值 --- 主机
		 function setVirtualTreeValue(){
			 Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys/virtual/tree",
		 			type:'post',
		 			params:{},
		 			callback : function (data) {
		 				initVirtualTopologyTree(data);
		 			}
		 	    });
		 }
		 
		 // 刷新左边资源拓扑tree赋值 --- 主机
		 function setVirtualRefreshTreeValue(){
			 Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys/virtual/tree",
		 			type:'post',
		 			params:{},
		 			callback : function (data) {
		 				initVirtualRefreshTopologyTree(data);
		 			}
		 	    });
		 }
		 
		 
		 
		 // 给左边的资源拓扑tree赋值 --- 存储
		 function setStorageVirtualTreeValue(){
			 Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys/virtual/storage/tree",
		 			type:'post',
		 			params:{},
		 			callback : function (data) {
		 				initStorageVirtualTopologyTree(data);
		 			}
		 	    });
		 }
		 
		// 给左边的资源拓扑tree赋值 --- 存储
		 function setStorageRefreshVirtualTreeValue(){
			 Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys/virtual/storage/tree",
		 			type:'post',
		 			params:{},
		 			callback : function (data) {
		 				initStorageRefreshVirtualTopologyTree(data);
		 			}
		 	    });
		 }
		 
		 // 初始化主机虚拟化的tree
		 function initVirtualTopologyTree(data){
             var source ={
                 datatype: "json",
                 datafields: [
                     { name: 'resTopologySid' },
                     { name: 'parentTopologySid' },
                     { name: 'resTopologyName' },
                     { name: 'topologyIcon' },
                     { name: 'topologyValue' }
                 ],
                 id: 'topologySid',
                 localdata: data
             };
             var dataAdapter = new $.jqx.dataAdapter(source);
             dataAdapter.dataBind(); 
             var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'topologyValue', map: 'value'},{ name: 'topologyIcon', map: 'icon'}]);
             $('#jqxTreeVirtual').jqxTree({ source: records, width: '100%',height:'100%',allowDrag:false});
             $('#jqxTreeVirtual').jqxTree('selectItem', $("#jqxTreeVirtual").find('li')[1]);
             $('#jqxTreeVirtual').jqxTree('expandItem', $("#jqxTreeVirtual").find('li')[1]);
	      }
		 
		// 初始化主机虚拟化的tree --- refresh
		 function initVirtualRefreshTopologyTree(data){
			 var source ={
	                 datatype: "json",
	                 datafields: [
	                     { name: 'resTopologySid' },
	                     { name: 'parentTopologySid' },
	                     { name: 'resTopologyName' },
	                     { name: 'topologyIcon' },
	                     { name: 'topologyValue' }
	                 ],
	                 id: 'topologySid',
	                 localdata: data
	             };
	             var dataAdapter = new $.jqx.dataAdapter(source);
	             dataAdapter.dataBind(); 
	             var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'topologyValue', map: 'value'},{ name: 'topologyIcon', map: 'icon'}]);
             $('#jqxTreeVirtual').jqxTree({ source: records, width: '100%',height:'100%',allowDrag:true});
             $("#jqxTreeVirtual").jqxTree('refresh');
             
	      }
		 
		  
		 // 初始化主机虚拟化的tree
		 function initStorageVirtualTopologyTree(data){
             var source ={
                 datatype: "json",
                 datafields: [
                     { name: 'resTopologySid' },
                     { name: 'parentTopologySid' },
                     { name: 'resTopologyName' },
                     { name: 'topologyIcon' },
                     { name: 'topologyValue' }
                 ],
                 id: 'topologySid',
                 localdata: data
             };
             var dataAdapter = new $.jqx.dataAdapter(source);
             dataAdapter.dataBind(); 
             var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'topologyValue', map: 'value'},{ name: 'topologyIcon', map: 'icon'}]);
             $('#jqxTreeVirtualStorage').jqxTree({ source: records, width: '100%',height:'100%',allowDrag:true});
             $('#jqxTreeVirtualStorage').jqxTree('selectItem', $("#jqxTreeVirtualStorage").find('li')[1]);
             $('#jqxTreeVirtualStorage').jqxTree('expandItem', $("#jqxTreeVirtualStorage").find('li')[1]);
	      }
		 
		 // 初始化主机虚拟化的tree --- refresh
		 function initStorageRefreshVirtualTopologyTree(data){
             var source ={
                 datatype: "json",
                 datafields: [
                     { name: 'resTopologySid' },
                     { name: 'parentTopologySid' },
                     { name: 'resTopologyName' },
                     { name: 'topologyIcon' },
                     { name: 'topologyValue' }
                 ],
                 id: 'topologySid',
                 localdata: data
             };
             var dataAdapter = new $.jqx.dataAdapter(source);
             dataAdapter.dataBind(); 
             var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'topologyValue', map: 'value'},{ name: 'topologyIcon', map: 'icon'}]);
             $('#jqxTreeVirtualStorage').jqxTree({ source: records, width: '100%',height:'100%',allowDrag:true});
             $("#jqxTreeVirtualStorage").jqxTree('refresh');
             
	      }
		 
		 
		 // 还原主机虚拟机Tree的选择
		 function setHostVirtualTree(){
			 var item = $('#jqxTreeVirtual').jqxTree('getSelectedItem');
             var attr = item.value.split(","); 
             if(attr[1] == "R"){
             	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/region/res-region-mgt.jsp",function(){
             		initRegionTabs(attr[0],attr[1]);
             	});
             }else if(attr[1] == "DC"){
             	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/dc/res-dc-virtual-mgt.jsp",function(){
             		initDCTabs(attr[0],attr[1],"host");
             	});
             }else if(attr[1] == "HMC"){
             	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/ve/res-pve-virtual-mgt.jsp",function(){
            		initPVETabs(attr[0],attr[1],"host");
            	});
            }else if(attr[1] == "IVM"){
            	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/ve/res-pve-virtual-mgt.jsp",function(){
            		initPVETabs(attr[0],attr[1],"host");
            	});
            }else if(attr[1] == "VMware"){
             	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/ve/res-ve-virtual-mgt.jsp",function(){
             		initVETabs(attr[0],attr[1],"host");
             	});
             }else if(attr[1] == "VC"){
             	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/cluster/res-cluster-virtual-mgt.jsp",function(){
             		initClusterTabs(attr[0],attr[1]);
             	});
             }else if(attr[1] == "HOST"){
             	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/host/res-host-virtual-mgt.jsp",function(){
             		initHostTabs(attr[0],attr[1]);
             	});
             }else if(attr[1] == "PHOST"){
             	$("#containerVirtual .listContent").load(ctx+"/pages/resources/topology/host/res-pve-host-virtual-mgt.jsp",function(){
            		initPveHostTabs(attr[0],attr[1]);
            	});
            }
		 }
		 
		// 还原存储虚拟机Tree的选择
		 function setStorageVirtualTree(){
			 var item = $('#jqxTreeVirtualStorage').jqxTree('getSelectedItem');
             var attr = item.value.split(","); 
             if(attr[1] == "R"){
             	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/region/res-region-mgt.jsp",function(){
             		initRegionTabs(attr[0],attr[1]);
             	});
             }else if(attr[1] == "DC"){
             	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/dc/res-dc-virtual-mgt.jsp",function(){
             		initDCTabs(attr[0],attr[1],"storage");
             	});
             }else if(attr[1] == "HMC"){
             	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/ve/res-pve-virtual-mgt.jsp",function(){
            		initPVETabs(attr[0],attr[1],"storage");
            	});
            }else if(attr[1] == "IVM"){
            	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/ve/res-pve-virtual-mgt.jsp",function(){
            		initPVETabs(attr[0],attr[1],"storage");
            	});
            }else if(attr[1] == "VMware"){
             	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/ve/res-ve-storage-virtual-mgt.jsp",function(){
             		initVETabs(attr[0],attr[1],"storage");
             	});
             }else if(attr[1] == "RSC"){
             	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/rsc/res-rsc-virtual-mgt.jsp",function(){
            		initRSCTabs(attr[0],attr[1]);
            	});
            }else if(attr[1] == "STORAGE"){
            	$("#containerVirtual .listContentStorage").load(ctx+"/pages/resources/topology/storage/res-storage-virtual-mgt.jsp",function(){
            		initStorageTabs(attr[0],attr[1]);
            	});
            }
		 }

</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		
		<!-- 主机 -->
		<script type="text/javascript" src="${ctx}/js/res/host/host-datagrid-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/host/host-add-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/host/host-edit-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/host/host-detail-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/host/host-mountstorage-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/host/host-find-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/host/host-monitor-init-model.js"></script>
		
		<!-- 网络 -->
		<script type="text/javascript" src="${ctx}/js/res/network/network-vlan-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/network/network-ip-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/network/ip-add-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/network/ip-edit-init-model.js"></script>
		
		<!-- 存储 -->
		<script type="text/javascript" src="${ctx}/js/res/storage/storage-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/storage/storage-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/storage/storage-edit-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/storage/host-storage-model.js"></script>
		
		<!-- 资源池 -->
		<script type="text/javascript" src="${ctx}/js/res/pool/res-edit-pool-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/pool/res-add-pool-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/pool/res-add-host-to-pool-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/pool/res-remove-host-out-pool-model.js"></script>
		
		<!-- 主机和集群 -->
		<script type="text/javascript" src="${ctx}/js/res/dcCluster/res-edit-dc-cluster-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/dcCluster/res-add-dc-cluster-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/dcCluster/res-add-host-to-cluster-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/dcCluster/res-remove-host-out-cluster-model.js"></script>
		
		<!-- 虚拟机 -->
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-managed-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-reconfig-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-unManaged-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-unManagedVm-to-db.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-migrate-target-host.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-migrate-target-storage.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-detail-model.js"></script>
		
		<!-- 告警 -->
		<script type="text/javascript" src="${ctx}/js/res/alarm/alarm-console-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/alarm/alarm-info-confirm-model.js"></script>
		
		<style type="text/css">
			#containerHost{
				width:99.9%;
				height:99%;
				margin:0px;
			}
			#mainSplitterHost{
				width:99%;
				height:98%;
				margin-left:0.5%;
			}
			#mainSplitterHost .listHost{
				width:14.9%;
				height:100%;
				float:left;
				border:1px solid #E5E5E5;
			}
			#mainSplitterHost .listContent{
				width:84.1%;
				height:100%;
				margin-left:0.5%;
				float:left;
				border:1px solid #E5E5E5;
			}
    </style>  
</head>	
	
 <body>    
   <div id="containerHost">
   		<div style="margin:0px;padding:0px;height:8px;"></div>
 		<div id="mainSplitterHost"> 
            <div class="listHost">
            	<div id='viewType' style="border:0px;">
				    <ul>
				        <li>资源拓扑</li>
				        <li>资源池</li>
				    </ul>
				    <div>  
				       <div id='jqxTreeHost'style="width:100%;height:100%;border:0px;overflow:hidden"></div>
				    </div>
				     <div>  
				       <div id='jqxTreePool'style="width:100%;height:100%;border:0px;overflow:hidden"></div>
				    </div>
				</div>
            </div>
            <div class="listContent">
            </div>
       </div>
   </div>
   <%@ include file="../dcCluster/res-edit-dc-cluster-host.jsp"%>
   <%@ include file="../dcCluster/res-add-dc-cluster-host.jsp"%>
  <%@ include file="../pool/res-add-pool-host.jsp"%>
  <%@ include file="../host/res-add-host.jsp"%>
  <%@ include file="../storage/res-add-storage.jsp"%>
  <%@ include file="../host/res-find-host.jsp"%>
  <%@ include file="../network/res-add-ip.jsp"%>
  <%@ include file="../vm/res-unmanagedvm-to-db.jsp"%>
</body>
<script type="text/javascript">
         // 当前选中Tree的sid
         var resTopologySid;
         // 当前选中Tree的类型
         var resTopologyType;
         
		 $(function(){
				// 禁用Enter键
		       document.onkeypress=function(evt){
					if(evt.keyCode ==13){
						event.keyCode = 0;//屏蔽回车键  
			            event.returnvalue = false;  
						return false;
					}
				};
			 
			    $('#viewType').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
			 	// 清除前一个tab选中情况
			    $('#viewType').on('selected', function (event) {
				  	   var pageIndex = event.args.item;
				  	   if(pageIndex == 0){
				  		 $('#jqxTreeHost').jqxTree('selectItem', null);
				  	   }else{
				  		 $('#jqxTreePool').jqxTree('selectItem', null);  
				  	   }
			    });
				
			    // 查询主机和集群的Tree
				setHostTreeValue();
				// 资源拓扑tree选择事件
	  		    setPoolTreeValue();
				
	            $('#jqxTreeHost').on('select', function (event) {
	                var args = event.args;
	                var item = $('#jqxTreeHost').jqxTree('getItem', args.element);  
	                var attr = item.value.split(","); 
	                
	                if(attr[1] == "region"){
	                	$("#mainSplitterHost .listContent").load(ctx+"/pages/res/topology/region/res-region-mgt.jsp",function(){
	                		initRegionTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "dc"){
	                	$("#mainSplitterHost .listContent").load(ctx+"/pages/res/topology/dc/res-dc-host-mgt.jsp",function(){
	                		initDCTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "ve"){
	                	$("#mainSplitterHost .listContent").load(ctx+"/pages/res/topology/ve/res-ve-host-mgt.jsp",function(){
	                		initVETabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "cluster"){
	                	$("#mainSplitterHost .listContent").load(ctx+"/pages/res/topology/cluster/res-cluster-host-mgt.jsp",function(){
	                		initClusterTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "host"){
	                	$("#mainSplitterHost .listContent").load(ctx+"/pages/res/topology/host/res-host-mgt.jsp",function(){
	                		initHostTabs(attr[0],attr[1]);
	                	});
	                }
	            });
			    
			    // 资源池拓扑结构Tree选择事件
	            $('#jqxTreePool').on('select', function (event) {
	                var args = event.args;
	                var item = $('#jqxTreePool').jqxTree('getItem', args.element);  
	                var attr = item.value.split(","); 
	                
	                if(attr[1] == "region"){
	                	$("#mainSplitterHost .listContent").load(ctx+"/pages/res/topology/region/res-region-mgt.jsp",function(){
	                		initRegionTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "dc"){
	                	$("#mainSplitterHost .listContent").load(ctx+"/pages/res/topology/dc/res-dc-host-mgt.jsp",function(){
	                		initDCTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "cluster"){
	                	$("#mainSplitterHost .listContent").load(ctx+"/pages/res/topology/cluster/res-cluster-host-mgt.jsp",function(){
	                		initClusterTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "pool"){
	                	$("#mainSplitterHost .listContent").load(ctx+"/pages/res/topology/pool/res-pool-host-mgt.jsp",function(){
	                		initpoolTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "host"){
	                	$("#mainSplitterHost .listContent").load(ctx+"/pages/res/topology/host/res-host-mgt.jsp",function(){
	                		initHostTabs(attr[0],attr[1]);
	                	});
	                }
	            });
			    
			    
		 });
		 
		 // 给左边的资源拓扑tree赋值
		 function setHostTreeValue(){
			 Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys",
		 			type:'post',
		 			params:{
		 				resPoolType:"'RES-POOL-VM','RES-POOL-PM'",
		 				resType:"RES-HOST"
		 			},
		 			callback : function (data) {
		 				initTreeHost(data);
		 			}
		 	    });
		 }
		 
		// 给左边的资源池tree赋值
		 function setPoolTreeValue(){
			 Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys/pool",
		 			type:'post',
		 			params:{
		 				resPoolType:"'RES-POOL-VM','RES-POOL-PM'",
		 				resType:"RES-HOST"
		 			},
		 			callback : function (data) {
		 				initTreePool(data);
		 			}
		 	    });
		 }
		 
		 // 初始化主机的tree
		 function initTreeHost(data){
             var source ={
                 datatype: "json",
                 datafields: [
                     { name: 'topologySid' },
                     { name: 'topologyParentSid' },
                     { name: 'topologyText' },
                     { name: 'topologyIcon' },
                     { name: 'topologyValue' }
                 ],
                 id: 'topologySid',
                 localdata: data
             };
             // create data adapter. 
             var dataAdapter = new $.jqx.dataAdapter(source);
             // perform Data Binding.
             dataAdapter.dataBind();
             var records = dataAdapter.getRecordsHierarchy('topologySid', 'topologyParentSid', 'items', [{ name: 'topologyText', map: 'label'},{ name: 'topologyValue', map: 'value'},{ name: 'topologyIcon', map: 'icon'}]);
             // create jqxTree
             $('#jqxTreeHost').jqxTree({ source: records, width: '99.7%',height:'100%',allowDrag:false});
             $('#jqxTreeHost').jqxTree('selectItem', $("#jqxTreeHost").find('li:first')[0]);
             $('#jqxTreeHost').jqxTree('expandItem', $("#jqxTreeHost").find('li:first')[0]);
	      }
		 
		// 初始化资源池的tree
		 function initTreePool(data){
             var source ={
                 datatype: "json",
                 datafields: [
                     { name: 'topologySid' },
                     { name: 'topologyParentSid' },
                     { name: 'topologyText' },
                     { name: 'topologyIcon' },
                     { name: 'topologyValue' }
                 ],
                 id: 'topologySid',
                 localdata: data
             };
             // create data adapter. 
             var dataAdapter = new $.jqx.dataAdapter(source);
             // perform Data Binding.
             dataAdapter.dataBind();
             var records = dataAdapter.getRecordsHierarchy('topologySid', 'topologyParentSid', 'items', [{ name: 'topologyText', map: 'label'},{ name: 'topologyValue', map: 'value'},{ name: 'topologyIcon', map: 'icon'}]);
             // create jqxTree
             $('#jqxTreePool').jqxTree({ source: records, width: '99.7%',height:'100%',allowDrag:false});
             $('#jqxTreePool').jqxTree('selectItem', $("#jqxTreePool").find('li:first')[0]);
             $('#jqxTreePool').jqxTree('expandItem', $("#jqxTreePool").find('li:first')[0]);
             
	      }
</script>
</html>
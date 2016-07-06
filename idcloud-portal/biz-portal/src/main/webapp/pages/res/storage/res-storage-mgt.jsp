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
		<script type="text/javascript" src="${ctx}/js/res/pool/res-add-storage-to-pool-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/pool/res-remove-storage-out-pool-model.js"></script>
		
		<!-- 主机和集群 -->
		<script type="text/javascript" src="${ctx}/js/res/dcCluster/res-edit-dc-cluster-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/dcCluster/res-add-dc-cluster-model.js"></script>
		
		<!-- 虚拟机 -->
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-managed-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-reconfig-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-unManaged-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-unManagedVm-to-db.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-migrate-target-host.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-migrate-target-storage.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/vm/vm-detail-model.js"></script>
		
		<style type="text/css">
			#containerStorage{
				width:99.9%;
				height:99%;
				margin:0px;
			}
			#mainSplitterStorage{
				width:99%;
				height:98%;
				margin-left:0.5%;
			}
			#mainSplitterStorage .listStorage{
				width:15%;
				height:100%;
				float:left;
				border:1px solid #E5E5E5;
			}
			#mainSplitterStorage .listContent{
				width:84.1%;
				height:100%;
				margin-left:0.5%;
				float:left;
				border:1px solid #E5E5E5;
			}
    </style>  
</head>	
	
 <body>    
   <div id="containerStorage">
   		<div style="margin:0px;padding:0px;height:8px;"></div>
 		<div id="mainSplitterStorage"> 
            <div class="listStorage">
            	<div id='viewType' style="border:0px;">
				    <ul>
				        <li>资源拓扑</li>
				        <li>资源池</li>
				    </ul>
				    <div>
				       <div id='jqxTreeStorage'style="width:100%;height:100%;border:0px;overflow:hidden"></div>
				    </div>
				     <div>  
				       <div id='jqxTreePoolStorage'style="width:100%;height:100%;border:0px;overflow:hidden"></div>
				    </div>
				</div>
            
                
            </div>
            <div class="listContent">
            </div>
       </div>
   </div>
   <%@ include file="../dcCluster/res-edit-dc-cluster-host.jsp"%>
  <%@ include file="../dcCluster/res-add-dc-cluster-host.jsp"%>
  <%@ include file="../pool/res-add-pool-storage.jsp"%>
  <%@ include file="../storage/res-add-storage.jsp"%>
   <%@ include file="../host/res-add-host.jsp"%>
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
				// 查询主机和集群的Tree
				setHostTreeValue();
				setPoolTreeValue();
			    // tree选择事件
	            $('#jqxTreeStorage').on('select', function (event) {
	                var args = event.args;
	                var item = $('#jqxTreeStorage').jqxTree('getItem', args.element);  
	                var attr = item.value.split(","); 
	                
	                if(attr[1] == "region"){
	                	$("#mainSplitterStorage .listContent").load(ctx+"/pages/res/topology/region/res-region-mgt.jsp",function(){
	                		initRegionTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "ve"){
	                	$("#mainSplitterStorage .listContent").load(ctx+"/pages/res/topology/ve/res-ve-storage-mgt.jsp",function(){
	                		initVETabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "dc"){
	                	$("#mainSplitterStorage .listContent").load(ctx+"/pages/res/topology/dc/res-dc-storage-mgt.jsp",function(){
	                		initDCTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "storage"){
	                	$("#mainSplitterStorage .listContent").load(ctx+"/pages/res/topology/storage/res-storage-mgt.jsp",function(){
	                		initstorageTabs(attr[0],attr[1]);
	                	});
	                }
	            });
			    
	            $('#jqxTreePoolStorage').on('select', function (event) {
	                var args = event.args;
	                var item = $('#jqxTreePoolStorage').jqxTree('getItem', args.element);  
	                var attr = item.value.split(","); 
	                
	                if(attr[1] == "region"){
	                	$("#mainSplitterStorage .listContent").load(ctx+"/pages/res/topology/region/res-region-mgt.jsp",function(){
	                		initRegionTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "dc"){
	                	$("#mainSplitterStorage .listContent").load(ctx+"/pages/res/topology/dc/res-dc-storage-mgt.jsp",function(){
	                		initDCTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "pool"){
	                	$("#mainSplitterStorage .listContent").load(ctx+"/pages/res/topology/pool/res-pool-storage-mgt.jsp",function(){
	                		initpoolTabs(attr[0],attr[1]);
	                	});
	                }else if(attr[1] == "storage"){
	                	$("#mainSplitterStorage .listContent").load(ctx+"/pages/res/topology/storage/res-storage-mgt.jsp",function(){
	                		initstorageTabs(attr[0],attr[1]);
	                	});
	                }
	            });
		 });
		 
		 // 给左边的tree赋值
		 function setHostTreeValue(){
			 Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys",
		 			type:'post',
		 			params:{
		 				resPoolType:"'RES-POOL-STORAGE'",
		 				resType:"RES-STORAGE"
		 			},
		 			callback : function (data) {
		 				initTreeStorage(data);
		 			}
		 	    });
		 }
		 
		// 给左边的tree赋值
		 function setPoolTreeValue(){
			 Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys/pool",
		 			type:'post',
		 			params:{
		 				resPoolType:"'RES-POOL-STORAGE'",
		 				resType:"RES-STORAGE"
		 			},
		 			callback : function (data) {
		 				initTreePoolStorage(data);
		 			}
		 	    });
		 }
		 
		 // 初始化数据中心tree
		 function initTreeStorage(data){
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
             $('#jqxTreeStorage').jqxTree({ source: records, width: '99.7%',height:'100%',allowDrag:false});
             $('#jqxTreeStorage').jqxTree('selectItem', $("#jqxTreeStorage").find('li:first')[0]);
             $('#jqxTreeStorage').jqxTree('expandItem', $("#jqxTreeStorage").find('li:first')[0]);
             
	      }
		 
		// 初始化资源池tree
		 function initTreePoolStorage(data){
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
             $('#jqxTreePoolStorage').jqxTree({ source: records, width: '99.7%',height:'100%',allowDrag:false});
             $('#jqxTreePoolStorage').jqxTree('selectItem', $("#jqxTreePoolStorage").find('li:first')[0]);
             $('#jqxTreePoolStorage').jqxTree('expandItem', $("#jqxTreePoolStorage").find('li:first')[0]);
             
	      }
		 
		 
</script>
</html>
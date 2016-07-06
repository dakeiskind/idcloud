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
		<script type="text/javascript" src="${ctx}/js/res/pool/res-add-network-to-pool-model.js"></script>
		
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
		
		<!-- sharepoint -->
		<script type="text/javascript" src="${ctx}/js/res/sharepoint/sharepoint-config-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/exchange/exchange-config-model.js"></script>
		
		<style type="text/css">
			#containerSpEx{
				width:99.9%;
				height:100%;
				margin:0px;
			}
			#mainSplitterSpEx{
				width:99%;
				height:98%;
				margin-left:0.5%;
			}
			#mainSplitterSpEx .listHost{
				width:15%;
				height:100%;
				float:left;
				border:1px solid #E5E5E5;
			}
			#mainSplitterSpEx .listContent{
				width:84.1%;
				height:100%;
				margin-left:0.5%;
				float:left;
				border:1px solid #E5E5E5;
			}
			
		</style>
</head>	
<body>
   <div id="containerSpEx">
 	   <div style="margin:0px;padding:0px;height:8px;"></div>
 	   <div id="mainSplitterSpEx"> 
           <div class="listHost">
                <div id='jqxTreeSpEx'style="width:100%;height:100%;background:#eee;border:0px;overflow:hidden"></div>
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
   <%@ include file="../pool/res-add-pool-sharepoint.jsp"%>
  <%@ include file="../pool/res-add-pool-exchange.jsp"%>
  
</body>
<script type="text/javascript">
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
			setSpExTreeValue();

			 // 当选择某个item的时候
            $('#jqxTreeSpEx').on('select', function (event) {
            	var args = event.args;
                var item = $('#jqxTreeSpEx').jqxTree('getItem', args.element);  
                var attr = item.value.split(","); 
                var resPoolData = getResPoolsValue(attr[0]);

                if(attr[1] == "region"){
                	$("#mainSplitterSpEx .listContent").load(ctx+"/pages/res/topology/region/res-region-mgt.jsp",function(){
                		initRegionTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "dc"){
                	$("#mainSplitterSpEx .listContent").load(ctx+"/pages/res/topology/dc/res-dc-spex-mgt.jsp",function(){
                		initDCTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "pool"){
                	
                	if(resPoolData.resPoolType == 'RES-POOL-SHAREPOINT') {
                		$("#mainSplitterSpEx .listContent").load(ctx+"/pages/res/topology/pool/res-pool-sharepoint-mgt.jsp",function(){
                    		initpoolTabs(attr[0],attr[1]);
                    	});
                	} else if(resPoolData.resPoolType == 'RES-POOL-EXCHANGE') {
                		$("#mainSplitterSpEx .listContent").load(ctx+"/pages/res/topology/pool/res-pool-exchange-mgt.jsp",function(){
                    		initpoolTabs(attr[0],attr[1]);
                    	});
                	}
                }
            });
		});

		 // 初始化tree
		 function initTreeSpEx(data){
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
            $('#jqxTreeSpEx').jqxTree({ source: records, width: '99.7%',height:'100%'});
            $('#jqxTreeSpEx').jqxTree('selectItem', $("#jqxTreeSpEx").find('li:first')[0]);
            $('#jqxTreeSpEx').jqxTree('expandItem', $("#jqxTreeSpEx").find('li:first')[0]);
	    } 
		 
		 // 给左边的tree赋值
		 function setSpExTreeValue(){
			    Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys",
		 			type:'post',
		 			params:{
		 				resPoolType:"'RES-POOL-SHAREPOINT','RES-POOL-EXCHANGE'",
		 				resType:"RES-EXCHANGE"
		 			},
		 			callback : function (data) {
		 				initTreeSpEx(data);
		 			}
		 	    });
		 }
		 
		// 根据资源池sid取得资源池数据
		 function getResPoolsValue(resPoolSid){
			 var resPoolData;
				Core.AjaxRequest({
						url : ws_url + "/rest/pools/"+resPoolSid+"",
						type:"get",
						async:false,
						callback : function (data) {
							resPoolData = data;
					    },
					    failure:function(data){
					    	
					    }
				 });
				return resPoolData;
		 }
</script>
</html>

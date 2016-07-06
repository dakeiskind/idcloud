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
		<script type="text/javascript" src="${ctx}/js/res/network/vlan-add-init-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/res/network/vlan-edit-init-model.js"></script>
		
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
		
		<style type="text/css">
			#containerNetwork{
				width:99.9%;
				height:100%;
				margin:0px;
			}
			#mainSplitterNetwork{
				width:99%;
				height:98%;
				margin-left:0.5%;
			}
			#mainSplitterNetwork .listHost{
				width:15%;
				height:100%;
				float:left;
				border:1px solid #E5E5E5;
			}
			#mainSplitterNetwork .listContent{
				width:84.1%;
				height:100%;
				margin-left:0.5%;
				float:left;
				border:1px solid #E5E5E5;
			}
			
		</style>
</head>	
<body>
   <div id="containerNetwork">
 	   <div style="margin:0px;padding:0px;height:8px;"></div>
 	   <div id="mainSplitterNetwork"> 
           <div class="listHost">
                <div id='jqxTreeNetwork'style="width:100%;height:100%;background:#eee;border:0px;overflow:hidden"></div>
	             <div id='jqxNetworkMenu'>
		            <ul>
		                <li>新增</li>
		                <li>编辑</li>
		            </ul>
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
  <%@ include file="../network/res-add-vlan.jsp"%>
  <%@ include file="../network/res-edit-vlan.jsp"%>
  <%@ include file="../vm/res-unmanagedvm-to-db.jsp"%>
  <%@ include file="../pool/res-add-pool-ip.jsp"%>
  <%@ include file="../pool/res-add-pool-vlan.jsp"%>
</body>
<script type="text/javascript">
		var resTopologySid;
		// 当前选中Tree的类型
		var resTopologyType;
		
		// 定义右击弹出框
		var contextMenu;
		$(function(){
			// 在网络管理tab中，默认选中数据中心，右边默认显示网络数据中心信息
			//$("#mainSplitterNetwork .listContent").load(ctx+"/pages/res/dc/res-dc-mgt.jsp",function(){
				// 记得传入参数，以区分这里调用网络数据中心
			//	initDCTabs();
			//});
			 // 当前选中Tree的sid
			
		   // 禁用Enter键
	       document.onkeypress=function(evt){
				if(evt.keyCode ==13){
					event.keyCode = 0;//屏蔽回车键  
		            event.returnvalue = false;  
					return false;
				}
			};
			setNetworkTreeValue();

			 // 当选择某个item的时候
            $('#jqxTreeNetwork').on('select', function (event) {
            	var args = event.args;
                var item = $('#jqxTreeNetwork').jqxTree('getItem', args.element);  
                var attr = item.value.split(","); 
                var resPoolData = getResPoolsValue(attr[0]);
				
                if(attr[1] == "region"){
                	$("#mainSplitterNetwork .listContent").load(ctx+"/pages/res/topology/region/res-region-mgt.jsp",function(){
                		initRegionTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "dc"){
                	$("#mainSplitterNetwork .listContent").load(ctx+"/pages/res/topology/dc/res-dc-network-mgt.jsp",function(){
                		initDCTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "pool"){
                	
                	if(resPoolData.resPoolType == 'RES-POOL-VLAN') {
                		$("#mainSplitterNetwork .listContent").load(ctx+"/pages/res/topology/pool/res-pool-vlan-mgt.jsp",function(){
                    		initpoolTabs(attr[0],attr[1]);
                    	});
                	} else if(resPoolData.resPoolType == 'RES-POOL-IP') {
                		$("#mainSplitterNetwork .listContent").load(ctx+"/pages/res/topology/pool/res-pool-ip-mgt.jsp",function(){
                    		initpoolTabs(attr[0],attr[1]);
                    	});
                	}
                	
                }
            });

		     // 当按键按下tree的时候
           attachContextMenu();
		    
	         // 当选中右击操作框的时候
	  		  $("#jqxNetworkMenu").on('itemclick', function (event) {
	                  var item = $.trim($(event.args).text());
                 switch (item) {
                     case "新增":
                         var item = $('#jqxTreeNetwork').jqxTree('selectedItem');
                         if (item != null) {
                       	  var attr = item.value.split(","); 
                          var resPoolData = getResPoolsValue(attr[0]);

                       	  if(attr[1] == "dc"){
                       		  // 新增数据中心
                       		  popaddHostDcAndClusterWindow('02');
                       	  }else if(attr[1] == "pool"){
                       		if(resPoolData.resPoolType == 'RES-POOL-VLAN') {
                       			popAddRespoolVlanWindow();
                        	} else if(resPoolData.resPoolType == 'RES-POOL-IP') {
                        		popAddRespoolIpWindow();
                        	}
                       	  }
                       	  attachContextMenu();
                         }
                         break;
                     case "编辑":
                   	  var item = $('#jqxTreeNetwork').jqxTree('selectedItem');
                   	  if (item != null) {
                       	  var attr = item.value.split(","); 
                          var resPoolData = getResPoolsValue(attr[0]);

                       	  if(attr[1] == "dc"){
                       		  // 编辑数据中心
                       		 popEditHostDcAndClusterWindow();
                       	  }else if(attr[1] == "pool"){
                       		  // 编辑资源池
                       		 if(resPoolData.resPoolType == 'RES-POOL-VLAN') {
                       			popEditRespoolVlanWindow();
                        	} else if(resPoolData.resPoolType == 'RES-POOL-IP') {
                        		popEditRespoolIpWindow();
                        	}
                       	  }
                       	  attachContextMenu();
                         }
                         break;
                 }
             });
		});

		 // 初始化tree
		 function initTreeNetwork(data){
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
            $('#jqxTreeNetwork').jqxTree({ source: records, width: '99.7%',height:'100%'});
            $('#jqxTreeNetwork').jqxTree('selectItem', $("#jqxTreeNetwork").find('li:first')[0]);
            $('#jqxTreeNetwork').jqxTree('expandItem', $("#jqxTreeNetwork").find('li:first')[0]);
            
            // 初始化右击操作
            contextMenu = $("#jqxNetworkMenu").jqxMenu({ width: '120px',  height: '56px', autoOpenPopup: false, mode: 'popup',theme:currentTheme });

	        } 
		 
		 // 是否右击
		 function isRightClick(event) {
              var rightclick;
              if (!event) var event = window.event;
              if (event.which) rightclick = (event.which == 3);
              else if (event.button) rightclick = (event.button == 2);
              return rightclick;
          }
		 
		 // 禁止浏览器默认的右击
		  $(document).on('contextmenu', function (e) {
                if ($(e.target).parents('.jqx-tree').length > 0) {
                    return false;
                }
                return true;
          });
		// 当按键按下tree的时候
		 function attachContextMenu(){
			 $("#jqxTreeNetwork").on('mousedown', function (event) {
	                var target = $(event.target).parents('li:first')[0];
	                var rightClick = isRightClick(event);
	                if (rightClick && target != null) {
	                    $("#jqxTreeNetwork").jqxTree('selectItem', target);
	                    var scrollTop = $(window).scrollTop();
	                    var scrollLeft = $(window).scrollLeft();
	                    contextMenu.jqxMenu('open', parseInt(event.clientX) + 5 + scrollLeft, parseInt(event.clientY) + 5 + scrollTop);
	                    return false;
	                }
	            });
		 }
		 // 给左边的tree赋值
		 function setNetworkTreeValue(){
			    Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys/pool",
		 			type:'post',
		 			params:{
		 				resPoolType:"'RES-POOL-IP','RES-POOL-VLAN'",
		 				resType:"RES-IP"
		 			},
		 			callback : function (data) {
		 				initTreeNetwork(data);
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

<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='clusterTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>主机</li> 
        <li>存储</li> 
        <li>虚拟机</li>
        
    </ul>
    <div id="cluster_summary">  
        <%@ include file="res-cluster-virtual-summary.jsp"%>
    </div>
    <div id="clusterTabsContent1">
    	
    </div>
    <div id="clusterTabsContent2">
    	
    </div>
    <div id="clusterTabsContent3">
    </div>

</div>
<script type="text/javascript">
	 function initClusterTabs(id,type){
		  // 初始化数据中心tabs选项卡
		  $('#clusterTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});

		  // 设置选中Tree的sid和类型
		  resTopologySid = id;
		  resTopologyType = type;
		  // 初始化集群概要
		  initHostClusterSummary();
		  
		  $('#clusterTabs').on('selected', function (event) {
			  	   var pageIndex = event.args.item;  
			  	   
	               if(pageIndex == 1){
	                	$("#clusterTabsContent1").load(ctx+"/pages/resources/host/res-host-datagrid-index.jsp",function(){	
	                		HostDatagridModel();
						});
	                }else if(pageIndex == 2){
	                	$("#clusterTabsContent2").load(ctx+"/pages/resources/storage/res-storage-datagrid-index.jsp",function(){	
	                   		StorageDatagridModel();
    					});
	                }else if(pageIndex == 3){
	                	$("#clusterTabsContent3").load(ctx+"/pages/resources/vm/res-vm-datagrid-index.jsp",function(){	
	                		VMDatagridModel();
						});
	                }
          });
	 }
	
</script>


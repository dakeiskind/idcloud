<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='vestorageTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>集群</li>
        <li>主机</li>  
        <li>存储</li>
        <li>虚拟机</li>
        
    </ul>
    <div id="veTabsContent0">  
        <%@ include file="res-ve-storage-virtual-summary.jsp"%>
    </div>
    <div id="veTabsContent1">
    
    </div>
    <div id="veTabsContent2">
    
    </div>
    <div id="veTabsContent3">
    
    </div>
    <div id="veTabsContent4">
    </div>

</div>
<script type="text/javascript">
	 function initVETabs(id,type,virtualType){
		 
		  // 初始化数据中心tabs选项卡
		  $('#vestorageTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  
		  initHostVESummary(virtualType);
		  
	 }
	 $('#vestorageTabs').on('selected', function (event) {
		  	   var pageIndex = event.args.item;
		  	 if(pageIndex == 1){
               	$("#veTabsContent1").load(ctx+"/pages/resources/virtual/vc/res-vc-datagrid-index.jsp",function(){	
               		vcDatagridModel();
				});
               }else if(pageIndex == 2){
               	$("#veTabsContent2").load(ctx+"/pages/resources/host/res-host-datagrid-index.jsp",function(){	
               	   HostDatagridModel();
					});
               }else if(pageIndex == 3){
               	$("#veTabsContent3").load(ctx+"/pages/resources/storage/res-storage-datagrid-index.jsp",function(){	
               		StorageDatagridModel();
					});
               }else if(pageIndex == 4){
               	$("#veTabsContent4").load(ctx+"/pages/resources/vm/res-vm-datagrid-index.jsp",function(){	
               	 	VMDatagridModel();
					});
               }
//                else if(pageIndex == 5){
//             	   $("#veTabsContent5").load(ctx+"/pages/resources/alarm/alarm-console-index.jsp",function(){	
//             		   initAlarmConsolePageJs();
//    					});
//                }
     });
	
</script>


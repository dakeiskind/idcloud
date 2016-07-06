<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='pveTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>主机</li>  
        <li>存储</li>
        <li>分区</li>
        <li>VIOS</li>
    </ul>
    <div id="pveTabsContent0">  
        <%@ include file="res-pve-virtual-summary.jsp"%>
    </div>
    <div id="pveTabsContent1">
    	主机
    </div>
    <div id="pveTabsContent2">
    	存储
    </div>
    <div id="pveTabsContent3">
    	分区
    </div>
    <div id="pveTabsContent4">
    	VIOS
    </div>

</div>
<script type="text/javascript">
	 function initPVETabs(id,type,virtualType){
		 
		  // 初始化数据中心tabs选项卡
		  $('#pveTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  initHostPVESummary(virtualType);
		  
	 }
	 $('#pveTabs').on('selected', function (event) {
		  	   var pageIndex = event.args.item;
			  if(pageIndex == 1){
               		$("#pveTabsContent1").load(ctx+"/pages/resources/host/res-pve-host-datagrid-index.jsp",function(){	
               	  	 	pveHostDatagridModel();
					});
               }else if(pageIndex == 2){
            	   
               		$("#pveTabsContent2").load(ctx+"/pages/resources/storage/res-pve-storage-datagrid-index.jsp",function(){	
               			initPveHostViosStorage();
					});
               }else if(pageIndex == 3){
               		$("#pveTabsContent3").load(ctx+"/pages/resources/vm/res-pve-vm-datagrid-index.jsp",function(){	
               	 		pveVMDatagridModel();
					});
               }else if(pageIndex == 4){
               		$("#pveTabsContent4").load(ctx+"/pages/resources/vios/res-vios-host-index.jsp",function(){	
               			initViosHostModel();
					});
               }
     });
	
</script>


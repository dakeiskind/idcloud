<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='dcTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>资源分区</li>
        <li>主机</li>  
		<li>存储</li>
		<li>虚拟机&分区</li>
    </ul>
    <div id="dcTabsContent0">  
        <%@ include file="res-dc-pool-summary.jsp"%>
    </div>
    <div id="dcTabsContent1">
    	
    </div>
    <div id="dcTabsContent2">
    	
    </div>
    <div id="dcTabsContent3">
    	
    </div>
    <div id="dcTabsContent4">
    	
    </div>
</div>
<script type="text/javascript">
	 function initDCTabs(id,type){
		  // 初始化数据中心tabs选项卡
		  $('#dcTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  initHostDCSummary();
	 }
	 $('#dcTabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;
	  	 if(pageIndex == 1){
        	  $("#dcTabsContent1").load(ctx+"/pages/resources/pool/rz/res-rz-datagrid-index.jsp");
          }else if(pageIndex == 2){
        	  $("#dcTabsContent2").load(ctx+"/pages/resources/host/res-host-datagrid-index.jsp",function(){
        		  HostDatagridModel();
        	  });	
          }else if(pageIndex == 3){
        	  $("#dcTabsContent3").load(ctx+"/pages/resources/storage/res-storage-datagrid-index.jsp",function(){
        		  StorageDatagridModel();
        	  });
          }else if(pageIndex == 4){
        	  
        	  $("#dcTabsContent4").load(ctx+"/pages/resources/vm/res-vm-datagrid-index.jsp",function(){
        		  VMDatagridModel();
        	  });
          }
  });
	
</script>


<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='dcTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>资源环境</li>
        <li>集群</li>
        <li>主机</li>  
		<li>存储</li>
		<li>虚拟机&分区</li>
    </ul>
    <div id="dcTabsContent0">  
        <%@ include file="res-dc-virtual-summary.jsp"%>
    </div>
    <div id="dcTabsContent1">
    	
    </div>
    <div id="dcTabsContent2">
    	
    </div>
    <div id="dcTabsContent3">
    	
    </div>
    <div id="dcTabsContent4">
    	
    </div>
    <div id="dcTabsContent5">
    	
    </div>
</div>
<script type="text/javascript">
	 function initDCTabs(id,type,virtualType){
		  // 初始化数据中心tabs选项卡
		  $('#dcTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  if(virtualType == "host"){
			  var selectedItem = $("#jqxTreeVirtual").jqxTree('selectedItem');
		      var prevItem = $("#jqxTreeVirtual").jqxTree('getPrevItem', selectedItem.element);
		      var arr = prevItem.value.split(",");
		      parentTopologySid = arr[0];
		  }else if(virtualType == "storage"){
			  var selectedItem = $("#jqxTreeVirtualStorage").jqxTree('selectedItem');
		      var prevItem = $("#jqxTreeVirtualStorage").jqxTree('getPrevItem', selectedItem.element);
		      var arr = prevItem.value.split(",");
		      parentTopologySid = arr[0];
		  }
		  
		  // 初始化概要页面
		  initHostDCSummary();
	 }
	 $('#dcTabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;
	  	 if(pageIndex == 1){
        	  $("#dcTabsContent1").load(ctx+"/pages/resources/virtual/ve/res-ve-datagrid-index.jsp");
          }else if(pageIndex == 2){
        	  $("#dcTabsContent2").load(ctx+"/pages/resources/virtual/vc/res-vc-datagrid-index.jsp",function(){
        		  vcDatagridModel();
        	  });	
          }else if(pageIndex == 3){
        	  $("#dcTabsContent3").load(ctx+"/pages/resources/host/res-host-datagrid-index.jsp",function(){
        		  HostDatagridModel();
        	  });	
          }else if(pageIndex == 4){
        	  $("#dcTabsContent4").load(ctx+"/pages/resources/storage/res-storage-datagrid-index.jsp",function(){
        		  StorageDatagridModel();
        	  });	
          }else if(pageIndex == 5){
        	  $("#dcTabsContent5").load(ctx+"/pages/resources/vm/res-vm-datagrid-index.jsp",function(){
        		  VMDatagridModel();
        	  });
          }
  });
	
</script>


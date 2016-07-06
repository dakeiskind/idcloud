<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='storageTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
<!--         <li>存储</li> -->
    </ul>
    <div id="storageTabsContent0">  
        <%@ include file="res-storage-virtual-summary.jsp"%>
    </div>
<!--     <div id="storageTabsContent1"> -->
    
<!--     </div> -->

</div>
<script type="text/javascript">
	 function initStorageTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#storageTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  
		  initStorageSummary();
		  
	 }
	 $('#storageTabs').on('selected', function (event) {
		  	   var pageIndex = event.args.item;
		  	   if(pageIndex == 1){
// 	               	$("#storageTabsContent1").load(ctx+"/pages/resources/virtual/vc/res-vc-datagrid-index.jsp",function(){	
// 	               		vcDatagridModel();
// 					});
               }
     });
	
</script>


<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='rscTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>存储</li>
    </ul>
    <div id="rscTabsContent0">  
        <%@ include file="res-rsc-virtual-summary.jsp"%>
    </div>
    <div id="rscTabsContent1">
    
    </div>
</div>
<%@ include file="../../rsc/res-remove-storage-to-rsc-index.jsp"%>
<script type="text/javascript">
	 function initRSCTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#rscTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  initRscRemoveStorageModel();
		  // 初始化概要页面
		  initStorageRscSummary();
		  
	 }
	 $('#rscTabs').on('selected', function (event) {
		  	   var pageIndex = event.args.item;
		  	   if(pageIndex == 1){
	               	$("#rscTabsContent1").load(ctx+"/pages/resources/rsc/res-storage-rsc-index.jsp",function(){	
	               		initRscStorageModel();
					});
               }
     });
	
</script>


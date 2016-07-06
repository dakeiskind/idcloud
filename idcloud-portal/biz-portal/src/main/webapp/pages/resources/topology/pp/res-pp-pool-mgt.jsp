<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='ppTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>资源集群</li>
    </ul>
    <div id="ppTabsContent0">  
        <%@ include file="res-pp-pool-summary.jsp"%>
    </div>
    <div id="ppTabsContent1">
    		资源集群
    </div>
    
</div>
<script type="text/javascript">
	 function initPPTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#ppTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  
		  initPoolPPSummary();
		  
	 }
	 $('#ppTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 1){
// 	               	$("#veTabsContent1").load(ctx+"/pages/resources/virtual/vc/res-vc-datagrid-index.jsp",function(){	
// 	               		vcDatagridModel();
// 					});
             }
     });
	
</script>


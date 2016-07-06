<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='pcdnTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>CDN资源池</li>
    </ul>
    <div id="pcdnTabsContent0">  
        <%@ include file="res-pcdn-pool-summary.jsp"%>
        
    </div>
    <div id="pcdnTabsContent1">
    
    </div>
    
</div>
<script type="text/javascript">
 	 var noRelationData,relationData;
	 function initCDNTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#pcdnTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  // 初始化概要页面
		  initPoolPCDNSummary();
		  
	 }
	 $('#pcdnTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 1){
	               	$("#pcdnTabsContent1").load(ctx+"/pages/resources/pool/pcdn/res-pcdn-datagrid-index.jsp",function(){	
	               		initPcdnDatagridModel();
					});
             }
     });
	
</script>


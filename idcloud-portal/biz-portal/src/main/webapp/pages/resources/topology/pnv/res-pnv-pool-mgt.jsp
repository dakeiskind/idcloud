<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='pnvTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>VLAN池</li>
    </ul>
    <div id="pnvTabsContent0">  
        <%@ include file="res-pnv-pool-summary.jsp"%>
        
    </div>
    <div id="pnvTabsContent1">
    
    </div>
    
</div>
<script type="text/javascript">
 	 var noRelationData,relationData;
	 function initPNVTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#pnvTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  initPoolPNVSummary();
		  
	 }
	 $('#pnvTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 1){
	               	$("#pnvTabsContent1").load(ctx+"/pages/resources/pool/pnv/res-pnv-datagrid-index.jsp",function(){	
	               		initPnvDatagridModel();
					});
             }
     });
	
</script>


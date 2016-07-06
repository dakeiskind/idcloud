<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='posTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>对象存储资源池</li>
    </ul>
    <div id="posTabsContent0">  
        <%@ include file="res-pos-pool-summary.jsp"%>
        
    </div>
    <div id="posTabsContent1">
    
    </div>
    
</div>
<script type="text/javascript">
 	 var noRelationData,relationData;
	 function initPOSTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#posTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  // 初始化概要页面
		  initPoolPOSSummary();
		  
	 }
	 $('#posTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 1){
	               	$("#posTabsContent1").load(ctx+"/pages/resources/pool/pos/res-pos-datagrid-index.jsp",function(){	
	               		initPosDatagridModel();
					});
             }
     });
	
</script>


<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='psTabs' style="border:0px;">
    <ul>
    	<li style="margin-left: 30px;">概要</li> 
        <li>存储</li>
    </ul>
    <div id="psTabsContent0">
   		<%@ include file="res-ps-pool-summary.jsp"%>
    </div>
    <div id="psTabsContent1">
   		
    </div>
</div>
<%@ include file="../../pool/ps/res-ps-relation-index.jsp"%>
<script type="text/javascript">
	 function initPSTabs(id,type){
		  // 初始化数据中心tabs选项卡
		  $('#psTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		 
		  // 初始化概要
		  initPoolPSSummary();
		  
		  // 初始化新增关联存储
		  initAddRelationStorage();
		  
	 }
	 $('#psTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 1){
			  		$("#psTabsContent1").load(ctx+"/pages/resources/pool/ps/res-ps-datagrid-index.jsp",function(){	
		               	  // 初始化计算资源池
		          		  initPsDatagridModel();
					});
             }
     });
	
	//初始化新增存储
	 function initAddRelationStorage(){
	 	var relation = new poolPsRelationModel();
	 	relation.initInput();
	 	relation.initPopWindow();
	 	relation.initPsDatagrid();
	 	relation.searchStoragesInfo();
	 }
</script>


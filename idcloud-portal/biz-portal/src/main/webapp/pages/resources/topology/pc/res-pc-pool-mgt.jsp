<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='pcTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li> 
        <li>资源集群</li>
        <li>主机</li>
    </ul>
    <div id="pcTabsContent0">  
        <%@ include file="res-pc-pool-summary.jsp"%> 
        
    </div>
    <div id="pcTabsContent1">
    		
    </div>
    <div id="pcTabsContent2">
    		
    </div>
    
</div>
<script type="text/javascript">
	 function initPCTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#pcTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  initPoolPCSummary();
		  
		  // 初始化关联集群页面
		  initPcRelationCluster();
		  
	 }
	 $('#pcTabs').on('selected', function (event) {
	  	  var pageIndex = event.args.item;
	  	  if(pageIndex == 1){
              	$("#pcTabsContent1").load(ctx+"/pages/resources/pool/pc/res-pc-cluster-datagrid-index.jsp",function(){	
               	   // 资源集群
          		   initPcDatagridModel();
              	});
          }else if(pageIndex == 2){
        	    $("#pcTabsContent2").load(ctx+"/pages/resources/pool/pc/res-pc-host-datagrid-index.jsp",function(){	
              	   // 主机
         		   initPcHostDatagridModel();
             	});
          }
     });
	
</script>


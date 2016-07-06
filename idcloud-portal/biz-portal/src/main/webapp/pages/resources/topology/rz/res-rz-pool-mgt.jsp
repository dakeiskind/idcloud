<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='rzTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
<!--         <li>主机</li>   -->
<!--         <li>存储</li> -->
    </ul>
    <div id="rzTabsContent0">  
        <%@ include file="res-rz-pool-summary.jsp"%>
    </div>
<!--     <div id="rzTabsContent1"> -->
    
<!--     </div> -->
<!--     <div id="rzTabsContent2"> -->
    
<!--     </div> -->

</div>
<script type="text/javascript">
	 function initRZTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#rzTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  initPoolRZSummary();
		  
	 }
	 $('#rzTabs').on('selected', function (event) {
		  	   var pageIndex = event.args.item;
// 		  	   if(pageIndex == 1){
// 		  			$("#rzTabsContent1").load(ctx+"/pages/resources/host/res-host-datagrid-index.jsp",function(){	
// 	               	   	HostDatagridModel();
// 					});
//                }else if(pageIndex == 2){
//             	    $("#rzTabsContent2").load(ctx+"/pages/resources/storage/res-storage-datagrid-index.jsp",function(){	
//             	    	StorageDatagridModel();
// 					});
//                }
     });
	
</script>


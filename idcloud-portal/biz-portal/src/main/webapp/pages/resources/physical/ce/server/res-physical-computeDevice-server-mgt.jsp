<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='serverTabs' style="border:0px;">
    <ul>
    	<!-- <li style="margin-left: 30px;">概要</li> -->
        <li style="margin-left: 30px;">服务器</li>
    </ul>
   <%--  <div id="serverTabsContent0">  
         <%@ include file="/pages/resources/topology/ve/res-ve-virtual-summary.jsp"%>
    </div> --%>
    <div id="serverTabsContent0">
    </div>
</div>

<script type="text/javascript">
	 function initServerTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#serverTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		 
		  
		  // 初始化概要页面
		 // initHostVESummary();
		  $("#serverTabsContent0").load(ctx+"/pages/resources/physical/ce/server/res-ce-server-datagrid.jsp",
		           function(){	
		              serverDatagridModel();
				});
		  
	 }
	/*  $('#serverTabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;
	  	 if(pageIndex == 0){
	  		$("#serverTabsContent0").load(ctx+"/pages/resources/physical/ce/server/res-ce-server-datagrid.jsp",
	           function(){	
	              serverDatagridModel();
			});
         }
     }); */
	
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='cabinettabs' style="border:0px;">
    <ul>
    	<!-- <li style="margin-left: 30px;">概要</li> -->
        <li style="margin-left: 30px;">机柜</li>
    </ul>
   <%--  <div id="cabinettabsContent0">  
         <%@ include file="/pages/resources/topology/ve/res-ve-virtual-summary.jsp"%>
    </div> --%>
    <div id="cabinettabsContent0">
    </div>
</div>

<script type="text/javascript">
	 function initCabinetTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#cabinettabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		 
		  
		  // 初始化概要页面
		 // initHostVESummary();
		  $("#cabinettabsContent0").load(ctx+"/pages/resources/physical/pl/cabinet/res-pl-cabinet-datagrid.jsp",
		           function(){	
		  			cabinetDatagridModel();
		});
		  
	 }
	 /* $('#cabinettabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;
	  	 if(pageIndex == 0){
	  		$("#cabinettabsContent0").load(ctx+"/pages/resources/physical/pl/cabinet/res-pl-cabinet-datagrid.jsp",
	           function(){	
	  			cabinetDatagridModel();
			});
         }
     }); */
	
</script>
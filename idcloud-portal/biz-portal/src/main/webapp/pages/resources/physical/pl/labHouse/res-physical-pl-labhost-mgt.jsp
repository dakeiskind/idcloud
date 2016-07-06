<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='labHostabs' style="border:0px;">
    <ul>
    	<!-- <li style="margin-left: 30px;">概要</li> -->
        <li  style="margin-left: 30px;">机房</li>
    </ul>
   <%--  <div id="labHostabsContent0">  
         <%@ include file="/pages/resources/topology/ve/res-ve-virtual-summary.jsp"%>
    </div> --%>
    <div id="labHostabsContent0">
      <%--  <%@include file="/pages/resources/physical/pl/labHouse/res-pl-labhost-datagrid.jsp" %> --%>
    </div>
</div>

<script type="text/javascript">
	 function initLabHostTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		   $('#labHostabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  // 初始化概要页面
		 // initHostVESummary();
		  $("#labHostabsContent0").load(ctx+"/pages/resources/physical/pl/labHouse/res-pl-labhost-datagrid.jsp",
		           function(){	
		  			labHouseDatagridModel();
		  });
	 }
/*  	 $('#labHostabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;
	  	 if(pageIndex == 0){
	  		$("#labHostabsContent0").load(ctx+"/pages/resources/physical/pl/labHouse/res-pl-labhost-datagrid.jsp",
	           function(){	
	  			labHouseDatagridModel();
			});
         }
     });  */
	
</script>
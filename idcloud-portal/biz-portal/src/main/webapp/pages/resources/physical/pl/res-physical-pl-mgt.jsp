<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='phyLocationTabs' style="border:0px;">
    <ul>
    	<!-- <li style="margin-left: 30px;">概要</li> -->
        <li style="margin-left: 30px;">机房</li>
        <li>机柜</li>
        <li>机架</li>
    </ul>
   <%--  <div id="phyLocationTabsContent0">  
         <%@ include file="/pages/resources/topology/ve/res-ve-virtual-summary.jsp"%>
    </div> --%>
    <div id="phyLocationTabsContent0">
    
    </div>
    <div id="phyLocationTabsContent1">
    
    </div>
    <div id="phyLocationTabsContent2">
    
    </div>

</div>

<script type="text/javascript">
	 function initPhyLocationTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#phyLocationTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  // 初始化概要页面
		  
		//  initHostVESummary();
			$("#phyLocationTabsContent0").load(ctx+"/pages/resources/physical/pl/labHouse/res-pl-labhost-datagrid.jsp",
	               	function(){	
	               		labHouseDatagridModel();
			}); 
		  
		  
	 }
	 $('#phyLocationTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 1){
               	$("#phyLocationTabsContent1").load(ctx+"/pages/resources/physical/pl/cabinet/res-pl-cabinet-datagrid.jsp",
               	function(){	
               		cabinetDatagridModel();
				});
             }
		  	if(pageIndex == 2){
               	$("#phyLocationTabsContent2").load(ctx+"/pages/resources/physical/pl/rack/res-pl-rack-datagrid.jsp",
               	function(){	
               		rackDatagridModel();
				});
             }
     });
	
</script>
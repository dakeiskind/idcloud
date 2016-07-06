<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='sanTabs' style="border:0px;">
    <ul>
    	<!-- <li style="margin-left: 30px;">概要</li> -->
        <li style="margin-left: 30px;">存储</li>
    </ul>
    <%-- <div id="sanTabsContent0">  
         <%@ include file="/pages/resources/topology/ve/res-ve-virtual-summary.jsp"%>
    </div> --%>
    <div id="sanTabsContent0">
    
    </div>
    

</div>

<script type="text/javascript">
	 function initSANTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#sanTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  // 初始化概要页面
		  
		  //initHostVESummary();
			$("#sanTabsContent0").load(ctx+"/pages/resources/physical/se/san/res-se-san-datagrid.jsp",
	               	function(){	
	               		sanDatagridModel();
					});
		  
	 }
/* 	 $('#sanTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 0){
               	$("#sanTabsContent0").load(ctx+"/pages/resources/physical/se/san/res-se-san-datagrid.jsp",
               	function(){	
               		sanDatagridModel();
				});
             }
     }); */
	
</script>
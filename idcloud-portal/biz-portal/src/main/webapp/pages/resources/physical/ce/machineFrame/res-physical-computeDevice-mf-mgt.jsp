<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='machineFrameTabs' style="border:0px;">
    <ul>
    	<!-- <li style="margin-left: 30px;">概要</li> -->
        <li style="margin-left: 30px;">刀箱</li>
    </ul>
    <%-- <div id="machineFrameTabsContent0">  
         <%@ include file="/pages/resources/topology/ve/res-ve-virtual-summary.jsp"%>
    </div> --%>
    <div id="machineFrameTabsContent0">
    
    </div>

</div>
<script type="text/javascript">
	 function initMachineFrameTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#machineFrameTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  
		//initHostVESummary();
		  $("#machineFrameTabsContent0").load(ctx+"/pages/resources/physical/ce/machineFrame/res-ce-mf-datagrid.jsp",
	               	function(){	
	               		machineFrameDatagridModel();
					});
		  
	 }
/* 	 $('#machineFrameTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 0){
               	$("#machineFrameTabsContent0").load(ctx+"/pages/resources/physical/ce/machineFrame/res-ce-mf-datagrid.jsp",
               	function(){	
               		machineFrameDatagridModel();
				});
             }
     }); */
	
</script>
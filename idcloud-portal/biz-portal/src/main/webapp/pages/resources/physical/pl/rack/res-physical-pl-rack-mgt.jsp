<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='racktabs' style="border:0px;">
    <ul>
        <li  style="margin-left: 30px;">机架</li>
    </ul>
    <div id="labHostabsRack0">
    </div>
</div>

<script type="text/javascript">
	 function initRackTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#racktabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  // 初始化概要页面
		  $("#labHostabsRack0").load(ctx+"/pages/resources/physical/pl/rack/res-pl-rack-datagrid.jsp",
		           function(){	
			  rackDatagridModel();
		  });
	 }

	
</script>
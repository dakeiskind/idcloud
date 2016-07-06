<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='regionTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
    </ul>
    <div id="regionTabsContent0">  
        <%@ include file="res-region-pool-summary.jsp"%>
    </div>
</div>

<script type="text/javascript">
	 function initRegionTabs(id,type){
		  // 初始化数据中心tabs选项卡
		  $('#regionTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  
		  initRegionSummary();
		 
	 }
	
</script>


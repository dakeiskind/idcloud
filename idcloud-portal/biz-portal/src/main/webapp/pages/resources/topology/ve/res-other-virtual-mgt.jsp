<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='otherTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>主机</li>  
        <li>存储</li>
    </ul>
    <div id="otherTabsContent0">  
        <%@ include file="res-other-virtual-summary.jsp"%>
    </div>
    <div id="otherTabsContent1">
    	主机
    </div>
    <div id="otherTabsContent2">
    	存储
    </div>

</div>
<script type="text/javascript">
	 function initOtherTabs(id,type,virtualType){
		 
		  // 初始化数据中心tabs选项卡
		  $('#otherTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  initHostOtherSummary(virtualType);
		  
	 }
	 $('#otherTabs').on('selected', function (event) {
		  	  var pageIndex = event.args.item;
			  if(pageIndex == 1){
               		$("#otherTabsContent1").load(ctx+"/pages/resources/host/res-other-host-datagrid-index.jsp",function(){	
               	  	 	otherHostDatagridModel();
					});
               }else if(pageIndex == 2){
               		$("#otherTabsContent2").load(ctx+"/pages/resources/storage/res-storage-datagrid-index.jsp",function(){	
               			StorageDatagridModel();
					});
               }
     });
	
</script>


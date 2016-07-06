<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='storageTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>虚拟机</li>
    </ul>
    <div>  
        <%@ include file="res-storage-summary.jsp"%>
    </div>
    <div id="storageTabsContent1">
    </div>
</div>
<script type="text/javascript">
	 function initstorageTabs(id,type){
		  // 初始化数据中心tabs选项卡
		  $('#storageTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});

		  // 设置选中的sid和类型
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要画面js
		  initStorageSummary();
		  
		  $('#storageTabs').on('selected', function (event) {
			  	   var pageIndex = event.args.item;  
			  	   
			  	    if(pageIndex == 1){
	                	// 虚拟机
	                	$("#storageTabsContent1").load(ctx+"/pages/res/vm/res-vm-mgt.jsp",function(){	
	                		initVMTabs();
						});
	                }
          });
	 }
	
</script>


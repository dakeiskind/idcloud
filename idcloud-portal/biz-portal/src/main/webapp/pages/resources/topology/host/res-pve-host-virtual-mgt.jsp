<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='hostTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>存储</li>
        <li>分区</li>
    </ul>
    <div id='host_summary'>  
        <%@ include file="res-pve-host-virtual-summary.jsp"%>
    </div>
    <div id="hostTabsContent1">
    	
    </div>
    <div id="hostTabsContent2">
    	
    </div>

</div>
<script type="text/javascript">
	 function initPveHostTabs(id,type){
		  // 初始化数据中心tabs选项卡
		  $('#hostTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});

		  // 设置选中的sid和类型
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要画面js
		  initHostSummary();
	 }
	 
	 $('#hostTabs').on('selected', function (event) {
	  	  var pageIndex = event.args.item;  
	  	  if(pageIndex == 1){
	  	   		// 查询主机关联存储
	  		$("#hostTabsContent1").load(ctx+"/pages/resources/storage/res-storage-host-index.jsp",function(){	
          		//initVMTabs();
			});
          	
          }else if(pageIndex == 2){
        	// 虚拟机
            	$("#hostTabsContent2").load(ctx+"/pages/resources/vm/res-pve-vm-datagrid-index.jsp",function(){
        		    pveVMDatagridModel();
        	    });
          }

	  });
	
</script>


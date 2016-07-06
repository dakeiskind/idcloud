<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='pneTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>外部网络</li>
    </ul>
    <div id="pneTabsContent0">  
       <%@ include file="res-pne-pool-summary.jsp"%>
    </div>
    <div id="pneTabsContent1">
    
    </div>
    
</div>
<script type="text/javascript">
	 function initPNETabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#pneTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  initPoolPNESummary();
		  
	 }
	 $('#pneTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 1){
               	$("#pneTabsContent1").load(ctx+"/pages/resources/pool/pne/res-pne-datagrid-index.jsp",function(){	
	               	  // 初始化外部网络datagrid
	          		  initPneNetworkDatagrid();
				});
             }
     });
	
</script>


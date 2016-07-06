<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='pniTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>内部网络</li>
    </ul>
    <div id="pniTabsContent0">  
        <%@ include file="res-pni-pool-summary.jsp"%>
    </div>
    <div id="pniTabsContent1">
    
    </div>
    
</div>
<script type="text/javascript">
	 function initPNITabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#pniTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  initPoolPNISummary();
		  
		  //switchDatagridModel();
		  
	 }
	 $('#pniTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 1){
	               	$("#pniTabsContent1").load(ctx+"/pages/resources/pool/pni/res-pni-datagrid-index.jsp",function(){	
		               	  // 初始化网络datagrid
		          		  initPniNetworkDatagrid();
					});
             }
     });
	
</script>


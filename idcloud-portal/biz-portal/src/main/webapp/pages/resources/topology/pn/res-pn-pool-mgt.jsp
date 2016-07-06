<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='pnTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
        <li>VLAN池</li>
       <!--  <li>内部网络</li> -->
        <li>内部网络</li>
    </ul>
    <div id="pnTabsContent0">  
        <%@ include file="res-pn-pool-summary.jsp"%>
    </div>
    <div id="pnTabsContent1">  
        
    </div>
   <!--  <div id="pnTabsContent2">  
        
    </div> -->
    <div id="pnTabsContent2">  
        
    </div>
</div>
<script type="text/javascript">
	 function initPNTabs(id,type){
		 
		  // 初始化数据中心tabs选项卡
		  $('#pnTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和Type
		  resTopologySid = id;
		  resTopologyType = type;
		  
		  // 初始化概要页面
		  initPoolPNSummary();
		  
	 }
	 $('#pnTabs').on('selected', function (event) {
		  	 var pageIndex = event.args.item;
		  	 if(pageIndex == 1){
                 $("#pnTabsContent1").load(ctx+"/pages/resources/pool/pnv/res-pnv-datagrid-index.jsp",function(){	
               		initPnvDatagridModel();
				 });
             }
		  	 /* else if(pageIndex == 2){
            	 $("#pnTabsContent2").load(ctx+"/pages/resources/pool/pni/res-pni-datagrid-index.jsp",function(){	
	               	  // 初始化网络datagrid
	          		  initPneNetworkDatagrid();
				 });
             } */
		  	 else if(pageIndex == 2){
            	 $("#pnTabsContent2").load(ctx+"/pages/resources/pool/pni/res-pni-datagrid-index.jsp",function(){	
	               	  // 初始化内部网络datagrid
	          		  initPniNetworkDatagrid();
				 });
             }
     });
	
</script>


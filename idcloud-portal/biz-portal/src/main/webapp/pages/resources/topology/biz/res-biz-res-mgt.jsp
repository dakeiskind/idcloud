<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id='bizTabs' style="border:0px;">
    <ul>
        <li style="margin-left: 30px;">概要</li>
         <li>主机</li>
         <li>存储</li>
         <li>网络</li>
		<li>虚拟机</li>
		<%--<li>物理主机</li>--%>
    </ul>
    <div id="bizTabsContent0">  
        <%@ include file="res-biz-res-summary.jsp"%>
    </div>
     <div id="bizTabsContent1">
    	
     </div>
     <div id="bizTabsContent2">
    	
     </div>
     <div id="bizTabsContent3">
    	
     </div>
    <div id="bizTabsContent4">
    	
    </div>
    <%--<div id="bizTabsContent5">--%>
    	<%----%>
    <%--</div>--%>
</div>
<script type="text/javascript">
	 function initBizTabs(id,level,label,parentSid){
		  // 初始化数据中心tabs选项卡
		  $('#bizTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
		  
		  // 设置选中Tree的sid和level
		  resBizSid = id;
		  resBizLevel = level;
		  resBizType = label;
	 	  resParentBizSid = parentSid;

		  // 初始化概要页面
	 	 initBizSummary();
	 }
	 
	 $('#bizTabs').on('selected', function (event) {
	  	  var pageIndex = event.args.item;
 	  	  if(pageIndex == 1){
 	  		 $("#bizTabsContent1").load(ctx+"/pages/resources/biz/host/res-biz-host-datagrid-index.jsp",function(){
        		 	 bizHostDatagridModelInfo();
        	     });
           }else if(pageIndex == 2){
         	 $("#bizTabsContent2").load(ctx+"/pages/resources/biz/storage/res-biz-storage-datagrid-index.jsp",function(){
         		 bizStorageDatagridModel();
         	 });
           }else if(pageIndex == 3){
         	 $("#bizTabsContent3").load(ctx+"/pages/resources/biz/network/res-biz-network-datagrid-index.jsp",function(){
         		 bizNetworkDatagridModelInfo();
         	 });
           }else if(pageIndex == 4){
         	  $("#bizTabsContent4").load(ctx+"/pages/resources/biz/vm/res-biz-vm-datagrid-index.jsp",function(){
         		  bizVMDatagridModel();
         	  });
           }
		  if(pageIndex == 1){
	  		 $("#bizTabsContent4").load(ctx+"/pages/resources/biz/vm/res-biz-vm-datagrid-index.jsp",function(){
	  			bizVMDatagridModel();
       	     });
          }
//		  else if(pageIndex == 2){
//        	 $("#bizTabsContent5").load(ctx+"/pages/resources/biz/host/res-biz-physicalhost-datagrid-index.jsp",function(){
//        		 bizPhysicalHostDatagridModelInfo();
//        	 });
//          }
     });
	
</script>


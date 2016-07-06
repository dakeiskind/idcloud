<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<!-- vm -->
		<script type="text/javascript" src="${ctx}/js/resources/biz/vm/res-biz-datagrid-model.js"></script>
 		<script type="text/javascript" src="${ctx}/js/resources/biz/vm/res-biz-operation-vm-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/biz/vm/res-biz-vm-config-ip.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/biz/vm/res-biz-vm-detail-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/biz/vm/res-biz-nanotube-vm-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/biz/vm/res-biz-modify-vm-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-migrate-target-host.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-migrate-target-storage.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-migrate-select.js"></script>
		<!-- 主机 -->
		<script type="text/javascript" src="${ctx}/js/resources/biz/host/res-biz-host-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/biz/host/res-biz-physicalhost-datagrid-model.js"></script>
		
		<!-- 存储 -->
		<script type="text/javascript" src="${ctx}/js/resources/biz/storage/res-biz-storage-datagrid-model.js"></script>
		
		<!-- 网络 -->
		<script type="text/javascript" src="${ctx}/js/resources/biz/network/res-biz-network-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/biz/network/res-biz-network-mgtIp-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/biz/network/res-biz-remark-ip-model.js"></script>
		
		<style type="text/css">
			#containerBiz{
				width:99.9%;
				height:99%;
				margin:0px;
			}
    </style>  
</head>	
 <body>    
   <div id="containerBiz">
         <div id="mainSplitter" style="border:0px">
            <div class="splitter-panel">
                <div id='jqxTreeBiz'style="width:100%;height:100%;border:0px;overflow:hidden"></div>
            </div>
            <div class="listContent">
               
            </div>
         </div>
   </div>
   <%@ include file="network/res-biz-network-mgtIp-index.jsp"%>	
   <%@ include file="network/res-biz-remark-ip-index.jsp"%>
</body>
<script type="text/javascript">
         // 当前选中业务的sid
         var resBizSid;
         // 当前选中业务的级别
         var resBizLevel;
         
         var resBizType;
 		 var resParentBizSid;
 		 var resNetworkSid;
         
		 $(function(){
			 	// 初始化Splitter
			 	$('#mainSplitter').jqxSplitter({ width: "100%", height: "100%", theme:currentTheme, panels: [{ size: 200 ,max:300 ,min:100}] });
			    
			 	// 查询biz表
				setBizTreeValue();
			 	
	            $('#jqxTreeBiz').on('select', function (event) {
	                var args = event.args;
	                var item = $('#jqxTreeBiz').jqxTree('getItem', args.element); 
	     			
	                var parentItem = $('#jqxTreeBiz').jqxTree('getItem',item.parentElement);
 	     			var parentSid = parentItem ? parentItem.value : null;
	                
	     			$("#mainSplitter .listContent").load(ctx+"/pages/resources/topology/biz/res-biz-res-mgt.jsp",function(){
	     				initBizTabs(item.value,item.level,item.label,parentSid);
	                });
	            });
			    
		 });
		 
		 // 给左边的资源拓扑tree赋值
		 function setBizTreeValue(){
			 Core.AjaxRequest({
				 url : ws_url + "/rest/mgtObj/findAllMgtObj",
					type:'post',
					callback : function (data) {
		                var source ={
		                    datatype: "json",
		                    datafields: [
		                         { name: 'mgtObjSid' },
					             { name: 'parentId' },
					             { name: 'name' },
					             { name: 'level' },
					             { name: 'mgtObjIcon' },
					             { name: 'description' }
		                    ],
		                    id: 'bizSid',
		                    localdata: data
		                };
		                var dataAdapter = new $.jqx.dataAdapter(source);
		                dataAdapter.dataBind();
		                var records = dataAdapter.getRecordsHierarchy('mgtObjSid', 'parentId', 'items', 
		                		[{ name: 'name', map: 'label'},{ name: 'mgtObjSid', map: 'value'}, {name: 'level', map:'level'}, 
		                		 {name: 'parentId', map:'parentValue'},{name: 'mgtObjIcon', map:'icon'}]);
			            $('#jqxTreeBiz').jqxTree({source: records, height: '100%', width: '100%', theme : currentTheme});
			            $('#jqxTreeBiz').jqxTree('expandItem', $("#jqxTreeBiz").jqxTree("getItems")[0]);
			            $('#jqxTreeBiz').jqxTree('selectItem', $("#jqxTreeBiz").find('li:first')[0]);
					}
		 	    });
		 }

</script>
</html>
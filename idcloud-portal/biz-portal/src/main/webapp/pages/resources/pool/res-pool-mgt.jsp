<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<!-- Topology -->
		<script type="text/javascript" src="${ctx}/js/resources/regionDc/res-add-zone-dc-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/regionDc/res-edit-zone-dc-model.js"></script>
		<!-- pool -->
		<script type="text/javascript" src="${ctx}/js/resources/pool/res-rz-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pool/res-rz-edit-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pool/res-rz-datagrid-model.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/resources/pc/res-pc-cluster-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pc/res-pc-cluster-relation-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pc/res-pc-advance-config-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pc/res-pc-host-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pc/res-pc-relation-host-model.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/resources/pnv/res-pnv-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pnv/res-pnv-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pnv/res-pnv-edit-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pnv/res-pnv-mgtVlan-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pnv/res-pnv-relation-DVS-model.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/resources/pos/res-pos-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pos/res-pos-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pos/res-pos-edit-model.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/resources/pcdn/res-pcdn-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pcdn/res-pcdn-edit-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pcdn/res-pcdn-datagrid-model.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/resources/pni/res-pni-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pni/res-pni-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pni/res-pni-edit-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pni/res-pni-mgtIp-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pni/res-pni-remark-ip-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pni/res-pni-relation-host-model.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/resources/pne/res-pne-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pne/res-pne-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pne/res-pne-edit-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pne/res-pne-mgtIp-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/pne/res-pne-remark-ip-model.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/resources/ps/res-ps-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/ps/res-ps-relation-model.js"></script>
		<!-- host -->
		<script type="text/javascript" src="${ctx}/js/resources/host/res-host-datagrid-model.js"></script>
		<!-- vm -->
		<script type="text/javascript" src="${ctx}/js/resources/vm/res-vm-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-detail-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/res-vm-monitor-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-migrate-target-host.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-migrate-target-storage.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-config-ip.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-migrate-select.js"></script>
		<!-- storage -->
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-storage-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-storage-host-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/storage/res-storage-set-type-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-switch-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-physicalMonitor-info-mode.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-switch-info-model.js"></script>
		
		
		<style type="text/css">
			#containerPool{
				width:99.92%;
				height:99.8%;
				margin:0px;
			}
    	</style>  
</head>	
	
 <body>
      <div id="containerPool">
         <div id="mainSplitter" style="border:0px">
            <div class="splitter-panel">
                <div id='jqxTreePool'style="width:100%;height:100%;border:0px;overflow:hidden"></div>
            </div>
            <div class="listContent">
               
            </div>
         </div>
   </div>
   
   <%@ include file="pc/res-pc-cluster-relation-index.jsp"%>
   <%@ include file="pc/res-pc-advance-config-index.jsp"%>
   <%@ include file="pc/res-pc-relation-host-index.jsp"%>
   
   <%@ include file="rz/res-rz-add-index.jsp"%>
   <%@ include file="rz/res-rz-edit-index.jsp"%>
   <%@ include file="../regionDc/res-add-zone-dc-index.jsp"%>
   <%@ include file="../regionDc/res-edit-zone-dc-index.jsp"%>
   
   <%@ include file="pne/res-pne-add-index.jsp"%>
   <%@ include file="pne/res-pne-edit-index.jsp"%>
   <%@ include file="pne/res-pne-mgtIp-index.jsp"%>
   <%@ include file="pne/res-pne-remark-ip-index.jsp"%>
  
   
   <%@ include file="pos/res-pos-add-index.jsp"%> 
   <%@ include file="pos/res-pos-edit-index.jsp"%>
   <%@ include file="pcdn/res-pcdn-add-index.jsp"%>
   <%@ include file="pcdn/res-pcdn-edit-index.jsp"%>
   <%@ include file="../storage/res-storage-set-type-index.jsp"%>
   <%@ include file="../physical/ne/switch/res-ne-switch-info.jsp"%>
   
</body>
<script type="text/javascript">
		//当前选中Tree的sid
		var resTopologySid;
		// 当前选中Tree的类型
		var resTopologyType;
		// 当前选中的资源池sid
		var resourcePoolSid;
		
		$(function(){
			$('#mainSplitter').jqxSplitter({ width: "100%", height: "100%", theme:currentTheme, panels: [{ size: 250 ,max:300 ,min:100}] });
		    // 查询Topology表
			setPoolTreeValue();
			
            $('#jqxTreePool').on('select', function (event) {
                var args = event.args;
                var item = $('#jqxTreePool').jqxTree('getItem', args.element);  
                var attr = item.value.split(","); 
               
                if(attr[1] == "R"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/region/res-region-pool-mgt.jsp",function(){
                		initRegionTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "DC"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/dc/res-dc-pool-mgt.jsp",function(){
                		initDCTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "RZ"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/rz/res-rz-pool-mgt.jsp",function(){
                		initRZTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "PCX"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pc/res-pc-pool-mgt.jsp",function(){
                		initPCTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "PCVX"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pc/res-pc-pool-mgt.jsp",function(){
                		initPCTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "PCP"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pc/res-pc-pool-mgt.jsp",function(){
                		initPCTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "PCVP"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pc/res-pc-pool-mgt.jsp",function(){
                		initPCTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "PN"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pn/res-pn-pool-mgt.jsp",function(){
                		initPNTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "PP"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pc/res-pp-pool-mgt.jsp",function(){
                		initPPTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "PS"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/ps/res-ps-pool-mgt.jsp",function(){
                		initPSTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "PNV"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pnv/res-pnv-pool-mgt.jsp",function(){
                		initPNVTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "PNI"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pni/res-pni-pool-mgt.jsp",function(){
                		initPNITabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "PNE"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pne/res-pne-pool-mgt.jsp",function(){
                		initPNETabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "POS"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pos/res-pos-pool-mgt.jsp",function(){
                		initPOSTabs(attr[0],attr[1]);
                	});
                }else if(attr[1] == "CDN"){
                	$("#containerPool .listContent").load(ctx+"/pages/resources/topology/pcdn/res-pcdn-pool-mgt.jsp",function(){
                		initCDNTabs(attr[0],attr[1]);
                	});
                }

            });
	 });
		
		// 获取资源池拓扑结构Tree
		function setPoolTreeValue(){
			 Core.AjaxRequest({
		 			url : ws_url + "/rest/topologys/pool/tree",
		 			type:'post',
		 			params:{},
		 			callback : function (data) {
		 				initPoolTopologyTree(data);
		 			}
		 	    });
		 }
		
		// 初始化资源池Tree
		 function initPoolTopologyTree(data){
             var source ={
                 datatype: "json",
                 datafields: [
                     { name: 'resTopologySid' },
                     { name: 'parentTopologySid' },
                     { name: 'resTopologyName' },
                     { name: 'topologyIcon' },
                     { name: 'topologyValue' }
                 ],
                 id: 'topologySid',
                 localdata: data
             };
             var dataAdapter = new $.jqx.dataAdapter(source);
             dataAdapter.dataBind();
             var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'topologyValue', map: 'value'},{ name: 'topologyIcon', map: 'icon'}]);
             $('#jqxTreePool').jqxTree({ source: records, width: '99.7%',height:'100%',allowDrag:false});
             $('#jqxTreePool').jqxTree('selectItem', $("#jqxTreePool").find('li:first')[0]);
             $('#jqxTreePool').jqxTree('expandItem', $("#jqxTreePool").find('li')[1]);
	      }
		
</script>
</html>
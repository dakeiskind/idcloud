<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="relationDVSWindow">
       <div>关联DVS</div>
       <div style="position:relative;overflow:hidden">
            <div style="position:relative;width:100%;height:20px;">
            	<div style="position:absolute;left:3px;top:0px;width:100px;height:20px;line-height:20px;">未关联DVS：</div>
            	<div style="position:absolute;left:238px;top:0px;width:100px;height:20px;line-height:20px;">已关联DVS：</div>
            </div>
       		<div id="noRelationDVS" style="float:left;width:150px;height:250px;"></div>
       		<div style="float:left;width:60px;height:250px;margin-left:11px;">
       				<input id="relationBtn" onclick="operationRelationDvs('01')" style="margin-top:50px;margin-left:5px;cursor:pointer"  type="button" value="关联" />
       				<input id="unbundleBtn" onclick="operationRelationDvs('02')" style="margin-top:50px;margin-left:5px;cursor:pointer"  type="button" value="解绑" />
       		</div>
       		<div id="relationDVS" style="float:left;width:150px;height:250px;margin-left:11px;"></div>
       		
       		<div style="position:absolute;bottom:0px;width:99.5%;height:30px;text-align:right">
       			<input id="saveRelationBtn" onclick="saveRelationDvs()" type="button" value="确定" />&nbsp;&nbsp;
       			<input id="closeRelationBtn" type="button" value="关闭" />&nbsp;&nbsp;&nbsp;&nbsp;
       		</div>
       </div>
</div>

<script type="text/javascript">
  var dvs = new relationDVSGrid();
  dvs.initInput();
  dvs.initPopWindow();
  
  // 关联DVS
  function operationRelationDvs(type){
	  // 临时数组
	  var temporaryArray = new Array();
	  
	  if(type == '01'){
		  //关联操作，未关联DVS 至   已关联DVS 
		  var noRelationItems = $("#noRelationDVS").jqxListBox('getSelectedItems');
		  
		  if(noRelationItems.length > 0){
			  // 清除未关联数据 & 新增已关联数据
			  for(var i=0;i<noRelationItems.length;i++){
				  for(var j=0;j<noRelationData.length;j++){
					  if(noRelationItems[i].value == noRelationData[j].resVsSid){
						  temporaryArray.push(noRelationData[j]);
						  continue;
					  }
				  }
			  }
			  // 分配数据
			  allocationData(type,temporaryArray);
			  refreshListBox();
		  }else{
			  // 设置关联按钮不可用
			  $("#relationBtn").jqxButton({disabled:true});
		  }
		  
	  }else if(type == '02'){
		  //解绑操作，已关联DVS 至   未关联DVS
		  var relationItems = $("#relationDVS").jqxListBox('getSelectedItems');
		  
		  if(relationItems.length > 0){
			  // 清除未关联数据 & 新增已关联数据
			  for(var i=0;i<relationItems.length;i++){
				  for(var j=0;j<relationData.length;j++){
					  if(relationItems[i].value == relationData[j].resVsSid){
						  temporaryArray.push(relationData[j]);
						  continue;
					  }
				  }
			  }
		  
		   // 分配数据
		   allocationData(type,temporaryArray);
		   refreshListBox();
		  }else{
			  // 设置解绑按钮不可用
			  $("#unbundleBtn").jqxButton({disabled:true});
		  }
	  }
  }
  
  // 分配数据
  function allocationData(type,data){
	  if(type == '01'){
		  // 已关联DVS:直接添加数据;未关联DVS：删除解绑数据
		  // 先保存未关联数据
		  var cloneNoRelationData = noRelationData;
		  for(var i=0;i<data.length;i++){
			  relationData.push(data[i]);
			  for(var j=0;j<cloneNoRelationData.length;j++){
				  if(data[i].resVsSid == cloneNoRelationData[j].resVsSid){
					  deletedItemsByResVsSid(type,cloneNoRelationData[j].resVsSid);
					  break;
				  }
			  }
		  }
	  }else if(type == '02'){
		  // 未关联DVS:直接添加数据;已关联DVS：删除解绑数据
		  // 先保存已关联数据
		 var cloneRelationData = relationData;
		  for(var i=0;i<data.length;i++){
			  noRelationData.push(data[i]);
			  for(var j=0;j<cloneRelationData.length;j++){
				  if(data[i].resVsSid == cloneRelationData[j].resVsSid){
					  deletedItemsByResVsSid(type,cloneRelationData[j].resVsSid);
					  break;
				  }
			  }
		  }
	  }
  }
  
  // 根据resVsSid，删除数组某个元素
  
  function deletedItemsByResVsSid(type,sid){
	  if(type == '01'){
		  for(var j=0;j<noRelationData.length;j++){
			  if(sid == noRelationData[j].resVsSid){
				  noRelationData.splice(j,1);
				  break;
			  }
		  }
		  
	  }else if(type == '02'){
		  for(var j=0;j<relationData.length;j++){
			  if(sid == relationData[j].resVsSid){
				  relationData.splice(j,1);
				  break;
			  }
		  }
	  }
  }
  
  
  // 刷新ListBox
  function refreshListBox(){
	  // 未关联
	    var source ={
              datatype: "json",
              datafields: [
                  { name: 'resVsName' },
                  { name: 'resVsSid' }
              ],
              id: 'resVsSid',
              localdata:noRelationData
         };
		var dataAdapter = new $.jqx.dataAdapter(source);
		$("#noRelationDVS").jqxListBox({ source: dataAdapter, multiple: true,displayMember: "resVsName", valueMember: "resVsSid", width: 150, height: 250,theme:currentTheme});
  	  // 已关联
		var source ={
                datatype: "json",
                datafields: [
                    { name: 'resVsName' },
                    { name: 'resVsSid' }
                ],
                id: 'resVsSid',
                localdata:relationData
           };
			var dataAdapter = new $.jqx.dataAdapter(source);
			$("#relationDVS").jqxListBox({ source: dataAdapter, multiple: true,displayMember: "resVsName", valueMember: "resVsSid", width: 150, height: 250,theme:currentTheme});
		
  }
  
  
</script>

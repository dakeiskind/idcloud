<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
      <!-- 已挂载主机存储 -->
      <div style="width:100%;height:12px;"></div>
      <div	style="width:98%;margin-left:1%;height:98%;">
      	  <div id='mountedHostStorageDatagrid' style="width:100%;"></div> 
      </div>
       
      <!-- 可挂载存储 -->
       <div id="addHostStorageWindow">
            <div>添加存储</div>
            <div  style="overflow: hidden;">
            	 <div class="customPanel" style="width:100%;height:207px;">
            		 	<div class="title">可挂载存储</div>
            		 	<div id='addHostCanStorageDatagrid' style="width:100%;border-top:0px;border-right:0px;border-left:0px;"></div> 
                 </div>
                  <div style="width:100%;padding-top:10px;text-align:right;">
                      <input style="margin-right: 5px;" type="button" onclick="addHostCanStorageSubmit()" id="addHostCanStorageSave" value="保存" />
		              <input id="addHostCanStorageCancel" type="button" value="取消" />
                </div>
            </div>
       </div> 
  <script type="text/javascript">
      var hostStorage = new addMountedHostStorageModel();
      hostStorage.initMountDatagrid();
      hostStorage.initPopWindow();
      hostStorage.searchMountedStorageData(resTopologySid);
  		
	
// 	  // 清除datagrid的选择项
// 	  function clearAddHostStorageDatagrid(){
// 			$('#addHostStorageDatagrid').jqxGrid('clearselection');
// 	  }
	  
// 	  // 清除datagrid的选择项
// 	  function clearMountedStorageDatagrid(){
// 			$('#mountedStorageDatagrid').jqxGrid('clearselection');
// 	  }
  </script>
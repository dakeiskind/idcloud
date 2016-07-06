<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
      <!-- 已挂载存储 -->
      <div id="mountStorageHosDatagridtWindow">
            <div>挂载存储</div>
            <div id="" style="overflow: hidden;">
            	 <div class="customPanel" style="width:100%;height:250px;">
            		 	<div class="title">已挂载存储</div>
            		 	<div id='mountedStorageDatagrid' style="width:100%;border-top:0px;border-right:0px;border-left:0px;"></div> 
                 </div>
                  <div style="width:100%;padding-top:10px;text-align:right;">
		              <input id="mountStorageCancel"  onclick="clearMountedStorageDatagrid()" type="button" value="取消" />
                </div>
            </div>
       </div>
       
       <!-- 可挂载存储 -->
       <div id="addHostStorageInfoWindow">
            <div>添加存储</div>
            <div  style="overflow: hidden;">
            	 <div class="customPanel" style="width:100%;height:208px;">
            		 	<div class="title">可挂载存储</div>
            		 	<div id='addHostStorageDatagrid' style="width:100%;border-top:0px;border-right:0px;border-left:0px;"></div> 
                 </div>
                  <div style="width:100%;padding-top:10px;text-align:right;">
                  
                      <input style="margin-right: 5px;" onclick='addHostStorageSubmit()' type="button" id="addHostStorageSave" value="保存" />
		              <input id="addHostStorageCancel" onclick="clearAddHostStorageDatagrid()"  type="button" value="取消" />
                </div>
            </div>
       </div> 
  <script type="text/javascript">
	  var mounthost = new mountStorageHostModel();
	  mounthost.initMountDatagrid();
	  mounthost.initPopWindow();
	  
	// 清除datagrid的选择项
	  function clearAddHostStorageDatagrid(){
			$('#addHostStorageDatagrid').jqxGrid('clearselection');
	  }
	  
	  // 清除datagrid的选择项
	  function clearMountedStorageDatagrid(){
			$('#mountedStorageDatagrid').jqxGrid('clearselection');
	  }
  </script>
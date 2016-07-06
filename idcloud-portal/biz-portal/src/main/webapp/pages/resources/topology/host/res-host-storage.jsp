<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
      <!-- 已挂载主机存储 -->
      
      <div id="findHostStorageWindow">
     	<div>设置主机存储</div>
     	<div  style="overflow: hidden;">
     	<div style="width:100%;height:15px;padding:5px 0px 10px 0px;">
		       <table border="0" height="90%" cellspacing="0" cellpadding="2">
		        	<tr>
		        		<td><a id='addMountStorage'  onclick='popAddHostStorageWindow()'>&nbsp;&nbsp;<i class='icon-plus'></i>关联存储&nbsp;&nbsp;</a></td>
	        			<td><a id='editMountStorage' onclick='removeHostStorage()' style='margin-left:-1px' >&nbsp;&nbsp;<i class='icon-pencil'></i>卸载存储&nbsp;&nbsp;</a></td>
		        	</tr>
		        </table>
		 </div>
   		 <div class="customPanel" style="width:100%;height:180px;">
     		 <div id='findHostStorageDatagrid' style="width:100%;border-top:0px;border-right:0px;border-left:0px;"></div> 
         </div>
         <div style="width:100%;padding-top:5px;text-align:right;">
                 <!--   <input style="margin-right: 5px;" type="button" id="findHostStorageSave" value="确定" /> -->
             <input id="findHostStorageCancel" type="button" value="关闭" />
         </div>
     	</div>
      </div>
       
      <!-- 可挂载存储 -->
       <div id="addHostStorageWindow">
            <div>可挂载存储</div>
            <div  style="overflow: hidden;">
            	 <div class="customPanel" style="width:100%;height:300px;">
            		 	<!-- <div class="title">可挂载存储</div> -->
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
	       hostStorage.initHostStorageDatagrid();
	       hostStorage.initPopWindow();
	       //hostStorage.searchMountedStorageData();	
		</script>
 
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  <!-- 添加主机入集群 -->
  <div id="addHostToClusterWindow">
          <div>关联主机</div>
          <!-- <div style="position:relative;"> -->
          <div style="overflow: hidden;">
          <div style="width:100%;height:15px;padding:10px 0px 10px 0px;">
		       <table border="0" height="100%" cellspacing="0" cellpadding="2">
		        	<tr>
		        		<td><a onclick="popAddX86HostWindow()" id="add-new-host-to-cluster-button"><i class='icons-blue icon-plus'></i>添加新主机到集群&nbsp;</a></td>
	        			<td><a onclick="popRemoveHostOutClusterWindow()" id="remove-host-out-cluster-button"><i class='icons-blue icon-cancel'></i>移除主机&nbsp;</a></td>
		        	</tr>
		        </table>
		 </div>
		  <div style="width:100%;height:500px;overflow-y:auto;">
          		<div id='findHostAddToCluster' style="width:100%;"></div> 
          </div>
          		<div style="position:absolute;bottom:10px;width:99%;padding-top:10px;text-align:right;">
            		  <input style="margin-right: 5px;" type="button"  onclick="addHostToClusterSubmits()" id="addToClusterSave" value="保存" />
		              <input style="margin-right: 5px;" id="addToClusterCancel" type="button" value="取消" />
                </div>
          </div>
  </div>   
  

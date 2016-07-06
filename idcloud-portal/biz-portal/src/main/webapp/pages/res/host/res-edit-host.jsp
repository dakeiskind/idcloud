<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  <!-- 编辑主机 -->
	  <div id="editHostWindow">
            <div>编辑主机</div>
            <div id="editHostForm" style="overflow: hidden;">
            	<div class="customPanel" style="width:100%;height:125px;">
            	    <div class="title">基本信息</div>
		            <div>
		            	<input type="hidden" data-name="resSid" id="resSidHost"/>
		            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" width="100"><font style="color:red">*</font>主机名称:</td>
		                        <td align="left" ><input type="text" data-name="hostName" id="edit-hostName"/></td>
		                        <td align="right" width="100">主机全名:</td>
		                        <td align="left" ><input type="text" data-name="fullName" id="edit-fullName"/></td>
		                        <td align="right" width="100">主机型号:</td>
		                        <td align="left"><input type="text" data-name="hostType" id="edit-hostType"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right">集群名称:</td>
		                        <td align="left"><input type="text" data-name="clusterName" id="edit-clusterName"/></td>
		                        <td align="right">机柜编码:</td>
		                        <td align="left"><input type="text" data-name="rackNumber" id="edit-rackNumber"/></td>
		                        <td align="right">机笼Enclosure:</td>
		                        <td align="left"><input type="text" data-name="cageEnclosure" id="edit-cageEnclosure"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right" width="80">UUID:</td>
		                        <td align="left"><input type="text" data-name="uuid" id="edit-uuid"/></td>
		                        <td align="right">制造商:</td>
		                        <td align="left"><input type="text" data-name="vendor" id="edit-vendor"/></td>
		                        <td align="right">所属数据中心:</td>
		                        <td align="left"><input type="text" data-name="dataCenter" id="edit-dataCenter"/></td>
		                    </tr>
		                  
		                </table>
	                </div>
            	</div>
            	
            	<div class="customPanel" style="width:100%;height:125px;margin-top:10px;">
            	    <div class="title">资源信息</div>
		            <div>
		            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" width="100"><font style="color:red">*</font>主机IP:</td>
		                        <td align="left"><input type="text" data-name="hostIp" id="edit-hostIp"/></td>
		                        <td align="right" width="100"><font style="color:red">*</font>内存大小(MB):</td>
		                        <td align="left"><input type="text" data-name="memorySize" id="edit-memorySize"/></td>
		                        <td align="right" width="100"><font style="color:red">*</font>CPU个数:</td>
		                        <td align="left"><input type="text" data-name="cpuNumber" id="edit-cpuNumber"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>CPU核数:</td>
		                        <td align="left"><input type="text" data-name="cpuCores" id="edit-cpuCores"/></td>
		                        <td align="right">CPU主频(GHz):</td>
		                        <td align="left"><input type="text" data-name="cpuGhz" id="edit-cpuGhz"/></td>
		                        <td align="right">CPU类型:</td>
		                        <td align="left"><input type="text" data-name="cpuType" id="edit-cpuType"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>虚拟平台类型:</td>
		                        <td align="left">
		                           <div data-name="virtualPlatformType" id="edit-virtualPlatformType"></div>
		                        </td>
		                        <td align="right"><font style="color:red">*</font>操作系统:</td>
		                        <td align="left" colspan="3">
		                           <div data-name="hostOsType" id="edit-hostOsType"></div>
		                        </td>
		                    </tr>
		                </table>
	                </div>
            	</div>
            	<div class="customPanel" style="width:100%;height:80px;margin-top:10px;">
            	    <div class="title">监控信息</div>
		            <div>
		            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" height="50px" width="100">物理主机监控:</td>
		                        <td align="left" width="320"><input id="getMonitorNode" onclick="getMonitorNode()" type="button" value="获取监控节点" /></td>
		                        <td align="left">(<font id="monitorText"></font>)</td>
		                     </tr>
		                </table>
	                </div>
            	</div>
                <div style="width:100%;padding-top:10px;text-align:right">
            		  <input style="margin-right: 5px;" onclick='editHostSubmit()' type="button" id="edithostSave" value="保存" />
		              <input id="edithostCancel" type="button" value="取消" />
                </div>
            </div>
       </div> 
         
  <script type="text/javascript">
	    var edithost = new editHostModel();
	    edithost.initPopWindow();
	    edithost.initInput();
	    edithost.initValidator();
  </script>
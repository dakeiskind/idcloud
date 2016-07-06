<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
       <!-- 主机发现部署 -->
       <div id="hostDiscoveryDeploymentWindow">
            <div>已发现主机列表</div>
            <div  style="overflow: hidden;">
            	<div id='hostDiscoveryDeploymentDatagrid' style="width:100%;"></div> 
            </div>
       </div>  	  	
    
        <!-- 发现主机的详情 -->
       <div id="discoveryDeploymentDetailWindow">
            <div>主机详情</div>
            <div  style="overflow: hidden;">
            	<table border="0" width="100%" cellspacing="0" cellpadding="5">
            		<tr>
            			<td align="right" width=90>主机名称:</td>
            			<td><span id="hostName"></span></td>
            			<td align="right"  width=80>MAC地址:</td>
            			<td><span id="macAddress"></span></td>
            		</tr>
            		<tr>
            			<td align="right">节点名称:</td>
            			<td><span id="nodeHostName"></span></td>
            			<td align="right">操作系统:</td>
            			<td><span id="osname"></span></td>
            		</tr>
            		<tr>
            			<td align="right">ip地址:</td>
            			<td><span id="ipaddress"></span></td>
            			<td align="right">厂商:</td>
            			<td><span id="manufacturer"></span></td>
            		</tr>
            		<tr>
            			<td align="right">序列号:</td>
            			<td><span id="serialnumber"></span></td>
            			<td align="right">UUID:</td>
            			<td><span id="uuid"></span></td>
            		</tr>
            		<tr>
            			<td align="right">硬件类型:</td>
            			<td><span id="hardwaremodel"></span></td>
            			<td align="right">主机状态:</td>
            			<td><span id="status"></span></td>
            		</tr>
            		<tr>
            			<td align="right">CPU个数:</td>
            			<td><span id="physicalprocessorcount"></span></td>
            			<td align="right">产品名称:</td>
            			<td><span id="productname"></span></td>
            		</tr>
            		<tr>
            			<td align="right">CPU总核数:</td>
            			<td><span id="processorcount"></span></td>
            			<td align="right">网卡:</td>
            			<td><span id="interfaces"></span></td>
            		</tr>
            		<tr>
            			<td align="right" valign="top">CPU核数详情:</td>
            			<td><div id="cpuDetail" valign="top"></div></td>
            			<td align="right" valign="top">网卡详情:</td>
            			<td valign="top"><div id="interfaceDetail"></div></td>
            		</tr>
            	</table>
            </div>
       </div>
       <!-- 主机部署系统 -->
       <div id="DeploymentSystemWindow">
            <div>部署系统</div>
            <div style="overflow: hidden;">
	            <table border="0" width="100%" cellspacing="0" cellpadding="5">
	            		<tr>
	            			<td align="right" height="60px">部署系统:</td>
	            			<td><div id="hostOsType"></div></td>
	            			<td><input onclick='deploymentSystemSubmit()' type="button" id="deploymentSystemSave" value="确定" /></td>
	            		</tr>
	            </table>
            </div>
       </div>
       
    <!-- 加入平台 -->
    <div id="addToPlatformWindow">
            <div>加入平台</div>
            <div id="addToPlatformForm" style="overflow: hidden;">
            	<div class="customPanel" style="width:100%;height:125px;">
            	    <div class="title">基本信息</div>
		            <div>
		            	<input type="hidden" data-name="parentTopologySid" id="join-parentTopologySid"/>
		            	<input type="hidden" data-name="deployNodeId" id="join-deployNodeId"/>
		            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" width="100"><font style="color:red">*</font>主机名称:</td>
		                        <td align="left" ><input type="text" data-name="hostName" id="platform-hostName"/></td>
		                        <td align="right" width="100">主机全名:</td>
		                        <td align="left" ><input type="text" data-name="fullName" id="platform-fullName"/></td>
		                        <td align="right" width="100">主机型号:</td>
		                        <td align="left"><input type="text" data-name="hostType" id="platform-hostType"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right">集群名称:</td>
		                        <td align="left"><input type="text" data-name="clusterName" id="platform-clusterName"/></td>
		                        <td align="right">机柜编码:</td>
		                        <td align="left"><input type="text" data-name="rackNumber" id="platform-rackNumber"/></td>
		                        <td align="right">机笼Enclosure:</td>
		                        <td align="left"><input type="text" data-name="cageEnclosure" id="platform-cageEnclosure"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right" width="80">UUID:</td>
		                        <td align="left"><input type="text" data-name="uuid" id="platform-uuid"/></td>
		                        <td align="right">制造商:</td>
		                        <td align="left"><input type="text" data-name="vendor" id="platform-vendor"/></td>
		                        <td align="right">所属数据中心:</td>
		                        <td align="left"><input type="text" data-name="dataCenter" id="platform-dataCenter"/></td>
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
		                        <td align="left"><input type="text" data-name="hostIp" id="platform-hostIp"/></td>
		                        <td align="right" width="100"><font style="color:red">*</font>内存大小(MB):</td>
		                        <td align="left"><input type="text" data-name="memorySize" id="platform-memorySize"/></td>
		                        <td align="right" width="100"><font style="color:red">*</font>CPU个数:</td>
		                        <td align="left"><input type="text" data-name="cpuNumber" id="platform-cpuNumber"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>CPU核数:</td>
		                        <td align="left"><input type="text" data-name="cpuCores" id="platform-cpuCores"/></td>
		                        <td align="right">CPU主频(GHz):</td>
		                        <td align="left"><input type="text" data-name="cpuGhz" id="platform-cpuGhz"/></td>
		                        <td align="right">CPU类型:</td>
		                        <td align="left"><input type="text" data-name="cpuType" id="platform-cpuType"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>虚拟平台类型:</td>
		                        <td align="left">
		                           <div data-name="virtualPlatformType" id="platform-virtualPlatformType"></div>
		                        </td>
		                        <td align="right"><font style="color:red">*</font>操作系统:</td>
		                        <td align="left" colspan="3">
		                           <div data-name="hostOsType" id="platform-hostOsType"></div>
		                        </td>
		                    </tr>
		                </table>
	                </div>
            	</div>
                <div style="width:100%;padding-top:10px;text-align:right">
            		  <input style="margin-right: 5px;" onclick='platformHostSubmit()' type="button" id="platformhostSave" value="保存" />
		              <input id="platformhostCancel" type="button" value="取消" />
                </div>
            </div>
       </div> 
         
  <script type="text/javascript">
	  var findHost = new findAndDeploymentHostModel(); 
	  findHost.initValidator();
	  findHost.initFindHostDatagrid();
	  findHost.initPopWindow();
  </script>
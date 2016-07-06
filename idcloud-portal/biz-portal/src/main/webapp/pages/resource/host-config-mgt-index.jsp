<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;主机名称：</td>
            			<td><input type="text" id="search-host-name" />&nbsp;&nbsp;</td>
            			<td align="right">&nbsp;&nbsp;管理状态：</td>
            			<td>
            				<div id="search-host-mgt-status"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;使用状态：</td>
            			<td>
            				<div id="search-host-usage-status"></div>
            			</td>
            			<td><input type="button" value="查询" data-bind='click:searchHostConfigMgt' id='search-host-button' /></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='hostConfigMgtdatagrid' style="width:100%;height:450px;"></div> 
    </div>  
    
    <!-- 新增主机 -->
    <div id="addHostWindow">
            <div>新增主机</div>
            <div id="addHostForm" style="overflow: hidden;">
            	<div class="customPanel" style="width:100%;height:125px;">
            	    <div class="title">基本信息</div>
		            <div>
		            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" width="100"><font style="color:red">*</font>主机名称:</td>
		                        <td align="left" ><input type="text" data-name="hostName" id="add-hostName"/></td>
		                        <td align="right" width="100">主机全名:</td>
		                        <td align="left" ><input type="text" data-name="fullName" id="add-fullName"/></td>
		                        <td align="right" width="100">主机型号:</td>
		                        <td align="left"><input type="text" data-name="hostType" id="add-hostType"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right">集群名称:</td>
		                        <td align="left"><input type="text" data-name="clusterName" id="add-clusterName"/></td>
		                        <td align="right">机柜编码:</td>
		                        <td align="left"><input type="text" data-name="rackNumber" id="add-rackNumber"/></td>
		                        <td align="right">机笼Enclosure:</td>
		                        <td align="left"><input type="text" data-name="cageEnclosure" id="add-cageEnclosure"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right" width="80">UUID:</td>
		                        <td align="left"><input type="text" data-name="uuid" id="add-uuid"/></td>
		                        <td align="right">制造商:</td>
		                        <td align="left"><input type="text" data-name="vendor" id="add-vendor"/></td>
		                        <td align="right">所属数据中心:</td>
		                        <td align="left"><input type="text" data-name="dataCenter" id="add-dataCenter"/></td>
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
		                        <td align="left"><input type="text" data-name="hostIp" id="add-hostIp"/></td>
		                        <td align="right" width="100"><font style="color:red">*</font>内存大小(MB):</td>
		                        <td align="left"><input type="text" data-name="memorySize" id="add-memorySize"/></td>
		                        <td align="right" width="100"><font style="color:red">*</font>CPU个数:</td>
		                        <td align="left"><input type="text" data-name="cpuNumber" id="add-cpuNumber"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>CPU核数:</td>
		                        <td align="left"><input type="text" data-name="cpuCores" id="add-cpuCores"/></td>
		                        <td align="right">CPU主频(GHz):</td>
		                        <td align="left"><input type="text" data-name="cpuGhz" id="add-cpuGhz"/></td>
		                        <td align="right">CPU类型:</td>
		                        <td align="left"><input type="text" data-name="cpuType" id="add-cpuType"/></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>虚拟平台类型:</td>
		                        <td align="left">
		                           <div data-name="virtualPlatformType" id="add-virtualPlatformType"></div>
		                        </td>
		                        <td align="right"><font style="color:red">*</font>操作系统:</td>
		                        <td align="left" colspan="3">
		                           <div data-name="hostOsType" id="add-hostOsType"></div>
		                        </td>
		                    </tr>
		                </table>
	                </div>
            	</div>
                <div style="width:100%;padding-top:10px;text-align:right">
            		  <input style="margin-right: 5px;" data-bind='click:addHostSubmit' type="button" id="addhostSave" value="保存" />
		              <input id="addhostCancel" type="button" value="取消" />
                </div>
            </div>
       </div>  
	  
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
		                        <td align="left" width="320"><input id="getMonitorNode" data-bind="click:getMonitorNode" type="button" value="获取监控节点" /></td>
		                        <td align="left">(<font id="monitorText"></font>)</td>
		                     </tr>
		                </table>
	                </div>
            	</div>
                <div style="width:100%;padding-top:10px;text-align:right">
            		  <input style="margin-right: 5px;" data-bind='click:editHostSubmit' type="button" id="edithostSave" value="保存" />
		              <input id="edithostCancel" type="button" value="取消" />
                </div>
            </div>
       </div>
	  	
	   <!-- 查看主机详情 -->
	   <div id="detailHostWindow">
            <div>主机详情</div>
            <div id="detailHostForm" style="overflow: hidden;">
            	<div class="customPanel" style="width:100%;height:125px;">
            	    <div class="title">基本信息</div>
		            <div>
		            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" width="100"><font style="color:red">*</font>主机名称:</td>
		                        <td align="left" ><span id="detail-hostName"></span></td>
		                        <td align="right" width="100">主机全名:</td>
		                        <td align="left" ><span id="detail-fullName"></span></td>
		                        <td align="right" width="100">主机型号:</td>
		                        <td align="left"><span id="detail-hostType"></span></td>
		                    </tr>
		                    <tr>
		                        <td align="right">集群名称:</td>
		                        <td align="left"><span id="detail-clusterName"></span></td>
		                        <td align="right">机柜编码:</td>
		                        <td align="left"><span id="detail-rackNumber"></span></td>
		                        <td align="right">机笼Enclosure:</td>
		                        <td align="left"><span id="detail-cageEnclosure"></span></td>
		                    </tr>
		                    <tr>
		                        <td align="right" width="80">UUID:</td>
		                        <td align="left"><span id="detail-uuid"></span></td>
		                        <td align="right">制造商:</td>
		                        <td align="left"><span id="detail-vendor"></span></td>
		                        <td align="right">所属数据中心:</td>
		                        <td align="left"><span id="detail-dataCenter"></span></td>
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
		                        <td align="left"><span id="detail-hostIp"></span></td>
		                        <td align="right" width="100"><font style="color:red">*</font>内存大小(MB):</td>
		                        <td align="left"><span id="detail-memorySize"></span></td>
		                        <td align="right" width="100"><font style="color:red">*</font>CPU个数:</td>
		                        <td align="left"><span id="detail-cpuNumber"></span></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>CPU核数:</td>
		                        <td align="left"><span id="detail-cpuCores"></span></td>
		                        <td align="right">CPU主频(GHz):</td>
		                        <td align="left"><span id="detail-cpuGhz"></span></td>
		                        <td align="right">CPU类型:</td>
		                        <td align="left"><span id="detail-cpuType"></span></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>虚拟平台类型:</td>
		                        <td align="left">
		                           <span id="detail-virtualPlatformType"></span>
		                        </td>
		                        <td align="right"><font style="color:red">*</font>操作系统:</td>
		                        <td align="left" colspan="3">
		                           <span id="detail-hostOsType"></span>
		                        </td>
		                    </tr>
		                </table>
	                </div>
            	</div>
                <div style="width:100%;padding-top:10px;text-align:right">
		              <input id="detailhostCancel" type="button" value="取消" />
                </div>
            </div>
       </div>
       
       <!-- 查看监控信息详情 -->
       <div id="monitorHostWindow">
            <div>监控信息</div>
            <div id="detailHostForm" style="overflow: hidden;">
            	<div style="width:100%;height:100%;">
            		 <div class="customPanel" style="float:left;width:340px;height:170px;">
            		 	<div class="title">基本信息</div>
            		 	<div></div>
            		 </div>
            		 <div class="customPanel" style="float:left;width:340px;height:170px;margin-left:5px;">
            		 	<div class="title">实时信息</div>
            		 	<div></div>
            		 </div>
            		 <div class="customPanel" style="float:left;width:340px;height:170px;margin-top:5px;">
            		 	<div class="title">CPU使用率历史趋势</div>
            		 	<div></div>
            		 </div>
            		 <div class="customPanel" style="float:left;width:340px;height:170px;margin-left:5px;margin-top:5px;">
            		 	<div class="title">内存使用率历史趋势</div>
            		 	<div></div>
            		 </div>
            	</div>
            </div>
       </div> 
       <!-- 挂载存储 -->
      <div id="mountStorageHostWindow">
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
       
       <!-- 给主机添加存储 -->
       <div id="addHostStorageWindow">
            <div>添加存储</div>
            <div  style="overflow: hidden;">
            	 <div class="customPanel" style="width:100%;height:175px;">
            		 	<div class="title">可挂载存储</div>
            		 	<div id='addHostStorageDatagrid' style="width:100%;border-top:0px;border-right:0px;border-left:0px;"></div> 
                 </div>
                  <div style="width:100%;padding-top:10px;text-align:right;">
                  
                      <input style="margin-right: 5px;" data-bind='click:addHostStorageSubmit' type="button" id="addHostStorageSave" value="保存" />
		              <input id="addHostStorageCancel" onclick="clearAddHostStorageDatagrid()"  type="button" value="取消" />
                </div>
            </div>
       </div>
       
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
	            			<td><input data-bind='click:deploymentSystemSubmit' type="button" id="deploymentSystemSave" value="确定" /></td>
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
            		  <input style="margin-right: 5px;" data-bind='click:platformHostSubmit' type="button" id="platformhostSave" value="保存" />
		              <input id="platformhostCancel" type="button" value="取消" />
                </div>
            </div>
       </div>
    
      <script type="text/javascript">
      		function initHostConfigJs(){
	       		 // 初始化sys-index页面model
	   			 var hostconfig = new hostConfigModel();
	       		 // 初始化页面组件
	   			 hostconfig.initInput();
	       		 // 初始化组件验证规则
	   			 hostconfig.initValidator();
	       		 // 初始化弹出框
	   			 hostconfig.initPopWindow();
	       		 // 初始化datagrid
	   			 hostconfig.initHostDatagrid();
	       		 // 为datagrid赋值
	   			 hostconfig.searchHostConfigInfo();
	   	
	   			 return hostconfig;
      		}
      		
      	  // 清除datagrid的选择项
      	  function clearAddHostStorageDatagrid(){
				$('#addHostStorageDatagrid').jqxGrid('clearselection');
      	  }
      	  
      	  // 清除datagrid的选择项
      	  function clearMountedStorageDatagrid(){
				$('#mountedStorageDatagrid').jqxGrid('clearselection');
      	  }
      </script>
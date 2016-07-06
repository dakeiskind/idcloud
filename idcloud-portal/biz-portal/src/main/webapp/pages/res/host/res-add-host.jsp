<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="addHostWindow">
            <div>新增主机</div>
            <div id="addHostForm" style="overflow: hidden;">
            	<input type="hidden" data-name="parentTopologySid" id="add-host-parentTopologySid"/>
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
		                           <div data-name="virtualPlatformType" id="add-host-virtualPlatformType"></div>
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
            		  <input style="margin-right: 5px;" onclick="javascript:addHostSubmit()" type="button" id="addhostSave" value="保存" />
		              <input id="addhostCancel" type="button" value="取消" />
                </div>
            </div>
       </div>
         
  <script type="text/javascript">
	    var addhost = new addHostModel();
		addhost.initPopWindow();
		addhost.initValidator();
       

  </script>
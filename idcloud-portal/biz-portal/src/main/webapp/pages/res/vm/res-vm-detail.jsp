<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style type="text/css">

</style>

  <div id="vmDetailWindow">
            <div>虚拟机详情</div>
            <div id="vmDetailForm" style="overflow: hidden;">
            	<div class="customPanel" style="width:100%;height:125px;">
            	    <div class="title">&nbsp;&nbsp;基本信息</div>
		            <div>
		            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" width="100">虚拟机名称:</td>
		                        <td align="left" >
		                        	<span id="allocateVmName"></span>
		                        </td>
		                        <td align="right" width="100">虚机状态:</td>
		                        <td align="left" >
		                        	<span id="statusName"></span>
		                        </td>
		                        <td align="right" width="100">操作系统:</td>
		                        <td align="left">
		                        	<span id="osTypeName"></span>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right" width="100">所属租户:</td>
		                        <td align="left" >
		                        	<span id="tenantName"></span>
		                        </td>
		                        <td align="right" width="100">资源池级别:</td>
		                        <td align="left" >
		                        	<span id="perfLevelName"></span>
		                        </td>
		                        <td align="right" width="100">所有者:</td>
		                        <td align="left">
		                        	<span id="realName"></span>
		                        </td>
		                    </tr>
		                     <tr>
		                        <td align="right" width="100">主机IP:</td>
		                        <td align="left" >
		                        	<span id="hostName1"></span>
		                        </td>
		                        <td align="right" width="100">虚拟机IP:</td>
		                        <td align="left" colspan="3">
		                        	<span id="ip"></span>
		                        </td>
		                    </tr>
		                     <tr>
		                        <td align="right" width="100">CPU(核):</td>
		                        <td align="left" >
		                        	<span id="cpuCores"></span>
		                        </td>
		                        <td align="right" width="100">内存(MB):</td>
		                        <td align="left" colspan="3">
		                        	<span id="memorySize"></span>
		                        </td>
		                    </tr>
		                </table>
	                </div>
            	</div>
            	
            	<div class="customPanel" style="width:100%;height:185px;margin-top:10px;">
            	    <div class="title">&nbsp;&nbsp;硬盘信息</div>
		            <div>
		            	<div id='vmStorageDatagrid' style="width:98%;margin-left:1%;margin-top:5px;height:200px;"></div> 
	                </div>
            	</div>
            	
                <div style="width:100%;padding-top:10px;text-align:right">
		              <input id="vmDetailCancel" type="button" value="取消" />
                </div>
            </div>
       </div>

<script type="text/javascript">
   var vmDetail = new vmDetailModel();
   vmDetail.initPopWindow();
   vmDetail.initVmStorageDatagrid();
</script>


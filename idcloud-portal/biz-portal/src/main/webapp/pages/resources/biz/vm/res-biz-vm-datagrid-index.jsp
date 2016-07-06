<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="0">
            	<tr>
<!--             		<td align="left" width="65px" nowrap="nowrap">项目名称：</td> -->
<!--            		<td align="left" width="105px"><input type="text" id="search-biz-name-vm" /></td> -->
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;虚拟机名称：</td>
           			<td><input type="text" id="search-biz-vm-name" /></td>
           			<td align="right">&nbsp;&nbsp;IP地址：</td>
           			<td><input type="text" id="search-biz-vm-ip" /></td>
           			<td align="right">&nbsp;&nbsp;状态：</td>
           			<td><div id="search-biz-vm-status"></div></td>
           			<td align="left">&nbsp;&nbsp;<a onclick="searchBizVm()" id="search-biz-vm-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
           			<td align="right" id="nanotubeButton"></td>
           			<td align="right" id="bizVmExportButton" width="150px"></td>
           			<td align="right" id="bizCloudExportButton" width="115px"></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='bizVmdatagrid' style="width:100%;height:450px;"></div> 
     	<div style="height: 30px;"><p style="margin:0px;margin-top:7px;font-size:14px;color: green;">
	    	※列表中<u style="color: #FB4242;">红色</u>代表已到期，<u style="color: rgb(205, 205, 56);">黄色</u>代表即将到期</p>
	    </div>
    </div>
    
    <!-- 选择导出字段 -->
    <div id="chooseFilesWindow" style="display: none;">
    	<div class="title">&nbsp;选择导出的文件中需要显示的字段</div>
    	<div>
	    	<table id="fieldTable" border="0" cellspacing="2" cellpadding="0" style="width: 100%;height: 80%;">
	    		<tr>
		    		<td style="width: 25%;"><div id="checkBox1">项目名称</div></td>
		    		<td style="width: 25%;"><div id="checkBox2">归属部门</div></td>
		    		<td style="width: 25%;"><div id="checkBox3">开通时间</div></td>
		    		<td style="width: 25%;"><div id="checkBox4">虚拟机名称</div></td>
	    		</tr>
	    		<tr>
	    			<td><div id="checkBox5">状态(开机/关机)</div></td>
		    		<td><div id="checkBox6">CPU(核)</div></td>
		    		<td><div id="checkBox7">CPU使用率(%)</div></td>
		    		<td><div id="checkBox8">CPU使用率峰值(%)</div></td>
	    		</tr>
	    		<tr>
		    		<td><div id="checkBox9">内存(G)</div></td>
		    		<td><div id="checkBox10">内存使用率(%)</div></td>
		    		<td><div id="checkBox11">内存使用率峰值(%)</div></td>
		    		<td><div id="checkBox12">存储(G)</div></td>
	    		</tr>
	    		<tr>
		    		<td><div id="checkBox13">存储使用率(%)</div></td>
		    		<td><div id="checkBox14">存储使用率峰值(%)</div></td>
		    		<td><div id="checkBox15">均值流量(Mbps)</div></td>
		    		<td><div id="checkBox16">峰值流量(Mbps)</div></td>
	    		</tr>
	    		<tr>
		    		<td><div id="checkBox17">IP地址（多IP用逗号分开）</div></td>
		    		<td><div id="checkBox18">SNMP监控(是/否)</div></td>
	    		</tr>
	    	</table>
	    	<div style=" width: 99.5%; height: 25px; margin-top:5px; text-align: right">
				<input type="button" onclick="exportExcel()"
					id="exportExcelSubmit" value="导出" /> <input style="margin-right: 6px;"
					id="exportBizVmCancel" class="exportCancel" type="button" value="取消" />
			</div>
		</div>
    </div>
    
    <%@ include file="res-biz-modify-vm-index.jsp"%>
    <%@ include file="res-biz-vm-config-ip.jsp"%>
    <%@ include file="res-biz-vm-detail.jsp"%>
    <%@ include file="res-biz-nanotube-vm-index.jsp"%>
<%--     <%@ include file="../../vm/res-vm-migrate-select.jsp"%> --%>
</div>

<script type="text/javascript">

$(function(){
	if(10 == resBizSid){
		var str1 = "<a onclick='popNanotubeVmWindow()' id='search-biz-nanotube-vm-button'><i class='icons-blue icon-download'></i>&nbsp;纳管虚拟机</a>";
		$("#nanotubeButton").html(str1);
		var str2 = "<a onclick='exportBizVMRel()' id='export-biz-vm-button'><i class='icons-blue icon-export-1'></i>项目资源统计表导出&nbsp;</a>";
		$("#bizVmExportButton").html(str2);
// 		var str3 = "<a onclick='exportVmMonitor()' id='export-vm-monitor-button'><i class='icons-blue icon-export-1'></i>业务云报表导出&nbsp;</a>";
// 		$("#bizCloudExportButton").html(str3);
	}
});

function bizVMDatagridModel(){
	var bizVm = new virtualBizVMDatagridModel();
	 // 初始化页面组件
	 bizVm.initVMInput();	 
	 // 初始化datagrid
	 bizVm.initVMDatagrid();
	 // 初始化window
	 bizVm.initPopWindow();
	
}

</script>

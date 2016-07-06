<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;告警等级：</td>
            			<td>
            				<div id="search-alarm-level"></div>
            			</td>
            			<td align="right" nowrap="nowrap">告警类型：</td>
            			<td>
            				<div id="search-alarm-type"></div>
            			</td>
            			<td align="right" nowrap="nowrap">告警状态：</td>
            			<td>
            				<div id="search-alarm-status"></div>
            			</td>
            			<td align="right" nowrap="nowrap">告警时间：</td>
            			<td> <div id='alarmFromTime'></div></td>
            			<td>至</td>
            			<td><div id='alarmToTime'></div></td>
            			<td><a onClick="searchAlarmConsole()" id="search-alarm-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='alarmConsoledatagrid' style="width:100%;height:450px;"></div> 
    </div>
    
   	<%@ include file="alarm-info-confirm.jsp"%>
    
       <script type="text/javascript">
       		function initAlarmConsolePageJs(){
	       		 // 初始化sys-index页面model
	   			 var resalarmemodel = new resAlarmeModel();
	       		 // 初始化页面组件
	   			 resalarmemodel.initInput();
	       		 // 初始化弹出框
// 	   			 resalarmemodel.initPopWindow();
	       		 // 初始化组件验证规则
// 	   			 resalarmemodel.initValidator();
	       		 // 初始化datagrid  
	   			 resalarmemodel.initAlarmConsoledatagrid();
	       		 // 为datagrid赋值
	   			 resalarmemodel.searchAlarmConsoleInfo();
	   	
	   			return resalarmemodel;
       		}
       </script>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/alarm/alarm-bind-model.js"></script>
		<%--<script type="text/javascript" src="${ctx}/js/alarm/alarm-console-model.js"></script>--%>
		<script type="text/javascript" src="${ctx}/js/alarm/alarm-rule-model.js"></script>

		<title></title>
		<style type="text/css">
			#containerAlarm{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
			}
		</style>
	</head>
   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;告警指标：</td>
            			<td>
            				<div id="search-alarm-rule-kpi"></div>
            			</td>
             			<td align="right" nowrap="nowrap">告警类型：</td>
            			<td>
            				<div id="search-alarm-rule-type"></div>
            			</td>
            			<td align="right" nowrap="nowrap">告警等级：</td>
            			<td>
            				<div id="search-alarm-rule-level"></div>
            			</td>
            			<td><a data-bind="click:searchAlarmRule" id="search-alarm-rule-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='alarmRuledatagrid' style="width:100%;height:450px;"></div> 
    </div>
    
    <div id="addAlarmRuleInfoWindow">
            <div>新增告警规则</div>
            <div id="addAlarmRuleInfoForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>告警标题:</td>
                        <td align="left">
                        	<input type="text" data-name="title" id="add-title"/>
                       	</td>
                       	<td align="right">告警指标:</td>
                        <td align="left">
                        	<div data-name="alarmKpi" id="add-alarmKpi"></div>
                       	</td>
                    </tr>
                    <tr>
                        <td align="right">告警级别:</td>
                        <td align="left">
                        	<div data-name="alarmLevel" id="add-alarmLevel"></div>
                       	</td>
                       	<td align="right">告警类型:</td>
                        <td align="left">
                        	<div data-name="alarmType" id="add-alarmType"></div>
                       	</td>
                    </tr>
                	<tr>
                        <td align="right">运算符:</td>
                        <td align="left">
                        	<div data-name="operator" id="add-operator"></div>
                       	</td>
                       	<td align="right">触发规则:</td>
                        <td align="left">
                        	<input type="text" data-name="accumulateCount" id="add-accumulateCount"/>
                       	</td>
                    </tr>
                    <tr>
                        <td align="right">告警阀值:</td>
                        <td align="left">
                        	<input type="text" data-name="alarmThreshold" id="add-alarmThreshold"/>
                       	</td>
						<td align="left" valign="top" colspan="2">
							<font style="color:green">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;规则为{#总次数}{运算符}{出现次数},例如：#10>=9</font>
						</td>
                    </tr>
                    <tr>   	
                       	<td align="right" valign="top">告警内容:</td>
                        <td align="left" colspan="3">
                        	<textarea data-name="content" id="add-content"></textarea>
                       	</td>
                    </tr>
                    <tr>
                    	<td colspan="4" align="right">
                    		<input style="margin-right: 5px;" data-bind="click:ruleAlarmInfoSubmit" id="ruleInfoSave" type="button" value="保存" />
                    		<input id="ruleInfoCancel" type="button" value="取消" />
                    	</td>
                    </tr>
                </table>
            </div>
       </div> 
       
       <div id="editAlarmRuleInfoWindow">
            <div>编辑告警规则</div>
            <div id="editAlarmRuleInfoForm" style="overflow: hidden;">
            	<input type="hidden" data-name="alarmRuleSid" id="editAlarmRuleSid"/>
            	<input type="hidden" data-name="checkOptr" id="editAlarmRuleCheckOptr"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>告警标题:</td>
                        <td align="left">
                        	<input type="text" data-name="title" id="edit-title"/>
                       	</td>
                       	<td align="right">告警指标:</td>
                        <td align="left">
                        	<div data-name="alarmKpi" id="edit-alarmKpi"></div>
                       	</td>
                    </tr>
                    <tr>
                        <td align="right">告警级别:</td>
                        <td align="left">
                        	<div data-name="alarmLevel" id="edit-alarmLevel"></div>
                       	</td>
                       	<td align="right">告警类型:</td>
                        <td align="left">
                        	<div data-name="alarmType" id="edit-alarmType"></div>
                       	</td>
                    </tr>
                	<tr>
                        <td align="right">运算符:</td>
                        <td align="left">
                        	<div data-name="operator" id="edit-operator"></div>
                       	</td>
                       	<td align="right">触发规则:</td>
                        <td align="left">
                        	<input type="text"  placeholder="#10>=9"  data-name="accumulateCount" id="edit-accumulateCount"/>
                       	</td>
                    </tr>
                    <tr>
                        <td align="right">告警阀值:</td>
                        <td align="left">
                        	<input type="text" data-name="alarmThreshold" id="edit-alarmThreshold"/>
                       	</td>
                       	<td align="left" valign="top" colspan="2">
                       		<font style="color:green">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;规则为{#总次数}{运算符}{出现次数},例如：#10>=9</font>
                       	</td>
                    </tr>
                    <tr>   	
                       	<td align="right" valign="top">告警内容:</td>
                        <td align="left" colspan="3">
                        	<textarea data-name="content" id="edit-content"></textarea>
                       	</td>
                    </tr>
                    <tr>
                    	<td colspan="4" align="right">
                    		<input style="margin-right: 5px;" data-bind="click:editRuleAlarmInfoSubmit" id="editRuleInfoSave" type="button" value="保存" />
                    		<input id="editRuleInfoCancel" type="button" value="取消" />
                    	</td>
                    </tr>
                </table>
            </div>
       </div> 
    
       <script type="text/javascript">
		   $(function(){

			   // 初始化告警规则js
			   var rule =	initAlarmRulePageJs();
			   var bindmodel = new alarmBindModel(null,rule);
			   //将model和view绑定
			   ko.applyBindings(bindmodel);
		   });


       		function initAlarmRulePageJs(){
	       		 // 初始化sys-index页面model
	   			 var alarmRule = new alarmRuleModel();
	       		 // 初始化页面组件
	   			 alarmRule.initInput();
	       		 // 初始化弹出框
	   			 alarmRule.initPopWindow();
	       		 // 初始化组件验证规则
	   			 alarmRule.initValidator();
	       		 // 初始化datagrid  
	   			 alarmRule.initAlarmRuledatagrid();
	       		 // 为datagrid赋值
	   			 alarmRule.searchAlarmRuleInfo();
	   	
	   			return alarmRule;
       		}
       </script>
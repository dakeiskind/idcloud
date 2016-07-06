<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="defineAlarmInfoWindow">
            <div>确定告警信息</div>
            <div id="defineAlarmInfoForm" style="overflow: hidden;">
            	<input type="hidden" data-name="alarmDataSid" id="defineConsoleAlarmSid"/>
            	<input type="hidden" data-name="status" id="status" value="02"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>告警确认人:</td>
                        <td align="left">
                        	<input type="text" data-name="confirmUser" id="confirmUser"/>
                       	</td>
                    </tr>
                    <tr>
                    	<td align="right"><font style="color:red">*</font>告警确认时间:</td>
                        <td align="left">
                        	<div data-name="confirmTime" id='alarmDefineTime'></div>
                        </td>
                    </tr>
                    <tr>
                    	<td width="100px" align="right" valign="top">告警确认内容:</td>
                        <td>
                        	<textarea id="alarmDefineContent" data-name="confirmContent"></textarea>
                        </td>
                    </tr>
                    <tr>
                    	<td colspan="2" align="right">
                    		<input style="margin-right: 5px;" onClick="DefineAlarmInfoSubmit()" id="consoleInfoSave" type="button" value="保存" />
                    		<input id="consoleInfoCancel" type="button" value="取消" />
                    	</td>
                    </tr>
                </table>
            </div>
       </div> 
         
  <script type="text/javascript">
	    var confirmAlarmInfo = new confirmAlarmInfoModel();
	    confirmAlarmInfo.initPopWindow();
	    confirmAlarmInfo.initValidator();
  </script>
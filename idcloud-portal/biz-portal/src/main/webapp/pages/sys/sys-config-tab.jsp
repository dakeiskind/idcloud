<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
		<style type="text/css">
			table{
				font-size: 12px;
				font-family: '微软雅黑', 'Microsoft Yahei', 'segoe ui', arial, sans-serif;
				color: #767676;
			}
		</style>
   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;配置名称：</td>
            			<td><input type="text" id="search-sys-config" />&nbsp;&nbsp;</td>
            			<td align="right" nowrap="nowrap">配置key：</td>
            			<td><input type="text" id="search-config-key"/>&nbsp;&nbsp;</td>
            			<input type="hidden" id="search-config-type"/>
            			<td><a onclick="searchSysConfig()" id="search-sys-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='systemdatagrid' style="width:100%;"></div> 
    </div>   
    
    <div id="editConfigWindow">
            <div>编辑系统配置</div>
            <div id="sysConfigForm" style="overflow: hidden;">
            	<input type="hidden" data-name="configSid" id="configSid"/>
            	<input type="hidden" data-name="configType" id="configType"/>
                <table border="0" width="100%" cellspacing="10" cellpadding="0">
                    <tr>
                        <td align="right">配置名称:</td>
                        <td align="left"><span id="config-name"></span></td>
                        <td align="right">配置key:</td>
                        <td align="left"><span id="config-key"></span></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>配置值:
                        	<input type="hidden" data-name="configValue" id="config-value"/>
                        </td>
                        <td align="left" colspan="3" id="valueTd">
                        </td>
                    </tr>
                 	<tr>
                        <td align="right" valign="top">配置描述:</td>
                        <td align="left" colspan="3" valign="top" height="50px"><span id="config-description"></span></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4"> 
                        <input style="margin-right: 5px;" onclick='sysConfigSave()' type="button" id="sysConfigSave" value="保存" />
                        <input id="sysConfigCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div> 
    <script type="text/javascript">
    </script>
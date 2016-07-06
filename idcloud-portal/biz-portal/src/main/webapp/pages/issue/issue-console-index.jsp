<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div style="width: 100%; height: 50px; padding: 10px 0px 10px 0px;">
	<form id="form-search">
		<table border="0" height="100%" style="font-size:12px;" cellspacing="0" cellpadding="2">
			<tr>
				<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;问题等级：</td>
				<td>
					<div id="search-issue-priority"></div>
				</td>
				<td align="right" nowrap="nowrap">问题类型：</td>
				<td>
					<div id="search-issue-type"></div>
				</td>
				<td align="right" nowrap="nowrap">问题状态：</td>
				<td>
					<div id="search-issue-status"></div>
				</td>
				<td align="right" nowrap="nowrap">提问者：</td>
				<td><input type="text" id="search-created-by" style="width: 150px;" /></td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;问题创建时间：</td>
				<td>
					<div id='createdDtFromDate'></div>
				</td>
				<td>至</td>
				<td><div id='createdDtToDate'></div></td>
				<td><a data-bind="click:searchIssueConsole"
					id="search-issue-button" class='icons-blue icon-search-4'
					style="display: inline; white-space: nowrap;">查询&nbsp;</a></td>
				<td><a id="search-reset-button"
					class="icons-blue icon-cancel-5 jqx-rc-all jqx-rc-all-metro jqx-button jqx-button-metro jqx-widget jqx-widget-metro jqx-fill-state-normal jqx-fill-state-normal-metro"
					style="display: inline; white-space: nowrap; width: 50px; height: 25px;">清除日期条件&nbsp;</a></td>
			</tr>
		</table>
	</form>
</div>

<div style="width: 98%; height: 80%; margin-left: 1%;">
	<div id='issueConsoledatagrid' style="width: 100%; height: 450px;"></div>
</div>

<div id="defineIssueInfoWindow">
	<div>回复问题</div>
	<div id="defineIssueInfoForm" style="overflow: hidden;">
		<input type="hidden" data-name="issueSid" id="issueSid" />
		<%--<input type="hidden" data-name="issueStatus" id="issueStatus" value="02" />--%>
		<input type="hidden" id="issueStatus">
		<table border="0" width="100%" style="font-size: 12px;">
			<tr style="line-height: 24px;">
				<td style="width: 10%;text-align: left;padding-left:2%;">问题标题:</td>
				<td style="width: 42%;text-align: left" id="issueTitle"></td>
				<td style="width: 8%;text-align: left">问题级别:</td>
				<td style="width: 42%;text-align: left" id="issuePriorityName"></td>
			</tr>
			<tr style="line-height: 24px;">
				<td style="width: 10%;text-align: left;padding-left:2%;">问题类型:</td>
				<td style="width: 42%;text-align: left" id="issueTypeName"></td>
				<td style="width: 8%;text-align: left">问题状态:</td>
				<td style="width: 42%;text-align: left" id="issueStatusName"></td>
			</tr>
			<tr style="line-height: 24px;">
				<td style="width: 10%;text-align: left;padding-left:2%;">问题描述:</td>
				<td colspan="3" id="issueDesc"></td>
			</tr>
			<tr style="line-height: 24px;">
				<td style="width: 10%;text-align: left;padding-left:2%;">回复记录:</td>
				<td colspan="3">
					<div style="width:640px;height: 240px;overflow-y:scroll;border: solid 1px #EEEEEE" id="issueReply"></div>
				</td>
			</tr>
			<tr style="line-height: 24px;">
				<td style="width: 10%;text-align: left;padding-left:2%;">追加回复:</td>
				<td colspan="3">
					<textarea id="issueComment" data-name="content" maxLength="256" rows="3" cols="60"></textarea>
				</td>
			</tr>
			<tr style="line-height: 30px;">
				<td colspan="4" style="text-align: right;padding-right: 16px;">
					<input type="button" data-bind="click:DefineIssueInfoSubmit" id="consoleInfoSave" value="追加">&nbsp;
					<input type="button" id="consoleInfoCancel" value="取消">
				</td>
			</tr>
		</table>
	</div>
</div>

<script type="text/javascript">
	function initIssueConsolePageJs() {
		// 初始化sys-index页面model
		var issueConsole = new issueConsoleModel();
		// 初始化页面组件
		issueConsole.initInput();
		return issueConsole;
	}
</script>
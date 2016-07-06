<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/level/admin-level-mgt.js"></script>
		<style type="text/css">
			table{
				font-size: 12px;
				font-family: '微软雅黑', 'Microsoft Yahei', 'segoe ui', arial, sans-serif;
				color: #767676;
			}
		</style>
	</head>

	<body>

	<%--查询--%>
	<div style="width:100%;height:30px;padding:5px 0px 5px 0px;">
		<table border="0" height="100%" cellspacing="0" cellpadding="2">
			<tr>
				<td align="right" style="font-size:12px;">&nbsp;&nbsp;&nbsp;&nbsp;账户等级：</td>
				<td><input type="text" id="account-level" />&nbsp;&nbsp;</td>
				<td><a data-bind="click:searchAdminLevelInfo" id="search-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
			</tr>
		</table>
	</div>

	<%--grid--%>
	<div style="width:98%;height:80%;margin-left:1%;">
		<div id='accountLevelGrid' style="width:100%;height:450px;"></div>
	</div>

	<!-- 添加账户等级弹窗 -->
	<div class="jqxwindow" id="addLevelWindow" >
		<div>
			<div>添加账户等级</div>
		</div>
		<div class="jqxwindowbody" id="addLevelForm" >
			<table>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:red">*</font>账户等级:</td>
					<td><input type="text" id="add-level" /></td>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:red">*</font>折扣（%):</td>
					<td><input type="text" id="add-discount" /></td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:red">*</font>等级满足额度:</td>
					<td><input type="text" id="add-level-limit" /></td>
					<%--<td align="right" nowrap="nowrap" style="display: none;">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="module.admin.accountLevelMgt.add.priority" />:</td>--%>
					<%--<td><div  id='add-prior' style="display: none;"></div></td>--%>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;是否允许预渲染:</td>
					<td><div id="add-isAllowPrerender"></div></td>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;是否允许生成视频小样:</td>
					<td><div  id='add-isAllowCreateMovie'></div></td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;是否允许场景分析:</td>
					<td><div  id='add-isAllowSceneAnalysis'></div></td>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;是否允许生成缩略图:</td>
					<td><div  id='add-isAllowCreateThumbnail'></div></td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;最大预渲染帧数:</td>
					<td><div id="add-maxAllowedFrameCount" ></div></td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;等级描述:</td>
					<td colspan="3"><textarea id="add-level-description"></textarea></td>
				</tr>
				<tr>
					<td align="right" colspan="4">
						<input type="Button"  class="button_02" value="添加" id='saveAddLevelBtn' data-bind='click: addLevelSubmit'/>
						<input type="Button" class="button_02"  value="返回" id='cancelAddLevelBtn' />
					</td>
				</tr>
			</table>
		</div>
	</div>

	<!-- 编辑账户等级弹窗 -->
	<div class="jqxwindow" id="editLevelWindow" >
		<div>
			<div>编辑帐户等级</div>
		</div>
		<div class="jqxwindowbody" id="editLevelForm" >
			<input type="hidden" id="edit-sid"/>
			<table>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:red">*</font>账户等级:</td>
					<td><input type="text" id="edit-level" /></td>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:red">*</font>折扣（%):</td>
					<td><input type="text" id="edit-discount" /></td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:red">*</font>等级满足额度:</td>
					<td><input type="text" id="edit-level-limit" /></td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;是否允许预渲染:</td>
					<td><div id="edit-isAllowPrerender"></div></td>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;是否允许生成视频小样:</td>
					<td><div  id='edit-isAllowCreateMovie'></div></td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;是否允许场景分析:</td>
					<td><div  id='edit-isAllowSceneAnalysis'></div></td>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;是否允许生成缩略图:</td>
					<td><div  id='edit-isAllowCreateThumbnail'></div></td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;最大预渲染帧数:</td>
					<td><div id="edit-maxAllowedFrameCount" ></div></td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;等级描述:</td>
					<td colspan="3"><textarea id="edit-level-description"></textarea></td>
				</tr>
				<tr>
					<td align="right" colspan="4">
						<input type="Button"  class="button_02" value="修改" id='saveEditLevelBtn' data-bind='click: editLevelSubmit'/>
						<input type="Button" class="button_02"  value="返回" id='cancelEditLevelBtn' />
					</td>
				</tr>
			</table>
		</div>
	</div>
	</body>
</html>

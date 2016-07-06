<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/pages/common/taglibs.jsp"%>
	<%@ include file="/pages/common/resources.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
	<script type="text/javascript" src="${ctx}/js/service/service-catalog-model.js"></script>
	<style type="text/css">
		html,body{
			height:99.5%
		}
	</style>
</head>
<body>
	<div id="serviceCatalog" style="width:99.5%;height:99.8%;padding:0px;margin-left:0.3%;margin-top:0.3%;min-width: 1000px;">
		<div id='jqxExpanderCatalog' style="display: inline; float: left;width: 20%;height:100%;">
			<div>服务目录</div>
			<div >
				<div style="border: none;" id='jqxTreeCatalog'></div>
			</div>
		</div>
		<div id='jqxTabsCatalog' style="display: inline; float: left; margin-left: 3px;">
			<ul>
				<li style="font-size: 14px">服务目录</li>
			</ul>
			<div>
				<div style="width: 100%; padding: 10px 0px 10px 0px;">
					<p style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
						<span id="box">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color: green;font-size: 12px;">※在此展示所选服务目录下的服务目录信息，并提供服务目录新增、修改，删除等操作！</font></span>
					</p>
				</div>
				<div style="width: 100%;">
					<div id='jqxgridCatalog' style="margin-left: 1%"></div>
				</div>
				
				<div id="addCatalogWindow" style="display:none">
			     	<div>添加服务目录</div>
			     	<div id="addCatalogForm" style="overflow: hidden;">
			         <table border="0" width="100%" cellspacing="5" cellpadding="0">
			             <tr>
			                 <td align="right"><font style="color:red">*</font>目录名称:</td>
			                 <td align="left"><input type="text" data-name="catalogName" id="add-catalogName"/></td>
			             </tr>
			             <tr>
							 <td align="right">目录图片:</td>
			                 <td align="left" ><input type="text" data-name="imagePath" id="add-imagePath"/></td>
			             </tr>
			             <tr>             
			                 <td align="right">目录描述:</td>
			                 <td align="left" colspan="3"><textarea data-name="description" id="add-description"></textarea></td>
			             </tr>
			             <tr>
			                 <td align="right" colspan="4">
			                 <input style="margin-right: 5px;" onclick='addCatalogInfoSubmit()' type="button" id="saveAddCatalog" value="保存" />
			                 <input id="cancelAddCatalog" type="button" value="取消" /></td>
			             </tr>
			         </table>
	     			</div>
				</div>
				
				<div id="editCatalogWindow" style="display:none">
			     	<div>编辑服务目录</div>
			     	<div id="editCatalogForm" style="overflow: hidden;">
			     	<input type="hidden" data-name="catalogSid" id="catalogSid"/>
			     	
			         <table border="0" width="100%" cellspacing="5" cellpadding="0">
			             <tr>
			                 <td align="right"><font style="color:red">*</font>目录名称:</td>
			                 <td align="left"><input type="text" data-name="catalogName" id="edit-catalogName"/></td>
			             </tr>
			             <tr>
							 <td align="right">目录图片:</td>
			                 <td align="left" ><input type="text" data-name="imagePath" id="edit-imagePath"/></td>
			             </tr>
			             <tr>             
			                 <td align="right">目录描述:</td>
			                 <td align="left" colspan="3"><textarea data-name="description" id="edit-description"></textarea></td>
			             </tr>
			             <tr>
			                 <td align="right" colspan="4">
			                 <input style="margin-right: 5px;" onclick='editCatalogInfoSubmit()' type="button" id="saveEditCatalog" value="保存" />
			                 <input id="cancelEditCatalog" type="button" value="取消" /></td>
			             </tr>
			         </table>
	     			</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	// 初始化服务实例-index页面model
	var model = new serviceCatalogModel();
	model.initServiceCatalogTree();
	model.initServiceCatalogTempPopWindow();
	model.searchServiceCatalogContent();
	// 初始化组件验证规则
	model.initValidator();
</script>
</html>

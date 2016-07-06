<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
  <%@ include file="/pages/common/taglibs.jsp"%>
  <%@ include file="/pages/common/resources.jsp"%>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=8" />
  <script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
  <script type="text/javascript" src="${ctx}/js/sys/site-msg-mgt.js"></script>
  <style type="text/css">
    table{
      font-size: 12px;
      font-family: '微软雅黑', 'Microsoft Yahei', 'segoe ui', arial, sans-serif;
      color: #767676;
    }
  </style>
</head>
<body>
<div style="width:100%;height:30px;padding:5px 0px 5px 0px;">
  <table  border="0" height="100%" cellspacing="0" cellpadding="2">
    <tr>
      <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;消息标题：</td>
      <td><input type="text" id="search-msg-title">&nbsp;&nbsp;</td>
      <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;消息类型：</td>
      <td><div id="search-msg-type"></div></td>
      <td><a data-bind="click: searchSiteMsg" id="searchBtn"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
    </tr>
  </table>
</div>
<div style="width:99%;height:90%;margin-left:0.5%;overflow-y:auto;">
  <div id='jqxgridSiteMsg' style="width:100%;height:450px;"></div>
</div>

<div id="winAddSiteMsg" >
  <div>
    添加消息
  </div>
  <div id="winFormAddSiteMsg" style="overflow: auto;">
    <table border="0" width="100%" cellspacing="5" cellpadding="0">
      <tr>
        <td align="right"><font style="color:red">*</font>消息标题:</td>
        <td align="left" colspan="3"><input type="text" id="add-msg-title" /></td>
      </tr>
      <tr>
        <td align="right">消息类型:</td>
        <td align="left" colspan="3"><div id="add-msg-type"></div></td>
      </tr>
      <tr>
        <td align="right"><font style="color:red">*</font>消息内容:</td>
        <td align="left" colspan="3"><textarea id="add-msg-content"></textarea></td>
      </tr>
      <tr>
        <td align="right" colspan="4">
          <input type="button" value='发送所有用户' id='saveAddSiteMsgBtn' data-bind='click: addSiteMsgSubmit' />
          <input type="button" value='返回' id='cancelAddSiteMsgBtn' />
        </td>
      </tr>
    </table>
  </div>
</div>

</body>
</html>
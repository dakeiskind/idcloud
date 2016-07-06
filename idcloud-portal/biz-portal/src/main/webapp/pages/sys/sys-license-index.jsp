<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/sys-license-model.js"></script>
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
          
    </div>
     <div id="registerLicenseWindow">
            <div>注册和激活</div>
            <div id="registerLicenseForm" style="overflow: hidden;">
            	<!-- <input type="hidden" data-name="userSids" id="passwdUserSids"/> -->
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>License 序列号:</td>
                        <td align="left"><input style="width:400px;"type="text" id="register-license" class="text-input" /></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="2">
                        	<br />
	                        <input style="margin-right: 5px;" type="button" onclick='license_submit()' id="licenseSave" value="注册" />
	                        <input id="licenseCancel" type="button" value="关闭" />
                        </td>
                    </tr>
                </table>
            </div>
       </div>
    
    <div style="width:99%;height:90%;margin-left:0.5%;"> 
     	<div id='licensedatagrid' style="width:100%;height:450px;"></div> 
    </div>    
  </body>     
</html>          
       <script type="text/javascript">
    		// 初始化
			var licensemodel = new licenseModel(); 
				licensemodel.initInput();
				licensemodel.initPopWindow();
       		 	// 初始化datagrid
   			 	licensemodel.initLicenseDatagrid();
				// 为datagrid赋值
   			 	licensemodel.searchLicenseInfo();
    		
       		
       </script>
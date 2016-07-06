<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <%@ include file="/pages/common/taglibs.jsp"%>
	<%@ include file="/pages/common/resources.jsp"%>
    <script type="text/javascript" src="${ctx}/js/webConsole/res-webconsole.js"></script>
   	<style type="text/css">
   	#test1 {
		background: url("../../images/icon/webconsole.png");
		background-size:100% 100%;
	    background-repeat:no-repeat;
	    background-attachment:fixed;
	    background-position:center;
	    background-position:top;
	}
   	#test {
		background: -webkit-gradient(linear, 0 0, 0 100%, from(#CCCCCC), to(#666666)); 
		background:-moz-linear-gradient(top,#CCCCCC,#666666); 
		background: -ms-linear-gradient(#CCCCCC 0%,#666666 100%);
        filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#CCCCCC',endColorstr='#666666',grandientType=1);
        -ms-filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#CCCCCC',endColorstr='#666666',grandientType=1);
	}
   </style>
    
    <%
		String resVm = request.getParameter("vmrcconsole");
   		String[] resMessage = resVm.split(",");
    	String vmName = resMessage[1];
    	String resVmSid = resMessage[0];
	%>
	<title><%=vmName%> 控制台</title>
  </head>
<body>
<div >
	<div id="test" style="width:100%;margin-top:0px;text-align:right; height: 5%;padding-top:5px">
		<select id="view" name="position" style="width:150px;">
			 <option value="0" selected>请选择系统分辨率... </option>
			<!--  <option value="1">1920 x 1080</option>
			 <option value="2">1680 x 1050</option> -->
			 <option value="1">1672 x 1080</option>
			 <option value="2">1600 x 1200 </option>
			 <option value="3">1400 x 1050 </option>
			 <option value="4">1400 x 900 </option>
			 <option value="5">1366 x 768 </option>
			 <option value="6">1280 x 1024 </option>
			 <option value="7">1280 x 960 </option>
			 <option value="8">1280 x 768</option>
			 <option value="9">1280 x 720</option>
			 <option value="10">1152 x 864 </option>
			 <option value="11">1024 x 768</option>
			 <option value="12">800 x 600</option>
		</select>
	   <input class="icons-blue" type="button" onclick="setFullscreen()" value="全屏"/>&nbsp;&nbsp;
	   <input style='margin-left:-1px' class="icons-blue" type="button" onclick="sendCAD()" value="发送 Ctrl+Alt+Delete"/>&nbsp;&nbsp;&nbsp;&nbsp;
	   
   </div>
   <div  id="test1">
	   <div style="border: solid 0px blue; width: 100%; height: 100%; padding-top: 1%;text-align:center;margin-right:auto; margin-left:auto;" id="pluginPanel"></div>
	   <div style="overflow: auto; height: 10px; width: 100%; display: none" id="msgBox"></div>
	</div>
 </div>
</body>
<script type="text/javascript">
	var resVmSid =  "<%=resVmSid%>";
	var webconsole = new virtualmachineConsoleModel();
	webconsole.initVmWebConsoleDatagrid();
	//全屏操作
	function setFullscreen() {
	   try {
	      vmrc.setFullscreen(true);
	   } catch(err) {
	      log('全屏 call failed: '+ err.description);
	      return;
	   }
	   log('全屏 call returned successfully');
	}
	//解锁
	function sendCAD() {
		   try {
		      vmrc.sendCAD();
		   } catch (err) {
		      log('发送 Ctrl+Alt+Delete call failed: ' + err.description);
		      return;
		   }
		   log('发送 Ctrl+Alt+Delete call returned successfully');
	}
	 //设置屏幕分辨率
	 $(document).ready(function(){
		$("#view").change(function(){
			var obj = document.getElementById("view");
	        var index = obj.selectedIndex;
			var value = obj.options[index].text;
			var size = value.split("x");
			var width = size[0].replace(/(^\s*)|(\s*$)/g,'');
			var height = size[1].replace(/(^\s*)|(\s*$)/g,'');
			var width = parseInt(width);
			var height = parseInt(height);
			vmrc.setScreenSize(width, height);
		});
	})

</script>

</html>

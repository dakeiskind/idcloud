<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <!-- 查看监控信息详情 -->
   <div id="monitorHostWindow">
        <div>监控信息</div>
        <div id="detailHostForm" style="overflow: hidden;">
        	<div style="width:100%;height:100%;">
        		 <div class="customPanel" style="float:left;width:340px;height:170px;">
        		 	<div class="title">基本信息</div>
        		 	<div></div>
        		 </div>
        		 <div class="customPanel" style="float:left;width:340px;height:170px;margin-left:5px;">
        		 	<div class="title">实时信息</div>
        		 	<div></div>
        		 </div>
        		 <div class="customPanel" style="float:left;width:340px;height:170px;margin-top:5px;">
        		 	<div class="title">CPU使用率历史趋势</div>
        		 	<div></div>
        		 </div>
        		 <div class="customPanel" style="float:left;width:340px;height:170px;margin-left:5px;margin-top:5px;">
        		 	<div class="title">内存使用率历史趋势</div>
        		 	<div></div>
        		 </div>
        	</div>
        </div>
   </div> 
  <script type="text/javascript">
	  var monitorHost = new monitorHostModel();
//	  monitorHost.initValidator();
	  monitorHost.initPopWindow();
  </script>
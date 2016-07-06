<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <div style="width:98%;height:80%;margin-left:1%;padding-top:10px;">
  	<div id='vmUnMgtdatagrid' style="width:100%;height:450px;"></div> 
 </div> 
<%--     <%@ include file="res-unmanagedvm-to-db.jsp"%> --%>
 <script type="text/javascript">
 	var unManagedvm = new vmUnManagedModel();
//  	vm.initInput();
 	unManagedvm.initUnVMDatagrid();
//  	unManagedvm.searchVMInfo();
 </script>
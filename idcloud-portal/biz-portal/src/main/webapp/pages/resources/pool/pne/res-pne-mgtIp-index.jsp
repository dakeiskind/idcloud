<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="viewPneNetworkOwnIpGrid">
	 <div>管理IP列表</div>
          <div style="position:relative;overflow:hidden">
          		<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
			           <table border="0" height="100%" cellspacing="0" cellpadding="2">
			            	<tr>
			            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;IP地址：</td>
			            			<td><input type="text" id="search-pne-network-IP-address" />&nbsp;&nbsp;</td>
			            			<td align="right">&nbsp;&nbsp;使用状态：</td>
			            			<td>
			            				<div id="search-pne-network-IP-usage-status"></div>
			            			</td>
			            			<td><input type="button" value="查询" onclick='searchPneNetworkIpConfigMgt()' id='search-pne-network-ip-button' /></td>
			            	</tr>
			            </table>
                  </div>
			     <div style="width:98%;margin-left:1%;overflow-y:auto;height:345px;"> 
			     	<div id='pneNetworkIpConfigMgtdatagrid' style="width:99.9%;"></div> 
			     </div>
			     <div style="width:100%;margin-top:10px;text-align:right">
		              <input id="pneNetworkIpCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
		              
                </div> 
          </div>
</div>

<div id="addPneNetworkOwnIpWindow">
	 <div>新增IP</div>
          <div id="addPneNetworkOwnIpForm" style="position:relative;overflow:hidden">
          		<input type="hidden" data-name="resNetworkSid" id="add-pne-network-resSid"/>
          		<input type="hidden" data-name="status" value="01" id="add-pne-network-status"/>
          		<input type="hidden" data-name="ipType" value="01" id="add-pne-network-ipType"/>
          		<table border="0" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;IP地址：</td>
            			<td><input type="text" data-name="ipAddress" id="add-pne-network-IP-address" />&nbsp;&nbsp;</td>
            		</tr>
            		<tr>	
            			<td align="right" colspan="2">
            			 	<input type="button" value="保存" onclick="submitPneNetworkIpInfo()" id='pne-network-button-save' />&nbsp;&nbsp;
            			 	<input type="button" value="取消" id='pne-network-button-cancel' />&nbsp;&nbsp;
            			</td>
	            	</tr>
	            </table>
          </div>
</div>

<script type="text/javascript">
    var viewIp = new pneNetworkViewIpGrid();
	viewIp.initInput();
	viewIp.initValidator();
	viewIp.initIpDatagrid();
	viewIp.initPopWindow();
 </script>

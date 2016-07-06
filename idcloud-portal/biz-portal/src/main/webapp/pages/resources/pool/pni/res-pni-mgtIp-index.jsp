<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="viewNetworkOwnIpGrid">
	 <div>管理IP列表</div>
          <div style="position:relative;overflow:hidden">
          		<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
			           <table border="0" height="100%" cellspacing="0" cellpadding="2">
			            	<tr>
			            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;IP地址：</td>
			            			<td><input type="text" id="search-network-IP-address" />&nbsp;&nbsp;</td>
			            			<td align="right">&nbsp;&nbsp;使用状态：</td>
			            			<td>
			            				<div id="search-network-IP-usage-status"></div>
			            			</td>
			            			<td><input type="button" value="查询" onclick='searchNetworkIpConfigMgt()' id='search-network-ip-button' /></td>
			            	</tr>
			            </table>
                  </div>
			     <div style="width:98%;margin-left:1%;overflow-y:auto;height:345px;"> 
			     	<div id='networkIpConfigMgtdatagrid' style="width:99.9%;"></div> 
			     </div>
			     <div style="width:100%;margin-top:5px;text-align:right">
		              <input id="networkIpCancel" type="button" value="取消" />&nbsp;&nbsp;
		              
                </div> 
          </div>
</div>

<div id="addPniNetworkOwnIpWindow">
	 <div>新增IP</div>
          <div id="addPniNetworkOwnIpForm" style="position:relative;overflow:hidden">
          		<input type="hidden" data-name="resNetworkSid" id="add-pni-network-resSid"/>
          		<input type="hidden" data-name="status" value="01" id="add-pni-network-status"/>
          		<input type="hidden" data-name="ipType" value="01" id="add-pni-network-ipType"/>
          		<table border="0" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;IP地址：</td>
            			<td><input type="text" data-name="ipAddress" id="add-pni-network-IP-address" />&nbsp;&nbsp;</td>
            		</tr>
            		<tr>	
            			<td align="right" colspan="2">
            			 	<input type="button" value="保存" onclick="submitPniNetworkIpInfo()" id='pni-network-button-save' />&nbsp;&nbsp;
            			 	<input type="button" value="取消" id='pni-network-button-cancel' />&nbsp;&nbsp;
            			</td>
	            	</tr>
	            </table>
          </div>
</div>
    <script type="text/javascript">
      var networkIp = new pniNetworkViewIpGrid();
	  networkIp.initInput();
	  networkIp.initValidator();
	  networkIp.initIpDatagrid();
	  networkIp.initPopWindow();
    </script>

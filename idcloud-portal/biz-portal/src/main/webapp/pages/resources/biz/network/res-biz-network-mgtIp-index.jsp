<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="viewBizNetworkOwnIpGrid" style="display: none;">
	 <div>管理IP列表</div>
          <div style="position:relative;overflow:hidden">
          		<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
			           <table border="0" height="100%" cellspacing="0" cellpadding="2">
			            	<tr>
			            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;IP地址：</td>
			            			<td><input type="text" id="search-biz-network-IP-address" />&nbsp;&nbsp;</td>
			            			<td align="right">&nbsp;&nbsp;使用状态：</td>
			            			<td>
			            				<div id="search-biz-network-IP-usage-status"></div>
			            			</td>
			            			<td><input type="button" value="查询" onclick='searchBizNetworkIpConfigMgt()' id='search-biz-network-ip-button' /></td>
			            	</tr>
			            </table>
                  </div>
			     <div style="width:98%;margin-left:1%;overflow-y:auto;height:345px;"> 
			     	<div id='bizNetworkIpConfigMgtdatagrid' style="width:99.9%;"></div> 
			     </div>
			     <div style="width:100%;margin-top:5px;text-align:right">
		              <input id="bizNetworkIpCancel" type="button" value="取消" />&nbsp;&nbsp;
                </div> 
          </div>
</div>

<div id="addBizNetworkOwnIpWindow" style="display: none;">
	 <div>新增IP</div>
          <div id="addBizNetworkOwnIpForm" style="position:relative;overflow:hidden">
          		<input type="hidden" data-name="resNetworkSid" id="add-biz-network-resSid"/>
          		<input type="hidden" data-name="status" value="01" id="add-biz-network-status"/>
          		<input type="hidden" data-name="ipType" value="01" id="add-biz-network-ipType"/>
          		<table border="0" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;IP地址：</td>
            			<td><input type="text" data-name="ipAddress" id="add-biz-network-IP-address" />&nbsp;&nbsp;</td>
            		</tr>
            		<tr>	
            			<td align="right" colspan="2">
            			 	<input type="button" value="保存" onclick="submitNetworkIpInfo()" id='biz-network-button-save' />&nbsp;&nbsp;
            			 	<input type="button" value="取消" id='biz-network-button-cancel' />&nbsp;&nbsp;
            			</td>
	            	</tr>
	            </table>
          </div>
</div>

 <script type="text/javascript">
   var networkIp = new bizNetworkViewIpGridModel();
   networkIp.initInput();
   networkIp.initValidator();
   networkIp.initIpDatagrid();
   networkIp.initPopWindow();
 </script>

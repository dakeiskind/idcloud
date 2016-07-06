<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;IP地址：</td>
            			<td><input type="text" id="search-IP-address" />&nbsp;&nbsp;</td>
            			<td align="right" nowrap="nowrap">IP类别：</td>
            			<td>
            				<div id="search-IP-category"></div>
            			</td>
            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;IP类型：</td>
            			<td>
            				<div id="search-IP-type"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;管理状态：</td>
            			<td>
            				<div id="search-IP-mgt-status"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;使用状态：</td>
            			<td>
            				<div id="search-IP-usage-status"></div>
            			</td>
            			<td><input type="button" value="查询" onclick='searchIpConfigMgt()' id='search-ip-button' /></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='ipConfigMgtdatagrid' style="width:100%;height:450px;"></div> 
    </div>  
     
   <!--   <div id="addIpWindow">
          <div>新增IP</div>
          <div id="addIpForm" style="overflow: hidden;">
	            <input type="hidden" id="add-ipCategory"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>IP起始地址:</td>
                        <td align="left"><input type="text" data-name="ipAddressStart" id="add-ipAddressStart-ip1"/></td>
                        <td align="right"><font style="color:red">*</font>IP终止地址:</td>
                        <td align="left"><input type="text" data-name="ipAddressEnd" id="add-ipAddressEnd-ip1"/></td>
                        <td align="right">子网掩码:</td>
                        <td align="left"><input type="text" data-name="subnetMask" id="add-subnetMask-ip1"/></td>
                    </tr>
                    <tr>
                        <td align="right">DNS:</td>
                        <td align="left"><input type="text" data-name="dns" id="add-dns-ip1"/></td>
                        <td align="right">网关:</td>
                        <td align="left"><input type="text" data-name="gateway" id="add-gateway-ip1"/></td>
                        <td align="right">IP类型:</td>
                        <td align="left"><div data-name="ipType" id="add-ipType-ip1"></div></td>

                    </tr>
                    <tr>
                        <td align="right">VLAN ID:</td>
                        <td align="left"><div data-name="vlanId" id="add-vlanId-ip1"></div></td> 
                        <td align="right">IP 分类:</td>
                        <td align="left" colspan="5"><div data-name="ipCategory" id="add-ipCategory-ip1"></div></td>   
                    </tr>
                    <tr>
                        <td align="right">备注:</td>
                        <td align="left" colspan="5">
                             <textarea data-name="comments" id="add-comments-ip1"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="6">
                        <input style="margin-right: 5px;" onclick="addIpSubmit()" type="button" id="addIpSave" value="保存" />
                        <input id="addIpCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
  	</div>  
       
       <div id="editIpWindow">
          <div>编辑IP</div>
          <div id="editIpForm" style="overflow: hidden;">
            	<input type="hidden" data-name="resSid" id="edit-resSid-ip1"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>IP起始地址:</td>
                        <td align="left"><input type="text" data-name="ipAddressStart" id="edit-ipAddressStart-ip1"/></td>
                        <td align="right"><font style="color:red">*</font>IP终止地址:</td>
                        <td align="left"><input type="text" data-name="ipAddressEnd" id="edit-ipAddressEnd-ip1"/></td>
                        <td align="right">子网掩码:</td>
                        <td align="left"><input type="text" data-name="subnetMask" id="edit-subnetMask-ip1"/></td>
                    </tr>
                    <tr>
                        <td align="right">DNS:</td>
                        <td align="left"><input type="text" data-name="dns" id="edit-dns-ip1"/></td>
                        <td align="right">网关:</td>
                        <td align="left"><input type="text" data-name="gateway" id="edit-gateway-ip1"/></td>
                        <td align="right">IP类型:</td>
                        <td align="left"><div data-name="ipType" id="edit-ipType-ip1"></div></td>
                    </tr>
                    <tr>
                        <td align="right">VLAN ID:</td>
                        <td align="left"><div data-name="vlanId" id="edit-vlanId-ip1"></div></td>  
                        <td align="right">IP 分类:</td>
                        <td align="left" colspan="5"><div data-name="ipCategory" id="edit-ipCategory-ip1"></div></td>   
                    </tr>
                    <tr>
                        <td align="right">备注:</td>
                        <td align="left" colspan="5">
                             <textarea data-name="comments" id="edit-comments-ip1"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="6">
                        <input style="margin-right: 5px;" onclick="editIpSubmit()" type="button" id="editIpSave" value="保存" />
                        <input id="editIpCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
  	</div>   -->
    <%@ include file="res-edit-ip.jsp"%>
       
    <script type="text/javascript">
    	 var ip = new ipConfigMgtModel();
     	 ip.initInput();
      	 ip.initIpDatagrid();
     	 ip.searchIpConfigInfo();
     	 //ip.initPopWindow();
     	 //ip.initValidator();
    </script>

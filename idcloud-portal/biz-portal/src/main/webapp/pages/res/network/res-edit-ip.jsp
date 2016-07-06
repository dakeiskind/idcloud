<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  <!-- 编辑主机 -->
	  <div id="editIpWindow">
          <div>编辑IP</div>
          <div id="editIpForm" style="overflow: hidden;">
            	<input type="hidden" data-name="resSid" id="edit-resSid-ip1"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>IP地址:</td>
                        <td align="left"><input type="text" data-name="ipAddressStart" id="edit-ipAddressStart-ip1"/></td>
                        <td align="right">子网掩码:</td>
                        <td align="left"><input type="text" data-name="subnetMask" id="edit-subnetMask-ip1"/></td>
                        <td align="right">DNS:</td>
                        <td align="left"><input type="text" data-name="dns" id="edit-dns-ip1"/></td>
                    </tr>
                    <tr>
                        <td align="right">网关:</td>
                        <td align="left"><input type="text" data-name="gateway" id="edit-gateway-ip1"/></td>
                        <td align="right">IP类型:</td>
                        <td align="left"><div data-name="ipType" id="edit-ipType-ip1"></div></td>
                        <td align="right">公网地址:</td>
                        <td align="left"><input type="text" data-name="mapPublicIp" id="edit-mapPublicIp-ip1"/></td>  
                        
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
  	</div>  
         
  <script type="text/javascript">
	    var editip = new editIpModel();
	    editip.initPopWindow();
	    editip.initValidator();
  </script>
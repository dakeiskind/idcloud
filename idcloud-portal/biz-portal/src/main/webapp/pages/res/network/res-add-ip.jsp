<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="addIpWindow">
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
                        <td align="right">公网地址:</td>
                        <td align="left"><input type="text" data-name="mapPublicIp" id="add-mapPublicIp-ip1"/></td>  
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
         
  <script type="text/javascript">
	    var addip = new addIpModel();
	    addip.initPopWindow();
	    addip.initValidator();
       

  </script>
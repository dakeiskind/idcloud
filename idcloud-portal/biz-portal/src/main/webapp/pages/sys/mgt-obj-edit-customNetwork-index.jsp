<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="editCustomNetworkWindow">
          <div>编辑自定义网络</div>
          <div id="editCustomNetworkForm" style="overflow: hidden;">
                
            	<input type="hidden" data-name="resNetworkSid" id="edit-custom-resNetworkSid"/>
          
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>网络名称:</td>
                        <td align="left"><input type="text" data-name="networkName" id="edit-custom-networkName"/></td>
                        <td align="right"><font style="color:red">*</font>网络类型:</td>
                        <td align="left"><div data-name="networkType" id="edit-custom-networkType_n1"></div></td>   
                        
                    </tr>
                    <tr>
                    	<td align="right"><font style="color:red">*</font>IP类型:</td>
                        <td align="left"><div data-name="ipType" id="edit-custom-ipType"></div></td>
                        <td align="right"><font style="color:red">*</font>子网:</td>
                        <td align="left"><input type="text" data-name="subnet" id="edit-custom-subnet"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>子网掩码:</td>
                        <td align="left"><input type="text" data-name="subnetMask" id="edit-custom-subnetMask"/></td>
                        <td align="right">网关:</td>
                        <td align="left"><input type="text" data-name="gateway" id="edit-custom-gateway"/></td>
                    </tr>
                    <tr>
                        <td align="right">首选DNS:</td>
                        <td align="left"><input type="text" data-name="dns1" id="edit-custom-dns1"/></td>  
                        <td align="right">备选DNS:</td>
                        <td align="left"><input type="text" data-name="dns2" id="edit-custom-dns2"/></td> 
                    </tr>
                    <tr>
                        <td align="right">保留IP段1开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart1" id="edit-custom-ipRetainStart1"/></td>  
                        <td align="right">保留IP段1结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd1" id="edit-custom-ipRetainEnd1"/></td> 
                    </tr>
                    <tr>
                        <td align="right">保留IP段2开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart2" id="edit-custom-ipRetainStart2"/></td>  
                        <td align="right">保留IP段2结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd2" id="edit-custom-ipRetainEnd2"/></td> 
                    </tr>
                    <tr>
                        <td align="right">保留IP段3开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart3" id="edit-custom-ipRetainStart3"/></td>  
                        <td align="right">保留IP段3结束:</td>
                        <td align="left"><input type="text" data-name="ipRetainEnd3" id="edit-custom-ipRetainEnd3"/></td> 
                    </tr>
                    <tr>
                        <td align="right">备注:</td>
                        <td align="left" colspan="3">
                             <textarea data-name="description" id="edit-custom-description"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick="editCustomNetworkSubmit()" type="button" id="editCustomNetworkSave" value="保存" />
                        <input id="editCustomNetworkCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
  	</div>  
         
  <script type="text/javascript">
	    var editCustomNetwork = new editCustomNetworkModel();
	    editCustomNetwork.initInput();
	    editCustomNetwork.initValidator();
	    editCustomNetwork.initPopWindow();
       
  </script>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="addNetworkWindow">
          <div>新增网络</div>
          <div id="addNetworkForm" style="overflow: hidden;">
                
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>网络名称:</td>
                        <td align="left" colspan="3"><input type="text" data-name="networkName" id="add-networkName"/></td>
<!--                         <td align="right"><font style="color:red">*</font>网络类型:</td> -->
<!--                         <td align="left"><div data-name="networkType" id="add-networkType_n1"></div></td>    -->
                        
                    </tr>
                    <tr>
                    	<td align="right"><font style="color:red">*</font>IP类型:</td>
                        <td align="left"><div data-name="ipType" id="add-ipType"></div></td>
                        <td align="right"><font style="color:red">*</font>子网:</td>
                        <td align="left"><input type="text" data-name="subnet" id="add-subnet"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>子网掩码:</td>
                        <td align="left"><input type="text" data-name="subnetMask" id="add-subnetMask"/></td>
                        <td align="right">网关:</td>
                        <td align="left"><input type="text" data-name="gateway" id="add-gateway"/></td>
                    </tr>
                    <tr>
                        <td align="right">首选DNS:</td>
                        <td align="left"><input type="text" data-name="dns1" id="add-dns1"/></td>  
                        <td align="right">备选DNS:</td>
                        <td align="left"><input type="text" data-name="dns2" id="add-dns2"/></td> 
                    </tr>
                     <tr id="vlanPniPool">
                       
                    </tr>
                    <tr>
                        <td align="right">保留IP段1开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart1" id="add-ipRetainStart1"/></td>  
                        <td align="right">保留IP段1结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd1" id="add-ipRetainEnd1"/></td> 
                    </tr>
                    <tr>
                        <td align="right">保留IP段2开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart2" id="add-ipRetainStart2"/></td>  
                        <td align="right">保留IP段2结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd2" id="add-ipRetainEnd2"/></td> 
                    </tr>
                    <tr>
                        <td align="right">保留IP段3开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart3" id="add-ipRetainStart3"/></td>  
                        <td align="right">保留IP段3结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd3" id="add-ipRetainEnd3"/></td> 
                    </tr>
                    <tr>
                        <td align="right">备注:</td>
                        <td align="left" colspan="3">
                             <textarea data-name="description" id="add-description"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick="addNetworkSubmit()" type="button" id="addNetworkSave" value="保存" />
                        <input id="addNetworkCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
  	</div>  
         
  <script type="text/javascript">
	    var addnetwork = new addPniNetworkModel();
	    addnetwork.initPopWindow();
	    addnetwork.initValidator();
	    
  </script>
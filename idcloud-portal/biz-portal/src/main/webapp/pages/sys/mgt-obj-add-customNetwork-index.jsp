<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="addCustomNetworkWindow">
          <div>新增自定义网络</div>
          <div id="addCustomNetworkForm" style="overflow: hidden;">
                <input type="hidden" data-name="mgtObjSid" id="add-custom-mgtObjSid"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>网络名称:</td>
                        <td align="left"><input type="text" data-name="networkName" id="add-custom-networkName"/></td>
                        <td align="right"><font style="color:red">*</font>关联外网:</td>
                        <td align="left">
                            <div data-name="extNetId" id="add-custom-extNetId"></div>
                        </td>
                    </tr>
                    <tr>
                    	<td align="right"><font style="color:red">*</font>IP类型:</td>
                        <td align="left"><div data-name="ipType" id="add-custom-ipType"></div></td>
                        <td align="right"><font style="color:red">*</font>子网:</td>
                        <td align="left"><input type="text" data-name="subnet" id="add-custom-subnet"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>子网掩码:</td>
                        <td align="left"><input type="text" data-name="subnetMask" id="add-custom-subnetMask"/></td>
                        <td align="right">网关:</td>
                        <td align="left"><input type="text" data-name="gateway" id="add-custom-gateway"/></td>
                    </tr>
                    <tr>
                        <td align="right">首选DNS:</td>
                        <td align="left"><input type="text" data-name="dns1" id="add-custom-dns1"/></td>  
                        <td align="right">备选DNS:</td>
                        <td align="left"><input type="text" data-name="dns2" id="add-custom-dns2"/></td> 
                    </tr>
<!--                      <tr id="vlanPniPool"> -->
                       
<!--                     </tr> -->
                    <tr>
                        <td align="right">保留IP段1开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart1" id="add-custom-ipRetainStart1"/></td>  
                        <td align="right">保留IP段1结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd1" id="add-custom-ipRetainEnd1"/></td> 
                    </tr>
                    <tr>
                        <td align="right">保留IP段2开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart2" id="add-custom-ipRetainStart2"/></td>  
                        <td align="right">保留IP段2结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd2" id="add-custom-ipRetainEnd2"/></td> 
                    </tr>
                    <tr>
                        <td align="right">保留IP段3开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart3" id="add-custom-ipRetainStart3"/></td>  
                        <td align="right">保留IP段3结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd3" id="add-custom-ipRetainEnd3"/></td> 
                    </tr>
                    <tr>
                        <td align="right">备注:</td>
                        <td align="left" colspan="3">
                             <textarea data-name="description" id="add-custom-description"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick="addCustomNetworkSubmit()" type="button" id="addCustomNetworkSave" value="保存" />
                        <input id="addCustomNetworkCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
  	</div>  
         
  <script type="text/javascript">
	    var addCustomnetwork = new addCustomNetworkModel();
	    addCustomnetwork.initPopWindow();
	    addCustomnetwork.initValidator();
	    
  </script>
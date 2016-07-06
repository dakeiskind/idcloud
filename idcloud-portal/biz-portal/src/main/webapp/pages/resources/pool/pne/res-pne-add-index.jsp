<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="addPneNetworkWindow">
          <div>新增网络</div>
          <div id="addPneNetworkForm" style="overflow: hidden;">
                
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>网络名称:</td>
                        <td align="left"><input type="text" data-name="networkName" id="add-pne-networkName"/></td>
<!--                         <td align="right"><font style="color:red">*</font>网络类型:</td> -->
<!--                         <td align="left"><div data-name="networkType" id="add-pne-networkType_n1"></div></td>    -->
                        <td align="right"><font style="color:red">*</font>资源环境:</td>
                        <td align="left"><div data-name="resTopologySid" id="add-pne-resTopologySid"></div></td>
                    </tr>
                    <tr>
                    	<td align="right"><font style="color:red">*</font>IP类型:</td>
                        <td align="left"><div data-name="ipType" id="add-pne-ipType"></div></td>
                        <td align="right"><font style="color:red">*</font>子网:</td>
                        <td align="left"><input type="text" data-name="subnet" id="add-pne-subnet"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>子网掩码:</td>
                        <td align="left"><input type="text" data-name="subnetMask" id="add-pne-subnetMask"/></td>
                        <td align="right">网关:</td>
                        <td align="left"><input type="text" data-name="gateway" id="add-pne-gateway"/></td>
                    </tr>
                    <tr>
                        <td align="right">首选DNS:</td>
                        <td align="left"><input type="text" data-name="dns1" id="add-pne-dns1"/></td>  
                        <td align="right">备选DNS:</td>
                        <td align="left"><input type="text" data-name="dns2" id="add-pne-dns2"/></td> 
                    </tr>
                     <tr id="vlanPnePool">
                     	                        
                    </tr>
                    <tr>
                        <td align="right">保留IP段1开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart1" id="add-pne-ipRetainStart1"/></td>  
                        <td align="right">保留IP段1结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd1" id="add-pne-ipRetainEnd1"/></td> 
                    </tr>
                    <tr>
                        <td align="right">保留IP段2开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart2" id="add-pne-ipRetainStart2"/></td>  
                        <td align="right">保留IP段2结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd2" id="add-pne-ipRetainEnd2"/></td> 
                    </tr>
                    <tr>
                        <td align="right">保留IP段3开始:</td>
                        <td align="left"><input type="text" data-name="ipRetainStart3" id="add-pne-ipRetainStart3"/></td>  
                        <td align="right">保留IP段3结束:</td>
                        <td align="left" colspan="3"><input type="text" data-name="ipRetainEnd3" id="add-pne-ipRetainEnd3"/></td> 
                    </tr>
                    <tr>
                        <td align="right">备注:</td>
                        <td align="left" colspan="3">
                             <textarea data-name="description" id="add-pne-description"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick="addPneNetworkSubmit()" type="button" id="addPneNetworkSave" value="保存" />
                        <input id="addPneNetworkCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
  	</div>  
         
  <script type="text/javascript">
	    var addpne = new addPneNetworkModel();
	    addpne.initPopWindow();
	    addpne.initValidator();

  </script>
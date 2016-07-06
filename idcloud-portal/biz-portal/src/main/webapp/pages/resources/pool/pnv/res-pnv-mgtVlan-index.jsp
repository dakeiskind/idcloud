<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="PnvMgtVlanWindow">
	 <div>管理VLAN</div>
          <div style="position:relative;overflow:hidden">
          		<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
			           <table border="0" height="100%" cellspacing="0" cellpadding="2">
			            	<tr>
			            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;VLAN_ID：</td>
			            			<td><input type="text" id="search-pnv-vlan-address" />&nbsp;&nbsp;</td>
			            			<td align="right">&nbsp;&nbsp;使用状态：</td>
			            			<td>
			            				<div id="search-pnv-vlan-usage-status"></div>
			            			</td>
			            			<td><input type="button" value="查询" onclick='searchPnvMgtVlan()' id='search-pnv-vlan-button' /></td>
			            	</tr>
			            </table>
                  </div>
			     <div style="width:100%;height:220px;overflow-y:auto;"> 
			     	<div id='pnvMgtVlanDatagrid' style="width:99.9%;"></div> 
			     </div>
			     <div style="width:100%;margin-top:10px;text-align:right">
		              <input id="pnvMgtVlanCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
		              
                </div> 
          </div>
</div>

<div id="addMgtVlanWindow">
	 <div>新增VLAN</div>
          <div style="position:relative;overflow:hidden">
          		<div id="addMgtVlanForm" style="width:100%;height:30px;">
          			   <input type="hidden" data-name="resPoolSid" id="pnv-vlan-resPoolSid"/>
			           <table border="0" height="100%" cellspacing="0" cellpadding="2">
    			           <tr>
		                       <td align="right"><font style="color:red">*</font>起始VLAN ID:</td>
		                       <td align="left">
		                          <input type="text" data-name="startValnId" id="pnv-vlan-add-startValnId"/>
		                       </td>
		                   </tr>
		                   <tr>
		                       <td align="right"><font style="color:red">*</font>结束VLAN ID:</td>
		                       <td align="left">
		                          <input type="text" data-name="endVlanId" id="pnv-vlan-add-endVlanId"/>
		                       </td>
		                   </tr>
	            			<tr>
		            			<td align="right">&nbsp;&nbsp;端口组：</td>
		            			<td>
		            				<input type="text" data-name="tag" id="pnv-vlan-add-tag" />
		            			</td>
			            	 </tr>
			            	 <tr>		
		            			<td align="right" colspan="2" height="40px">
		            			   <input id="addVlanSave" type="button" onclick="submitAddMgtVlan()" value="保存" />&nbsp;&nbsp;
		            			   <input id="addVlanCancel" type="button" value="取消" />
		            			</td>
			            	</tr>
			            </table>
                  </div>
          </div>
</div>

<div id="editMgtVlanWindow">
	 <div>编辑VLAN</div>
          <div style="position:relative;overflow:hidden">
          		<div id="editMgtVlanForm" style="width:100%;height:30px;">
          			   <input type="hidden" data-name="resSid" id="pnv-vlan-resSid"/>
			           <table border="0" height="100%" cellspacing="0" cellpadding="2">
			            	<tr>
		            			<td align="right" nowrap="nowrap"><font style="color:red">*</font>VLAN_ID：</td>
		            			<td>
		            			   <input type="text" data-name="vlanId" id="pnv-vlan-edit-vlanId" />
		            			</td>
	            			</tr>
	            			<tr>
		            			<td align="right">&nbsp;&nbsp;端口组：</td>
		            			<td>
		            				<input type="text" data-name="tag" id="pnv-vlan-edit-tag" />
		            			</td>
			            	 </tr>
			            	 <tr>		
		            			<td align="right" colspan="2" height="40px">
		            			   <input id="editVlanSave" type="button" onclick="submitEditMgtVlan()" value="保存" />&nbsp;&nbsp;
		            			   <input id="editVlanCancel" type="button" value="取消" />
		            			</td>
			            	</tr>
			            </table>
                  </div>
          </div>
</div>

    <script type="text/javascript">
      var pnvvlan = new pnvNetworkMgtVlanGrid();
      pnvvlan.initInput();
      pnvvlan.initIpDatagrid();
      pnvvlan.initPopWindow();
      pnvvlan.initValidator();
    </script>

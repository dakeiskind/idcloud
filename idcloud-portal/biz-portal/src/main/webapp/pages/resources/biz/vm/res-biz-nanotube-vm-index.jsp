<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="nanotubeVmWindow" style="width:100%;">
    <div>纳管虚拟机</div>
    <div style="overflow:hidden">
		<div style="width:100%;height:30px;">
	           <table border="0" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
	            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;虚拟机名称：</td>
	           			<td><input type="text" id="search-biz-nanotube-vm-name" />&nbsp;&nbsp;</td>
	            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;虚拟机IP：</td>
	           			<td><input type="text" id="search-biz-nanotube-vm-ip" />&nbsp;&nbsp;</td>
	           			<td align="right">&nbsp;&nbsp;状态：</td>
	           			<td>
	           				<div id="search-biz-nanotube-vm-status"></div>
	           			</td>
	           			<td><a onclick="searchBizNanotubeVm()" id="search-biz-nanotube-vm-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
	            	</tr>
	            </table>
	    </div>
	    <div style="width:100%;height:345px;overflow-y:auto;"> 
	     	<div id='nanotubeableVmDatagrid'></div> 
	    </div>
	    <div style="width:100%;height:30px;text-align:right;padding-top:6px;"> 
	     	<input id="nanotubeVmCancel" type="button" value="取消" />
	    </div>
	 </div>
</div>

<div id="nanotubeVmChooseBizWindow">
     <div>选择项目</div>
     <div id="nanotubeVmChooseBizForm" style="overflow: hidden;">
     	 <input type="hidden" data-name="resBizVmSids" id="biz_nanotube_resBizVmSids"/>
         <table border="0" width="100%" cellspacing="5" cellpadding="0">
<!--              <tr> -->
<!--                  <td align="right">业务属性:</td> -->
<!--                  <td align="left" > -->
<!--                      <div  data-name="parentBizSid" id="biz_nanotube_parentBizSid"></div> -->
<!--                  </td> -->
<!--              </tr>  -->
             <tr>
                 <td align="right">项目名称:</td>
                 <td align="left" >
                     <div data-name="mgtObjSid" id="biz_nanotube_bizSid"></div>
                 </td>
             </tr>
             <tr>
                 <td align="right">所属用户:</td>
                 <td align="left" >
                     <div data-name="userSid" id="biz_nanotube_userSid"></div>
                 </td>
             </tr>
             <tr>
             	<td colspan="2" align="right">
             		<input style="margin-right: 5px;margin-top:5px;" onclick="submitNanotubeVmInfo()" id="chooseBizSave" type="button" value="保存" />
             		<input id="chooseBizCancel" style="margin-top: 5px;" type="button" value="取消" />
             	</td>
             </tr>
         </table>
     </div>
</div> 

<script type="text/javascript">
	 var nanotube = new resBizNanotubeVmModel();
	 nanotube.initNanotubeVmInput();
	 nanotube.initNanotubeVMDatagrid();
	 nanotube.initPopWindow();
	 nanotube.searchNanotubeVmInfo();
	 
// 	 $('#biz_nanotube_parentBizSid').on('select', function (event) {	
// 		 var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownHeight:200,dropDownWidth:150});
// 			var parentBizSid =  $("#biz_nanotube_parentBizSid").val();
// 			codeadd.getCustomCode("biz_nanotube_bizSid","/bizType/findSbizType","name","bizSid", false, 'POST', {'parentBizSid': parentBizSid});
// 		});
</script>

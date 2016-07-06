<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<input type="hidden" id="mgtObjSid-host" />
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;主机名称：</td>
           			<td><input type="text" id="search-biz-physicalhost-name" />&nbsp;&nbsp;</td>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;主机IP：</td>
           			<td><input type="text" id="search-biz-physicalhost-ip" />&nbsp;&nbsp;</td>
           			<td align="right">&nbsp;&nbsp;状态：</td>
           			<td>
           				<div id="search-biz-physicalhost-status"></div>
           			</td>
           			<td><a onclick="searchPhysicalBizHost()" id="search-biz-physicalhost-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
           			<td align="right" id="nanotubeHostButton"></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='bizPhysicalHostdatagrid' style="width:100%;height:450px;"></div> 
	    <div style="height: 30px;"><p style="margin:0px;margin-top:7px;font-size:14px;color: green;">
	    	※列表中<u style="color: #FB4242;">红色</u>代表已到期，<u style="color: rgb(205, 205, 56);">黄色</u>代表即将到期</p>
	    </div>
    </div>
</div>

<!-- 纳管主机 -->
<div id="addMgtObjHostWindow" style="display: none;">
    <div>纳管主机</div>
	<div style="overflow: hidden;">
          <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
		       <table border="0" height="100%" cellspacing="0" cellpadding="2">
		        	<tr>
	        			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;主机名称：</td>
	        			<td><input type="text" id="search-add-mgtObj-host-name" />&nbsp;&nbsp;</td>
	        			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;主机IP：</td>
           				<td><input type="text" id="search-add-mgtObj-host-ip" />&nbsp;&nbsp;</td>
	        			<td><a onclick="searchMgtObjHostRel()" id="search-add-mgtObj-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
		        	</tr>
		        </table>
		 </div>
		 <div style="width:100%;height:308px;overflow-y:auto;">
          		<div id='mgtObj-hostGrid' style="width:100%;"></div>
         </div>
          
         <div style="position:absolute;bottom:5px;width:100%;height:40px;text-align:right;line-height:45px;">
          		<input style="margin-right: 5px;" type="button" onclick='submitMgtObjHostRel()'  id="saveMgtObjHost" value="保存" />
                <input id="cancelMgtObjHost" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
         </div>
   </div>
</div>

<div id="nanotubeHostChooseBizWindow" style="display: none;">
     <div>选择项目</div>
     <div id="nanotubeHostChooseBizForm" style="overflow: hidden;">
     	 <input type="hidden" data-name="resBizHostSids" id="biz_nanotube_resBizHostSids"/>
         <table border="0" width="100%" cellspacing="5" cellpadding="0">
             <tr>
                 <td align="right">项目名称:</td>
                 <td align="left" >
                     <div data-name="mgtObjSid" id="host_nanotube_mgtobjSid"></div>
                 </td>
             </tr>
             <tr>
                 <td align="right">所属用户:</td>
                 <td align="left" >
                     <div data-name="userSid" id="host_nanotube_userSid"></div>
                 </td>
             </tr>
             <tr>
             	<td colspan="2" align="right">
             		<input style="margin-right: 5px;margin-top:5px;" onclick="submitNanotubeHostInfo()" id="chooseMgtobjSave" type="button" value="保存" />
             		<input id="chooseMgtObjCancel" style="margin-top: 5px;" type="button" value="取消" />
             	</td>
             </tr>
         </table>
     </div>
</div>  


<script type="text/javascript">
$(function(){
	if(10 == resBizSid){
		var str1 = "<a onclick='popNanotubeHostWindow()' id='search-biz-nanotube-host-button'><i class='icons-blue icon-download'></i>&nbsp;纳管物理机</a>";
		$("#nanotubeHostButton").html(str1);
	}
});
function bizPhysicalHostDatagridModelInfo(){
	 var physicalhost = new bizPhysicalHostDatagridModel();
	 // 初始化页面组件
	 physicalhost.initPhysicalHostInput();
	 // 初始化datagrid
	 physicalhost.initPhysicalHostDatagrid();
	 physicalhost.searchPhysicalHostInfo();
	 
	 physicalhost.initPopWindow();
}
	
</script>

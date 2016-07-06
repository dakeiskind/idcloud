<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<!-- 网络关联主机 --> 
<div id="pniRelationHostWindow">
       <div>关联主机</div>
       <div style="overflow: hidden;">
       		<div style="width:100%;height:30px;padding:3px 0px 3px 0px;">
	           <table border="0" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
            			<td align="right" valign="top" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;<div style="margin-top:-10px;">主机名称：</div></td>
            			<td valign="top"><input type="text" id="search-pni-relation-hostName" />&nbsp;&nbsp;</td>
            			<td align="right" valign="top" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;<div style="margin-top:-10px;">主机IP：</div></td>
            			<td valign="top"><input type="text" id="search-pni-relation-hostIp" />&nbsp;&nbsp;</td>
            			<td align="right" valign="top" nowrap="nowrap">&nbsp;&nbsp;<div style="margin-top:-10px;">资源环境：</div></td>
            			<td  valign="top"><div id="search-pni-relation-ve"></div>&nbsp;&nbsp;</td>
            			<td valign="top"><input type="button" value="查询" onclick='searchPniNetworkRelationHost()' id='search-pni-relation-host-button' /></td>
	            	</tr>
	            </table>
		    </div>
		    <div style="width:98%;height:350px;margin-left:1%"> 
		     	<div id='pniRelationedHostDatagrid' style="width:100%;height:330px;"></div> 
		    </div>
		    
		    <div style="width:98%;height:30px;margin-left:1%;text-align:right"> 
		     	<input type="button" value="关闭" id='relationHostWindowClose' />
		    </div>
       </div>	
</div>

<!-- 新增关联主机 --> 
<div id="addPniRelationHostWindow">
       <div>新增关联主机</div>
       <div style="overflow: hidden;">
       		<div style="width:100%;height:30px;padding:3px 0px 3px 0px;">
	           <table border="0" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
            			<td align="right" valign="top" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;<div style="margin-top:-10px;">主机名称：</div></td>
            			<td valign="top"><input type="text" id="search-pni-unrelation-hostName" />&nbsp;&nbsp;</td>
            			<td align="right" valign="top" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;<div style="margin-top:-10px;">主机IP：</div></td>
            			<td valign="top"><input type="text" id="search-pni-unrelation-hostIp" />&nbsp;&nbsp;</td>
            			<td align="right" valign="top" nowrap="nowrap">&nbsp;&nbsp;<div style="margin-top:-10px;">资源环境：</div></td>
            			<td  valign="top"><div id="search-pni-unrelation-ve"></div>&nbsp;&nbsp;</td>
            			<td valign="top"><input type="button" value="查询" onclick='searchPniNetworkUnRelationHost()' id='search-pni-unrelation-host-button' /></td>
	            	</tr>
	            </table>
		    </div>
       
		    <div style="width:98%;height:320px;margin-left:1%;"> 
		     	<div id='addPniRelationedHostDatagrid' style="width:100%;height:330px;"></div> 
		    </div>
		    
		    <div style="width:98%;height:30px;margin-left:1%;text-align:right;"> 
		    	<input type="button" value="保存" onclick="submitRelationHostInfo()" id='addRelationHostWindowSave' />&nbsp;&nbsp;
		     	<input type="button" value="关闭" id='addRelationHostWindowClose' />
		    </div>
       </div>	
</div>

 <script type="text/javascript">
	  var relationHost = new pniRelationHostModel();
	  relationHost.initInput();
	  relationHost.initPoolPniRelationHostDatagrid();
	  relationHost.initPopWindow();
 </script>

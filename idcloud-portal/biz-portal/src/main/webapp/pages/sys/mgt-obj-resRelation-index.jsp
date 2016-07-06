<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="mgtObjResWin" style="width:100%;height:100%;">
	<div>资源关联</div>
	<div style="overflow: hidden;" id="winContent">
		<input type="hidden" id="opMgtObjSid" />
   		<div style="width:100%;height:9%;">
       		<table border="0" cellspacing="2" cellpadding="2">
          	<tr>
          		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;资源分区：</td>
       			<td><div id="resTopology"></div></td>
          		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;资源环境：</td>
       			<td><div id="resEnvironment"></div></td> 
       			<td><a onclick="searchResData()" id="search-mgtObjRes-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
          	</tr>
         </table>
        </div>
    	<div id="tab2" style="width: 100%; height: 83%;">
        	<ul style="margin-left: 6px;">
				<li style="margin-left: 6px;"><i class='icons-blue icon-tasks'></i>计算资源</li>
          		<li><i class='icons-blue icon-list'></i>存储资源</li>
          		<li><i class='icons-blue icon-list'></i>网络资源</li>
            </ul>
			<div id="mgtObjComResForm" style="width:100%;height:97%%;margin-top: 1%;">
		         <div style="width:98%;height:88%;margin-left:1%;"> 
			     	<div id='mgtObjComResDatagrid' style="width:100%;height:100%;"></div> 
			    </div>
			</div>
			<div id="mgtObjStResForm" style="width:100%;height:97%;margin-top: 1%;">
		         <div style="width:98%;height:88%;margin-left:1%;"> 
			     	<div id='mgtObjStResDatagrid' style="width:100%;height:100%;"></div> 
			    </div>
			</div>
			<div id="mgtObjNetResForm" style="width:100%;height:97%;margin-top: 1%;">
		         <div style="width:98%;height:88%;margin-left:1%;"> 
			     	<div id='mgtObjNetResDatagrid' style="width:100%;height:100%;"></div> 
			    </div>
			</div>
		</div>
		<div style="width: 100%; height: 8%; text-align: right;margin-top: 1%;">
			<input type="button" id="mgtObjResSave" value="确定" onclick="saveMgtObjResRelation()" /> 
			<input style="margin-right: 6px;" id="mgtObjResCancel" type="button" value="取消" />
		</div>
    </div>
</div>

<script type="text/javascript">
      var mgtObjRes = new mgtObjResModel();
      mgtObjRes.initInput();
      mgtObjRes.initResDatagrid();
      mgtObjRes.initPopWindow();
</script>

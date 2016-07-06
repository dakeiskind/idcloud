<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="remarkPniIpWindow">
	 <div>IP占用</div>
          <div id="remarkPniIpForm" style="position:relative;overflow:hidden">
          	   <input type="hidden" data-name="resIpSids" id="remark-pni-resSids"/>
               <table border="0" width="100%" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
            			<td align="right" nowrap="nowrap">状态：</td>
            			<td><div data-name="status" id="remark-pni-status"></div></td>
            		</tr>
            		<tr>	
            			<td align="right">备注：</td>
            			<td>
            				<textarea data-name="description" id="remark-pni-description"></textarea>
            			</td>
	            	</tr>
	            	<tr>	
            			<td colspan="2" align="right">
            				<input id="remarkPniIpSave" onclick="submitPniRemarkIpInfo()" type="button" value="保存" />&nbsp;&nbsp;
		              		<input id="remarkPniIpCancel" type="button" value="取消" />&nbsp;&nbsp;
            			</td>
	            	</tr>
	            </table>
          
          </div>
</div>

<script type="text/javascript">
    var remarkPni = new remarkPniIpModel();
    remarkPni.initInput();
    remarkPni.initPopWindow();
    
</script>

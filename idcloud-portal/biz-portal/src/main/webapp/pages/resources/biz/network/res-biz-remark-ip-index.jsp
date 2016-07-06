<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="remarkBizIpWindow">
	 <div>IP占用</div>
          <div id="remarkBizIpForm" style="position:relative;overflow:hidden">
          	   <input type="hidden" data-name="resIpSids" id="remark-biz-resSids"/>
               <table border="0" width="100%" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
            			<td align="right" nowrap="nowrap">状态：</td>
            			<td><div data-name="status" id="remark-biz-status"></div></td>
            		</tr>
            		<tr>	
            			<td align="right">备注：</td>
            			<td>
            				<textarea data-name="description" id="remark-biz-description"></textarea>
            			</td>
	            	</tr>
	            	<tr>	
            			<td colspan="2" align="right">
            				<input id="remarkBizIpSave" onclick="submitRemarkIpInfo()" type="button" value="保存" />&nbsp;&nbsp;
		              		<input id="remarkBizIpCancel" type="button" value="取消" />&nbsp;&nbsp;
            			</td>
	            	</tr>
	            </table>
          
          </div>
</div>

<script type="text/javascript">
    var remark = new remarkBizIpModel();
    remark.initInput();
    remark.initPopWindow();
    
    $("#remark-biz-status").on('select', function (event) {
        if (event.args) {
            var item = event.args.item;
            if (item) {
                if("02" == item.value){
                	$("#remark-biz-description").val("已被【"+resBizType+"】使用。");
                }else{
                	$("#remark-biz-description").val("已被【"+resBizType+"】预占。");
                }
                
            }
        }
    });
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

 <div id="modifyVmWindow">
       <div>虚拟机信息修改</div>
       <div id="modifyVmForm" style="overflow: hidden;">
           <table border="0" width="100%" cellspacing="5" cellpadding="0">
               <tr>
                   <td align="right">创建时间:</td>
                   <td align="left" >
                       <div  data-name="createDate" id="createDate"></div>
                   </td>
               </tr>
               <tr>
                   <td align="right"  colspan="2" height="40"></td>
               </tr> 
               <tr>
               	<td colspan="2" align="right">
               		<input style="margin-right: 5px;" onclick="modifyVmSave()" id="modifyVmSave" type="button" value="保存" />
               		<input id="modifyVmCancel" type="button" value="取消" />
               	</td>
               </tr>
           </table>
       </div>
</div> 

<script type="text/javascript">
     var modify = new resBizModifyVmAddModel();
     modify.initPopWindow();
</script>

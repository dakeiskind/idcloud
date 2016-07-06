<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
	  <div id="vmReconfigWindow">
            <div>调整配置</div>
            <div id="vmReconfigForm" style="overflow: hidden;">
            	<input type="hidden" data-name="resInstanceSid" id="resInstanceSid"/>
            	<input type="hidden" data-name="allocateInstanceId" id="allocateInstanceId"/>
            	<table border="0" width="100%" cellspacing="5" cellpadding="0" >
                     <tr>
                        <td align="right"><font style="color:red">*</font>CPU核数：</td>
                        <td>
	        				<div data-name="cpuCores"  id="vm-cpu-cores"></div>
	        			</td>
                    </tr>
                    <tr>
                        <td align="right">内存大小（MB）:</td>
                        <td>
	        				<div data-name="memorySize"  id="vm-memory-size"></div>
	        			</td>
                    </tr>
                </table>
                <div style="width:100%;padding-top:10px;text-align:right">
            		  <input style="margin-right: 5px;" onclick='vmReconfigSubmit()' type="button" id="vmReconfigSave" value="保存" />
		              <input id="vmReconfigCancel" type="button" value="取消" />
                </div>
       </div> 
    </div>     
  <script type="text/javascript">
	    var vmreconfigmodel = new vmReconfigModel();
	    vmreconfigmodel.initPopWindow();
	    vmreconfigmodel.initInput();
// 	    vmreconfigmodel.initValidator();
  </script>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="setPcAdvanceConfigWindow">
          <div>高级设置</div>
          <div id="setPcAdvanceConfigForm" style="overflow: hidden;">
                <input type="hidden" data-name="resTopologySid" id="set-config-topologysid"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right">资源分配策略:</td>
                        <td align="left">
                            <div data-name="policy" id="set-config-policy"></div>
                        </td>
                    </tr>   
                   	<tr>
                        <td align="right">资源分配公式:</td>
                        <td align="left">
                        	<div data-name="mode" id="set-config-mode" style="float:left"></div>
                        	<div style="float:left">
                        		&nbsp;*&nbsp;<input type="text" data-name="rate" id="set-config-rate"></input>
                        	</div>
                        </td>   
                    </tr>
                    <tr>
                    	<td align="right">资源分配阀值:</td>
                        <td align="left"><input type="text" data-name="threshold" id="set-config-threshold"></input></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" colspan="2" height="35px">
	                        <input style="margin-right: 5px;" onclick="submitAdvanceConfig()" type="button" id="setConfigSave" value="保存" />
	                        <input id="setConfigCancel" type="button" value="取消" />
                        </td>
                    </tr>
                </table>
            </div>
  	</div>  
         
  <script type="text/javascript">
	    var config = new setPcAdvanceConfigModel();
	    config.initPopWindow();
	    config.initValidator();
	    

  </script>
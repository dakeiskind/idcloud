<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="setVeAdvanceConfigWindow">
          <div>高级设置</div>
          <div id="setVeAdvanceConfigForm" style="overflow: hidden;">
                <input type="hidden" data-name="resTopologySid" id="set-ve-topologysid"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right">是否开启VNC:</td>
                        <td align="left">
                            <div data-name="vnc" id="set-open-vnc"></div>
                        </td>
                    </tr>   
                   	<tr>
                        <td align="right">VNC连接配置开始端口:</td>
                        <td align="left">
                        	<input type="text" data-name="startPort" id="set-start-port"></input>
                        </td>   
                    </tr>
                    <tr>
                    	<td align="right">VNC连接配置结束端口:</td>
                        <td align="left"><input type="text" data-name="endPort" id="set-end-port"></input></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" colspan="2" height="35px">
	                        <input style="margin-right: 5px;" onclick="submitVeAdvanceConfig()" type="button" id="setVeConfigSave" value="保存" />
	                        <input id="setVeConfigCancel" type="button" value="取消" />
                        </td>
                    </tr>
                </table>
            </div>
  	</div>  
         
  <script type="text/javascript">
	    var config = new setVeAdvanceConfigModel();
	    config.initPopWindow();
	    config.initValidator();
	    

  </script>
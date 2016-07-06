<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="switchWindows">
     <div>详情</div>
     <div id="switchForm" style="overflow: hidden;">
             <div class="customPanel" style="width:99%;height:200px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;基本信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">位置编号：</td>
                                    <td align="left" style="width:15%" id="switch-locationNumber"></td>
                                    <td align="right" style="width:15%">设备编号：</td>
                                    <td align="left" style="width:15%"id="switch-name"></td>
                                    <td align="right"style="width:15%">设备类型：</td>
                                    <td align="left" style="width:15%"id="switch-equipType"></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">设备分类：</td>
                                    <td align="left" style="width:15%"id="switch-equipCategoryName"></td>
                                    <td align="right">序列号：</td>
                                    <td align="left" style="width:15%"id="switch-serialNumber"></td>
                                    <td align="right">品牌：</td>
                                    <td align="left" style="width:15%"id="switch-brand" ></td>
                                </tr>
                                <tr>
                                    <td align="right"style="width:15%">型号：</td>
                                    <td align="left" style="width:15%"id="switch-model"></td>
                                </tr>
                                <tr>
                                    <td align="right"style="width:15%">所属机房：</td>
                                    <td align="left" style="width:15%"id="switch-equipRoomName" ></td>
                                    <td align="right"style="width:15%">所属机柜：</td>
                                    <td align="left" style="width:15%"id="switch-equipCabinetName" ></td>
                                    <td align="right"style="width:15%">所属机架：</td>
                                    <td align="left" style="width:15%"id="switch-equipRackName" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">规格：</td>
                                    <td align="left" style="width:15%"id="switch-spec" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">描述：</td>
                                    <td align="left" style="width:15%"id="switch-description" ></td>
                                </tr>
                        </table>
                    </div>
             </div>
            <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                            <div class="title">&nbsp;&nbsp;远程管理信息</div>
                            <div>
                                  <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td align="right" style="width:15%">远程管理IP1:</td>
                                            <td align="left" style="width:15%"id="switch-remoteMgtIp1"></td>
                                            <td align="right"  style="width:15%">远程管理IP2:</td>
                                            <td align="left" style="width:15%"id="switch-remoteMgtIp2"></td>
                                            <td align="right"  style="width:15%">关联IP地址：</td>
                                            <td align="left" style="width:15%"id="switch-relevanceIp"></td>
                                        </tr>
                                        <tr>    
                                            <td align="right" style="width:15%">远程管理用户：</td>
                                            <td align="left" style="width:15%"id="switch-remoteMgtUser"></td>
                                            <td align="right"style="width:15%">远程管理密码：</td>
                                            <td align="left" style="width:15%"id="switch-remoteMgtPwd"></td>
                                        </tr>
                                </table>
                            </div>
            </div>
            
            <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;维保信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" width="95px">维保厂商：</td>
                                    <td align="left" id="switch-maintCompany"></td>
                                    <td align="right" width="95px">维保电话：</td>
                                    <td align="left" id="switch-maintTel"></td>
                                    <td align="right" width="95px">购置日期：</td>
                                    <td align="left" id="switch-purchaseDate"></td>
                                </tr>
                                <tr>    
                                    <td align="right">保修起始日期：</td>
                                    <td align="left" id="switch-startEndDate"></td>
                                    <td align="right">保修期限：</td>
                                    <td align="left" id="switch-warrantyPeriod"></td>
                                    <td align="right">设备提供商：</td>
                                    <td align="left" id="switch-equipSupplier"></td>
                                </tr>
                          </table>
                    </div>
            </div>
            <div style="width:99%;height:40px;margin-left:0.5%;margin-top:8px;text-align:right;">
                  <br/>
                  <input type="button" id="switch-close-button" value="关闭" />
            </div>
     </div>
</div> 
<script type="text/javascript">
$("#switchWindows").jqxWindow({
    width: 800, 
    height:600,
    theme:currentTheme,  
    resizable: false,  
    isModal: true, 
    autoOpen: false, 
    cancelButton: $("#switch-close-button"), 
    modalOpacity: 0.3,
    initContent:function(){
      // 初始化新增用户页面组件
    $("#switch-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    }
});
</script>


